package com.gtismartmoneytrader.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtismartmoneytrader.data.repository.GTIRepository
import com.gtismartmoneytrader.domain.engine.BacktestEngine
import com.gtismartmoneytrader.domain.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BacktestUiState(
    val selectedSymbol: Symbol = Symbol.NIFTY,
    val selectedPeriod: BacktestPeriod = BacktestPeriod.MONTH_1,
    val result: BacktestResult? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class BacktestViewModel @Inject constructor(
    private val repository: GTIRepository,
    private val backtestEngine: BacktestEngine
) : ViewModel() {

    private val _uiState = MutableStateFlow(BacktestUiState())
    val uiState: StateFlow<BacktestUiState> = _uiState.asStateFlow()

    init { runBacktest() }

    fun selectSymbol(symbol: Symbol) {
        _uiState.update { it.copy(selectedSymbol = symbol) }
        runBacktest()
    }

    fun selectPeriod(period: BacktestPeriod) {
        _uiState.update { it.copy(selectedPeriod = period) }
        runBacktest()
    }

    fun runBacktest() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                repository.getRecentCandles(
                    _uiState.value.selectedSymbol,
                    Timeframe.FIVE_MIN,
                    _uiState.value.selectedPeriod.days * 80
                ).collect { candles ->
                    if (candles.isEmpty()) {
                        _uiState.update { it.copy(isLoading = false) }
                        return@collect
                    }
                    val mockMarketData = MarketData(
                        symbol = _uiState.value.selectedSymbol,
                        timestamp = System.currentTimeMillis(),
                        ltp = candles.lastOrNull()?.ohlc?.close ?: 22000.0,
                        ohlc = candles.lastOrNull()?.ohlc ?: OHLC(22000.0, 22100.0, 21900.0, 22000.0),
                        volume = candles.lastOrNull()?.volume ?: 0L,
                        atr = candles.lastOrNull()?.atr ?: 100.0,
                        change = 0.0,
                        changePercent = 0.0
                    )
                    val result = backtestEngine.runBacktest(
                        candles = candles,
                        marketData = mockMarketData,
                        period = _uiState.value.selectedPeriod
                    )
                    _uiState.update { it.copy(result = result, isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }
}
