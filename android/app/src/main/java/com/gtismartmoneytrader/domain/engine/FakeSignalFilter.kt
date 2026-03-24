package com.gtismartmoneytrader.domain.engine

import com.gtismartmoneytrader.domain.model.*
import java.util.Calendar
import java.util.TimeZone
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeSignalFilter @Inject constructor(
    private val gtiEngine: GTIIndicatorEngine
) {

    private val tradingWindow = TradingWindow()

    fun shouldGenerateSignal(
        candles: List<Candle>,
        settings: UserSettings
    ): FilterResult {
        // Check time filter
        val timeFilter = checkTimeFilter()
        if (!timeFilter.isAllowed) {
            return FilterResult(
                isAllowed = false,
                message = "Outside trading hours: ${timeFilter.message}"
            )
        }

        // Check ATR filter
        val atrFilter = checkATRFilter(candles, settings)
        if (!atrFilter.isAllowed) {
            return FilterResult(
                isAllowed = false,
                message = atrFilter.message ?: "Low volatility"
            )
        }

        // Check volume filter
        val volumeFilter = checkVolumeFilter(candles, settings)
        if (!volumeFilter.isAllowed) {
            return FilterResult(
                isAllowed = false,
                message = volumeFilter.message ?: "Low volume"
            )
        }

        // Check sideways market filter
        val sidewaysFilter = checkSidewaysMarket(candles, settings)
        if (!sidewaysFilter.isAllowed) {
            return FilterResult(
                isAllowed = false,
                message = sidewaysFilter.message ?: "Sideways market"
            )
        }

        return FilterResult(isAllowed = true, message = null)
    }

    private fun checkTimeFilter(): FilterResult {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"))
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val currentTimeMinutes = hour * 60 + minute

        val morningStart = parseTime(tradingWindow.morningStart)
        val morningEnd = parseTime(tradingWindow.morningEnd)
        val afternoonStart = parseTime(tradingWindow.afternoonStart)
        val afternoonEnd = parseTime(tradingWindow.afternoonEnd)

        val isInMorningWindow = currentTimeMinutes in morningStart until morningEnd
        val isInAfternoonWindow = currentTimeMinutes in afternoonStart until afternoonEnd

        return if (isInMorningWindow || isInAfternoonWindow) {
            FilterResult(isAllowed = true, message = null)
        } else {
            FilterResult(
                isAllowed = false,
                message = "Trading Window: ${tradingWindow.morningStart}-${tradingWindow.morningEnd} & ${tradingWindow.afternoonStart}-${tradingWindow.afternoonEnd} IST"
            )
        }
    }

    private fun parseTime(time: String): Int {
        val parts = time.split(":")
        return parts[0].toInt() * 60 + parts[1].toInt()
    }

    private fun checkATRFilter(
        candles: List<Candle>,
        settings: UserSettings
    ): FilterResult {
        if (candles.isEmpty()) {
            return FilterResult(isAllowed = true, message = null)
        }

        val atr = gtiEngine.calculateATR(candles)
        
        return if (atr < settings.atrThreshold) {
            FilterResult(
                isAllowed = false,
                message = "Low Volatility (ATR: ${String.format("%.2f", atr)} < ${settings.atrThreshold})"
            )
        } else {
            FilterResult(isAllowed = true, message = null)
        }
    }

    private fun checkVolumeFilter(
        candles: List<Candle>,
        settings: UserSettings
    ): FilterResult {
        if (candles.size < 20) {
            return FilterResult(isAllowed = true, message = null)
        }

        val avgVolume = candles.takeLast(20).map { it.volume.toDouble() }.average()
        val currentVolume = candles.last().volume
        
        val volumeThreshold = avgVolume * (settings.volumeThresholdPercent / 100)
        
        return if (currentVolume < volumeThreshold) {
            FilterResult(
                isAllowed = false,
                message = "Low Volume (${String.format("%.0f", (currentVolume/avgVolume)*100)}% of average)"
            )
        } else {
            FilterResult(isAllowed = true, message = null)
        }
    }

    private fun checkSidewaysMarket(
        candles: List<Candle>,
        settings: UserSettings
    ): FilterResult {
        if (candles.size < 30) {
            return FilterResult(isAllowed = true, message = null)
        }

        val adx = gtiEngine.calculateADX(candles)
        
        return if (adx < settings.adxThreshold) {
            FilterResult(
                isAllowed = false,
                message = "Sideways Market (ADX: ${String.format("%.2f", adx)} < ${settings.adxThreshold})"
            )
        } else {
            FilterResult(isAllowed = true, message = null)
        }
    }

    fun getMarketStatus(candles: List<Candle>, settings: UserSettings): MarketStatus {
        if (candles.size < 30) return MarketStatus.SIDEWAYS
        
        val adx = gtiEngine.calculateADX(candles)
        
        return if (adx > settings.adxThreshold) {
            MarketStatus.TRENDING
        } else {
            MarketStatus.SIDEWAYS
        }
    }

    fun getTradingStatus(): TradingStatus {
        val timeFilter = checkTimeFilter()
        return if (timeFilter.isAllowed) TradingStatus.OPEN else TradingStatus.CLOSED
    }

    data class FilterResult(
        val isAllowed: Boolean,
        val message: String? = null
    )
}
