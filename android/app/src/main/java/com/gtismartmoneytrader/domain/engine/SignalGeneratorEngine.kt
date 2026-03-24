package com.gtismartmoneytrader.domain.engine

import com.gtismartmoneytrader.domain.model.*
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignalGeneratorEngine @Inject constructor(
    private val gtiEngine: GTIIndicatorEngine
) {
    private var lastYellowCandle: Candle? = null
    private var lastSignalTimestamp: Long = 0
    private val signalCooldownMs: Long = 300000 // 5 minutes between signals

    fun generateSignal(
        currentCandle: Candle,
        previousCandles: List<Candle>,
        symbol: Symbol,
        settings: UserSettings
    ): Signal? {
        // Check cooldown
        if (System.currentTimeMillis() - lastSignalTimestamp < signalCooldownMs) {
            return null
        }

        // Calculate current candle type
        val candleType = gtiEngine.calculateCandleType(currentCandle, previousCandles)
        val currentCandleWithType = currentCandle.copy(candleType = candleType)

        // Track yellow candles
        if (candleType == CandleType.YELLOW) {
            lastYellowCandle = currentCandleWithType
        }

        // Generate BUY CALL signal
        val callSignal = checkForCallSignal(currentCandleWithType, lastYellowCandle, symbol)
        if (callSignal != null) {
            lastSignalTimestamp = System.currentTimeMillis()
            return callSignal
        }

        // Generate BUY PUT signal
        val putSignal = checkForPutSignal(currentCandleWithType, lastYellowCandle, symbol)
        if (putSignal != null) {
            lastSignalTimestamp = System.currentTimeMillis()
            return putSignal
        }

        return null
    }

    private fun checkForCallSignal(
        currentCandle: Candle,
        yellowCandle: Candle?,
        symbol: Symbol
    ): Signal? {
        // Conditions for BUY CALL:
        // 1. Yellow candle exists
        // 2. Price breaks Yellow HIGH
        // 3. Current candle = Blue
        
        if (yellowCandle == null) return null
        if (currentCandle.candleType != CandleType.BLUE) return null
        
        val breakoutLevel = yellowCandle.ohlc.high
        
        if (currentCandle.ohlc.close > breakoutLevel) {
            return createSignal(
                type = SignalType.BUY_CALL,
                symbol = symbol,
                breakoutLevel = breakoutLevel,
                currentCandle = currentCandle,
                yellowCandle = yellowCandle
            )
        }
        
        return null
    }

    private fun checkForPutSignal(
        currentCandle: Candle,
        yellowCandle: Candle?,
        symbol: Symbol
    ): Signal? {
        // Conditions for BUY PUT:
        // 1. Yellow candle exists
        // 2. Price breaks Yellow LOW
        // 3. Current candle = Black
        
        if (yellowCandle == null) return null
        if (currentCandle.candleType != CandleType.BLACK) return null
        
        val breakoutLevel = yellowCandle.ohlc.low
        
        if (currentCandle.ohlc.close < breakoutLevel) {
            return createSignal(
                type = SignalType.BUY_PUT,
                symbol = symbol,
                breakoutLevel = breakoutLevel,
                currentCandle = currentCandle,
                yellowCandle = yellowCandle
            )
        }
        
        return null
    }

    private fun createSignal(
        type: SignalType,
        symbol: Symbol,
        breakoutLevel: Double,
        currentCandle: Candle,
        yellowCandle: Candle
    ): Signal {
        val entryPrice = currentCandle.ohlc.close
        val optionType = if (type == SignalType.BUY_CALL) "CE" else "PE"
        val strike = calculateATMStrike(symbol, entryPrice)
        
        // Calculate SL and Target
        val slPercent = 0.20 // 20% of premium
        val stopLoss = entryPrice * (1 - slPercent)
        val targetRatio = 2.0 // 1:2 risk-reward
        val target = entryPrice + (entryPrice - stopLoss) * targetRatio
        
        // Calculate confidence
        val confidence = calculateConfidence(currentCandle, yellowCandle)
        
        val candlePattern = CandlePattern(
            yellowCandle = true,
            breakoutLevel = breakoutLevel,
            currentCandle = currentCandle.candleType
        )
        
        return Signal(
            id = UUID.randomUUID().toString(),
            type = type,
            confidence = confidence,
            entryPrice = entryPrice,
            stopLoss = stopLoss,
            target = target,
            strike = strike,
            optionType = optionType,
            timestamp = System.currentTimeMillis(),
            symbol = symbol.displayName,
            candlePattern = candlePattern
        )
    }

    private fun calculateATMStrike(symbol: Symbol, price: Double): Int {
        val lotSize = when (symbol) {
            Symbol.NIFTY -> 50
            Symbol.BANKNIFTY -> 15
        }
        
        val roundedPrice = (price / lotSize).toInt() * lotSize
        return roundedPrice
    }

    private fun calculateConfidence(currentCandle: Candle, yellowCandle: Candle): ConfidenceLevel {
        var score = 0
        
        // Volume strength factor
        val avgVolume = gtiEngine.getAverageVolume()
        if (avgVolume > 0) {
            val volumeRatio = currentCandle.volume / avgVolume
            when {
                volumeRatio > 2.0 -> score += 3
                volumeRatio > 1.5 -> score += 2
                volumeRatio > 1.0 -> score += 1
            }
        }
        
        // Candle body clarity
        val range = currentCandle.ohlc.high - currentCandle.ohlc.low
        if (range > 0) {
            val bodyRatio = kotlin.math.abs(currentCandle.ohlc.close - currentCandle.ohlc.open) / range
            if (bodyRatio > 0.7) score += 2
            else if (bodyRatio > 0.5) score += 1
        }
        
        // Time of day factor
        val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        val isOptimalTime = (hour in 9..11) || (hour in 13..15)
        if (isOptimalTime) score += 2
        
        // Yellow candle proximity
        val timeDiff = currentCandle.timestamp - yellowCandle.timestamp
        val minutesDiff = timeDiff / 60000
        if (minutesDiff <= 15) score += 2
        else if (minutesDiff <= 30) score += 1
        
        return when {
            score >= 7 -> ConfidenceLevel.HIGH
            score >= 4 -> ConfidenceLevel.MEDIUM
            else -> ConfidenceLevel.LOW
        }
    }

    fun reset() {
        lastYellowCandle = null
        lastSignalTimestamp = 0
    }
}
