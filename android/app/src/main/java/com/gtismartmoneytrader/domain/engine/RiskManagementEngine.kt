package com.gtismartmoneytrader.domain.engine

import com.gtismartmoneytrader.domain.model.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.max
import kotlin.math.min

@Singleton
class RiskManagementEngine @Inject constructor() {

    private var trailingStopLoss: Double? = null
    private var peakPrice: Double? = null
    private var entryPrice: Double? = null

    fun calculateStopLoss(
        entryPrice: Double,
        settings: UserSettings,
        previousCandles: List<Candle>,
        signalType: SignalType
    ): Double {
        // Method 1: Percentage-based SL
        val percentageSL = entryPrice * (1 - settings.stopLossPercent / 100)
        
        // Method 2: Structure-based SL (swing points)
        val structureSL = calculateStructureSL(previousCandles, signalType)
        
        // Return the tighter of the two
        return if (signalType == SignalType.BUY_CALL) {
            maxOf(percentageSL, structureSL ?: 0.0)
        } else {
            minOf(percentageSL, structureSL ?: Double.MAX_VALUE)
        }
    }

    private fun calculateStructureSL(
        previousCandles: List<Candle>,
        signalType: SignalType
    ): Double? {
        if (previousCandles.size < 5) return null
        
        val lookbackCandles = previousCandles.takeLast(10)
        
        return if (signalType == SignalType.BUY_CALL) {
            // For CALL signals, use recent swing low
            lookbackCandles.minOf { it.ohlc.low }
        } else {
            // For PUT signals, use recent swing high
            lookbackCandles.maxOf { it.ohlc.high }
        }
    }

    fun calculateTarget(
        entryPrice: Double,
        stopLoss: Double,
        settings: UserSettings
    ): Double {
        val risk = kotlin.math.abs(entryPrice - stopLoss)
        return if (entryPrice > stopLoss) {
            entryPrice + (risk * settings.targetRatio)
        } else {
            entryPrice - (risk * settings.targetRatio)
        }
    }

    fun updateTrailingStopLoss(
        currentPrice: Double,
        settings: UserSettings
    ): Double? {
        if (!settings.trailingSLEnabled) return null
        
        // Initialize peak price on first call
        if (peakPrice == null) {
            peakPrice = currentPrice
            return null
        }
        
        // Update peak price
        peakPrice = maxOf(peakPrice!!, currentPrice)
        
        // Calculate profit percentage
        val profitPercent = if (entryPrice != null && entryPrice!! > 0) {
            ((peakPrice!! - entryPrice!!) / entryPrice!!) * 100
        } else {
            0.0
        }
        
        // Check if trailing SL should activate
        if (profitPercent >= settings.trailingSLThreshold) {
            val trailAmount = peakPrice!! * (settings.trailingSLThreshold / 100)
            trailingStopLoss = peakPrice!! - trailAmount
        }
        
        return trailingStopLoss
    }

    fun shouldActivateTrailingSL(currentPrice: Double): Boolean {
        if (entryPrice == null) return false
        val profitPercent = ((currentPrice - entryPrice!!) / entryPrice!!) * 100
        return profitPercent >= 20.0
    }

    fun reset() {
        trailingStopLoss = null
        peakPrice = null
        entryPrice = null
    }

    fun setEntryPrice(price: Double) {
        entryPrice = price
    }

    fun getCurrentStopLoss(
        baseSL: Double,
        currentPrice: Double,
        signalType: SignalType
    ): Double {
        val trailingSL = updateTrailingStopLoss(currentPrice, 
            UserSettings(trailingSLEnabled = true, trailingSLThreshold = 20.0)
        )
        
        return if (trailingSL != null) {
            if (signalType == SignalType.BUY_CALL) {
                maxOf(baseSL, trailingSL)
            } else {
                minOf(baseSL, trailingSL)
            }
        } else {
            baseSL
        }
    }
}
