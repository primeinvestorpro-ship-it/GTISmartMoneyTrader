package com.gtismartmoneytrader.domain.engine

import com.gtismartmoneytrader.domain.model.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.max
import kotlin.math.min

@Singleton
class GTIIndicatorEngine @Inject constructor() {

    private var volumeHistory: List<Long> = emptyList()
    private var atrHistory: List<Double> = emptyList()
    private var adxHistory: List<Double> = emptyList()
    
    companion object {
        private const val VOLUME_MA_PERIOD = 20
        private const val ATR_PERIOD = 14
        private const val ADX_PERIOD = 14
        
        // Accumulation detection thresholds
        private const val VOLUME_SPIKE_FACTOR_YELLOW = 1.2
        private const val LOWER_WICK_RATIO_YELLOW = 1.5
        
        // Momentum detection thresholds
        private const val VOLUME_SPIKE_FACTOR_MOMENTUM = 1.5
        private const val BODY_TO_RANGE_RATIO = 0.6
    }

    fun calculateCandleType(
        candle: Candle,
        previousCandles: List<Candle>
    ): CandleType {
        val volumeMA = calculateVolumeMA(previousCandles, VOLUME_MA_PERIOD)
        val atr = calculateATR(previousCandles, ATR_PERIOD)
        
        return when {
            isAccumulation(candle, volumeMA) -> CandleType.YELLOW
            isBullishMomentum(candle, volumeMA) -> CandleType.BLUE
            isBearishMomentum(candle, volumeMA) -> CandleType.BLACK
            else -> if (candle.isBullish) CandleType.BLUE else CandleType.BLACK
        }
    }

    private fun isAccumulation(candle: Candle, volumeMA: Double): Boolean {
        val isBullish = candle.ohlc.close > candle.ohlc.open
        val hasVolumeSpike = candle.volume > volumeMA * VOLUME_SPIKE_FACTOR_YELLOW
        val hasLargeLowerWick = (candle.ohlc.close - candle.ohlc.low) > 
                                (candle.ohlc.high - candle.ohlc.close) * LOWER_WICK_RATIO_YELLOW
        
        return isBullish && hasVolumeSpike && hasLargeLowerWick
    }

    private fun isBullishMomentum(candle: Candle, volumeMA: Double): Boolean {
        if (candle.ohlc.close <= candle.ohlc.open) return false
        
        val range = candle.ohlc.high - candle.ohlc.low
        if (range == 0.0) return false
        
        val bodyToRangeRatio = (candle.ohlc.close - candle.ohlc.open) / range
        val hasVolumeSpike = candle.volume > volumeMA * VOLUME_SPIKE_FACTOR_MOMENTUM
        
        return bodyToRangeRatio > BODY_TO_RANGE_RATIO && hasVolumeSpike
    }

    private fun isBearishMomentum(candle: Candle, volumeMA: Double): Boolean {
        if (candle.ohlc.open <= candle.ohlc.close) return false
        
        val range = candle.ohlc.high - candle.ohlc.low
        if (range == 0.0) return false
        
        val bodyToRangeRatio = (candle.ohlc.open - candle.ohlc.close) / range
        val hasVolumeSpike = candle.volume > volumeMA * VOLUME_SPIKE_FACTOR_MOMENTUM
        
        return bodyToRangeRatio > BODY_TO_RANGE_RATIO && hasVolumeSpike
    }

    private fun calculateVolumeMA(candles: List<Candle>, period: Int): Double {
        if (candles.isEmpty()) return 0.0
        val relevantCandles = candles.takeLast(minOf(period, candles.size))
        return relevantCandles.map { it.volume.toDouble() }.average()
    }

    fun calculateATR(candles: List<Candle>, period: Int = ATR_PERIOD): Double {
        if (candles.size < period + 1) return 0.0
        
        val trueRanges = mutableListOf<Double>()
        for (i in 1 until candles.size) {
            val current = candles[i]
            val previous = candles[i - 1]
            
            val tr1 = current.ohlc.high - current.ohlc.low
            val tr2 = kotlin.math.abs(current.ohlc.high - previous.ohlc.close)
            val tr3 = kotlin.math.abs(current.ohlc.low - previous.ohlc.close)
            
            trueRanges.add(max(tr1, max(tr2, tr3)))
        }
        
        return trueRanges.takeLast(period).average()
    }

    fun calculateADX(candles: List<Candle>, period: Int = ADX_PERIOD): Double {
        if (candles.size < period * 2 + 1) return 0.0
        
        val plusDM = mutableListOf<Double>()
        val minusDM = mutableListOf<Double>()
        val trueRanges = mutableListOf<Double>()
        
        for (i in 1 until candles.size) {
            val current = candles[i]
            val previous = candles[i - 1]
            
            val highDiff = current.ohlc.high - previous.ohlc.high
            val lowDiff = previous.ohlc.low - current.ohlc.low
            
            val plusDMValue = if (highDiff > lowDiff && highDiff > 0) highDiff else 0.0
            val minusDMValue = if (lowDiff > highDiff && lowDiff > 0) lowDiff else 0.0
            
            plusDM.add(plusDMValue)
            minusDM.add(minusDMValue)
            
            val tr1 = current.ohlc.high - current.ohlc.low
            val tr2 = kotlin.math.abs(current.ohlc.high - previous.ohlc.close)
            val tr3 = kotlin.math.abs(current.ohlc.low - previous.ohlc.close)
            trueRanges.add(max(tr1, max(tr2, tr3)))
        }
        
        val smoothedPlusDM = smoothValues(plusDM, period)
        val smoothedMinusDM = smoothValues(minusDM, period)
        val smoothedTR = smoothValues(trueRanges, period)
        
        if (smoothedTR.isEmpty() || smoothedTR.last() == 0.0) return 0.0
        
        val plusDI = (smoothedPlusDM.last() / smoothedTR.last()) * 100
        val minusDI = (smoothedMinusDM.last() / smoothedTR.last()) * 100
        
        val diSum = plusDI + minusDI
        if (diSum == 0.0) return 0.0
        
        return kotlin.math.abs(plusDI - minusDI) / diSum * 100
    }

    private fun smoothValues(values: List<Double>, period: Int): List<Double> {
        if (values.size < period) return emptyList()
        
        val smoothed = mutableListOf<Double>()
        var sum = values.take(period).sum()
        smoothed.add(sum)
        
        for (i in period until values.size) {
            sum = sum - (sum / period) + values[i]
            smoothed.add(sum)
        }
        
        return smoothed
    }

    fun updateHistory(candle: Candle) {
        volumeHistory = (volumeHistory + candle.volume).takeLast(VOLUME_MA_PERIOD * 2)
        atrHistory = (atrHistory + candle.atr).takeLast(ATR_PERIOD * 2)
    }

    fun getAverageVolume(): Double = if (volumeHistory.isNotEmpty()) volumeHistory.average() else 0.0
    
    fun getAverageATR(): Double = if (atrHistory.isNotEmpty()) atrHistory.average() else 0.0
}
