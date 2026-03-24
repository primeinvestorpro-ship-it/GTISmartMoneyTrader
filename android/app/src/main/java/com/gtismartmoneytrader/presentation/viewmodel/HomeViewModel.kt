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
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: GTIRepository,
    private val gtiEngine: GTIIndicatorEngine,
    private val signalGenerator: SignalGeneratorEngine,
    private val fakeSignalFilter: FakeSignalFilter,
    private val riskEngine: RiskManagementEngine,
    private val optionEngine: OptionSuggestionEngine
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
                _uiState.update { it.copy(candles = candles.toList()) }
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
        loadInitialData()
    }

    fun processNewMarketData(marketData: MarketData) {
        viewModelScope.launch {
            // Create new candle from market data
            val newCandle = Candle(
                timestamp = marketData.timestamp,
                ohlc = marketData.ohlc,
                volume = marketData.volume,
                atr = marketData.atr,
                candleType = CandleType.BLUE
            )

            // Calculate GTI candle type
            val previousCandles = candles.toList()
            val gtiCandle = Candle(
                timestamp = newCandle.timestamp,
                ohlc = newCandle.ohlc,
                volume = newCandle.volume,
                atr = newCandle.atr,
                candleType = gtiEngine.calculateCandleType(newCandle, previousCandles)
            )

            // Update candles list
            candles.add(gtiCandle)
            if (candles.size > 100) {
                candles.removeAt(0)
            }

            // Update UI state
            _uiState.update {
                it.copy(
                    marketData = marketData,
                    candles = candles.toList(),
                    marketStatus = fakeSignalFilter.getMarketStatus(candles, settings.value),
                    tradingStatus = fakeSignalFilter.getTradingStatus()
                )
            }

            // Check for signals
            checkForSignal(gtiCandle, previousCandles)
            
            // Update history in engine
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
                currentCandle,
                previousCandles,
                _uiState.value.selectedSymbol,
                settings.value
            )

            if (signal != null) {
                // Calculate SL and Target
                val sl = riskEngine.calculateStopLoss(
                    signal.entryPrice,
                    settings.value,
                    previousCandles,
                    signal.type
                )
                val target = riskEngine.calculateTarget(
                    signal.entryPrice,
                    sl,
                    settings.value
                )

                val updatedSignal = signal.copy(stopLoss = sl, target = target)

                // Save signal
                repository.saveSignal(updatedSignal)
                
                _uiState.update { it.copy(activeSignal = updatedSignal) }
            }
        }
    }

    fun acknowledgeSignal() {
        viewModelScope.launch {
            _uiState.value.activeSignal?.let { signal ->
                repository.acknowledgeSignal(signal.id)
            }
            _uiState.update { it.copy(activeSignal = null) }
        }
    }

    fun dismissSignal() {
        _uiState.update { it.copy(activeSignal = null) }
    }

    fun updateSettings(newSettings: UserSettings) {
        settings.value = newSettings
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
