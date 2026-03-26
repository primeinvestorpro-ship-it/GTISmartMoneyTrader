package com.gtismartmoneytrader.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtismartmoneytrader.data.repository.GTIRepository
import com.gtismartmoneytrader.domain.engine.*
import com.gtismartmoneytrader.domain.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val selectedSymbol: Symbol = Symbol.NIFTY,
    val marketData: MarketData? = null,
    val candles: List<Candle> = emptyList(),
    val signals: List<Signal> = emptyList(),
    val activeSignal: Signal? = null,
    val marketStatus: MarketStatus = MarketStatus.SIDEWAYS,
    val tradingStatus: TradingStatus = TradingStatus.CLOSED,
    val filterMessage: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    // === NEW Fusion + Straddle state ===
    val fusionDecision: FusionDecision? = null,
    val straddleResult: StraddleResult? = null,
    val marketMode: MarketMode = MarketMode.NEUTRAL,
    val adx: Double = 0.0
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: GTIRepository,
    private val gtiEngine: GTIIndicatorEngine,
    private val signalGenerator: SignalGeneratorEngine,
    private val fakeSignalFilter: FakeSignalFilter,
    private val riskEngine: RiskManagementEngine,
    private val optionEngine: OptionSuggestionEngine,
    // === NEW engines ===
    private val straddleEngine: StraddleEngine,
    private val fusionAIEngine: FusionAIEngine
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val settings = MutableStateFlow(UserSettings())
    private val candles = mutableListOf<Candle>()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.getRecentCandles(
                _uiState.value.selectedSymbol,
                Timeframe.FIVE_MIN,
                100
            ).collect { historicalCandles ->
                candles.clear()
                candles.addAll(historicalCandles)
                _uiState.update { it.copy(candles = candles.toList(), isLoading = false) }
            }
        }

        viewModelScope.launch {
            repository.getAllSignals().collect { signals ->
                _uiState.update { it.copy(signals = signals) }
            }
        }
    }

    fun selectSymbol(symbol: Symbol) {
        _uiState.update { it.copy(selectedSymbol = symbol) }
        candles.clear()
        loadInitialData()
    }

    fun processNewMarketData(marketData: MarketData) {
        viewModelScope.launch {
            val newCandle = Candle(
                timestamp = marketData.timestamp,
                ohlc = marketData.ohlc,
                volume = marketData.volume,
                atr = marketData.atr,
                candleType = CandleType.BLUE
            )

            val previousCandles = candles.toList()
            val gtiCandle = newCandle.copy(
                candleType = gtiEngine.calculateCandleType(newCandle, previousCandles)
            )

            candles.add(gtiCandle)
            if (candles.size > 100) candles.removeAt(0)

            val currentCandles = candles.toList()
            val adx = if (currentCandles.size >= 30) gtiEngine.calculateADX(currentCandles) else 0.0

            // === Run Straddle Engine ===
            val straddleResult = straddleEngine.evaluate(marketData, currentCandles)

            // === Run Fusion AI ===
            val fusionDecision = fusionAIEngine.decide(
                latestCandles = currentCandles,
                latestSignal = _uiState.value.activeSignal,
                straddleResult = straddleResult,
                adx = adx
            )

            val mode = fusionDecision.marketMode

            _uiState.update {
                it.copy(
                    marketData = marketData,
                    candles = currentCandles,
                    marketStatus = fakeSignalFilter.getMarketStatus(currentCandles, settings.value),
                    tradingStatus = fakeSignalFilter.getTradingStatus(),
                    straddleResult = straddleResult,
                    fusionDecision = fusionDecision,
                    marketMode = mode,
                    adx = adx
                )
            }

            checkForSignal(gtiCandle, previousCandles)
            gtiEngine.updateHistory(gtiCandle)
        }
    }

    private fun checkForSignal(currentCandle: Candle, previousCandles: List<Candle>) {
        viewModelScope.launch {
            val filterResult = fakeSignalFilter.shouldGenerateSignal(candles, settings.value)
            if (!filterResult.isAllowed) {
                _uiState.update { it.copy(filterMessage = filterResult.message) }
                return@launch
            }
            _uiState.update { it.copy(filterMessage = null) }

            val signal = signalGenerator.generateSignal(
                currentCandle, previousCandles, _uiState.value.selectedSymbol, settings.value
            )

            if (signal != null) {
                val sl = riskEngine.calculateStopLoss(signal.entryPrice, settings.value, previousCandles, signal.type)
                val target = riskEngine.calculateTarget(signal.entryPrice, sl, settings.value)
                val updatedSignal = signal.copy(stopLoss = sl, target = target)
                repository.saveSignal(updatedSignal)
                _uiState.update { it.copy(activeSignal = updatedSignal) }

                // Re-run fusion with the new signal
                _uiState.value.straddleResult?.let { straddle ->
                    val newFusion = fusionAIEngine.decide(
                        latestCandles = candles.toList(),
                        latestSignal = updatedSignal,
                        straddleResult = straddle,
                        adx = _uiState.value.adx
                    )
                    _uiState.update { it.copy(fusionDecision = newFusion, marketMode = newFusion.marketMode) }
                }
            }
        }
    }

    fun acknowledgeSignal() {
        viewModelScope.launch {
            _uiState.value.activeSignal?.let { repository.acknowledgeSignal(it.id) }
            _uiState.update { it.copy(activeSignal = null) }
        }
    }

    fun dismissSignal() { _uiState.update { it.copy(activeSignal = null) } }

    fun updateSettings(newSettings: UserSettings) { settings.value = newSettings }

    fun clearError() { _uiState.update { it.copy(error = null) } }
}
