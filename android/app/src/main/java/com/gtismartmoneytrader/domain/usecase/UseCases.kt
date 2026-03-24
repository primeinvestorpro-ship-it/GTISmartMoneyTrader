package com.gtismartmoneytrader.domain.usecase

import com.gtismartmoneytrader.domain.engine.*
import com.gtismartmoneytrader.domain.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMarketDataUseCase @Inject constructor(
    private val marketDataRepository: MarketDataRepository
) {
    fun execute(symbol: Symbol): Flow<MarketData> = flow {
        marketDataRepository.getMarketDataFlow(symbol).collect { data ->
            emit(data)
        }
    }
}

class ProcessCandlesUseCase @Inject constructor(
    private val gtiEngine: GTIIndicatorEngine
) {
    fun execute(
        candle: Candle,
        previousCandles: List<Candle>
    ): Candle {
        val candleType = gtiEngine.calculateCandleType(candle, previousCandles)
        val updatedCandle = candle.copy(candleType = candleType)
        gtiEngine.updateHistory(updatedCandle)
        return updatedCandle
    }
}

class GenerateSignalUseCase @Inject constructor(
    private val signalGenerator: SignalGeneratorEngine,
    private val fakeSignalFilter: FakeSignalFilter,
    private val riskEngine: RiskManagementEngine
) {
    fun execute(
        currentCandle: Candle,
        previousCandles: List<Candle>,
        symbol: Symbol,
        settings: UserSettings
    ): SignalGenerationResult {
        // Apply filters first
        val filterResult = fakeSignalFilter.shouldGenerateSignal(previousCandles, settings)
        if (!filterResult.isAllowed) {
            return SignalGenerationResult.Filtered(filterResult.message ?: "Signal filtered")
        }

        // Generate signal
        val signal = signalGenerator.generateSignal(currentCandle, previousCandles, symbol, settings)
        
        if (signal != null) {
            // Calculate risk parameters
            val stopLoss = riskEngine.calculateStopLoss(
                signal.entryPrice,
                settings,
                previousCandles,
                signal.type
            )
            val target = riskEngine.calculateTarget(
                signal.entryPrice,
                stopLoss,
                settings
            )
            
            val updatedSignal = signal.copy(
                stopLoss = stopLoss,
                target = target
            )
            
            return SignalGenerationResult.Success(updatedSignal)
        }
        
        return SignalGenerationResult.NoSignal
    }
}

sealed class SignalGenerationResult {
    data class Success(val signal: Signal) : SignalGenerationResult()
    data class Filtered(val reason: String) : SignalGenerationResult()
    object NoSignal : SignalGenerationResult()
}

class GetOptionSuggestionUseCase @Inject constructor(
    private val optionEngine: OptionSuggestionEngine
) {
    fun execute(
        signal: Signal,
        underlyingPrice: Double,
        optionChain: OptionChain?
    ): ATMOption? {
        return optionEngine.suggestOption(signal, underlyingPrice, optionChain)
    }
}

class CheckFiltersUseCase @Inject constructor(
    private val fakeSignalFilter: FakeSignalFilter
) {
    fun getMarketStatus(candles: List<Candle>, settings: UserSettings): MarketStatus {
        return fakeSignalFilter.getMarketStatus(candles, settings)
    }
    
    fun getTradingStatus(): TradingStatus {
        return fakeSignalFilter.getTradingStatus()
    }
    
    fun shouldGenerateSignal(
        candles: List<Candle>,
        settings: UserSettings
    ): FakeSignalFilter.FilterResult {
        return fakeSignalFilter.shouldGenerateSignal(candles, settings)
    }
}

class CalculateRiskUseCase @Inject constructor(
    private val riskEngine: RiskManagementEngine
) {
    fun calculateStopLoss(
        entryPrice: Double,
        settings: UserSettings,
        previousCandles: List<Candle>,
        signalType: SignalType
    ): Double {
        return riskEngine.calculateStopLoss(entryPrice, settings, previousCandles, signalType)
    }
    
    fun calculateTarget(
        entryPrice: Double,
        stopLoss: Double,
        settings: UserSettings
    ): Double {
        return riskEngine.calculateTarget(entryPrice, stopLoss, settings)
    }
    
    fun updateTrailingSL(
        currentPrice: Double,
        settings: UserSettings
    ): Double? {
        return riskEngine.updateTrailingStopLoss(currentPrice, settings)
    }
}

interface MarketDataRepository {
    fun getMarketDataFlow(symbol: Symbol): Flow<MarketData>
    suspend fun getOptionChain(symbol: Symbol, expiry: String): OptionChain?
}
