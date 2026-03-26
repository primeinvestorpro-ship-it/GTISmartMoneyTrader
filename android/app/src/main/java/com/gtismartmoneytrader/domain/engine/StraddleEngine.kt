package com.gtismartmoneytrader.domain.engine

import com.gtismartmoneytrader.domain.model.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Straddle Engine – evaluates whether market conditions are suitable for a Short Straddle.
 *
 * Key inputs:
 *   • ATM Call + Put LTP (option chain)
 *   • IV Percentile (rolling 52-week rank)
 *   • ADX (trend strength from GTI Engine)
 *
 * Short straddle is recommended when:
 *   ivPercentile > 60  (IV is inflated → premium seller advantage)
 *   ADX < 20           (market is NOT trending)
 */
@Singleton
class StraddleEngine @Inject constructor(
    private val gtiEngine: GTIIndicatorEngine
) {

    companion object {
        private const val IV_PERCENTILE_THRESHOLD = 60.0
        private const val ADX_TREND_THRESHOLD = 20.0

        // Expected move multiplier (1-sigma): approximately 0.68 of price × daily vol
        private const val SIGMA_MULTIPLIER = 0.68
    }

    // Rolling IV window for percentile calculation (simulate 52-week)
    private val ivHistory = ArrayDeque<Double>(260)

    fun evaluate(
        marketData: MarketData,
        candles: List<Candle>
    ): StraddleResult {
        val atmStrike = calculateATMStrike(marketData.symbol, marketData.ltp)
        val callLtp = marketData.atmCallLtp ?: estimatePremium(marketData, atmStrike, "CE")
        val putLtp = marketData.atmPutLtp ?: estimatePremium(marketData, atmStrike, "PE")
        val totalPremium = callLtp + putLtp

        val ivPercentile = marketData.ivPercentile ?: calculateIVPercentile(marketData)
        val adx = if (candles.size >= 30) gtiEngine.calculateADX(candles) else 25.0

        val breakEvenUpper = atmStrike + totalPremium
        val breakEvenLower = atmStrike - totalPremium

        // Expected range using 1-sigma move
        val hv = marketData.historicalVolatility ?: 15.0 // default 15% annualized HV
        val dailyMove = marketData.ltp * (hv / 100.0) / sqrt(252.0)
        val expectedRangeHigh = marketData.ltp + dailyMove * 5  // 5-day look-ahead
        val expectedRangeLow = marketData.ltp - dailyMove * 5

        val isLowTrend = adx < ADX_TREND_THRESHOLD
        val isHighIV = ivPercentile > IV_PERCENTILE_THRESHOLD
        val isRecommended = isLowTrend && isHighIV

        // Probability of profit: higher when premium > expected move
        val expectedMoveSize = (expectedRangeHigh - expectedRangeLow)
        val bepSpread = breakEvenUpper - breakEvenLower
        val probabilityOfProfit = when {
            bepSpread >= expectedMoveSize * 1.3 -> 75.0
            bepSpread >= expectedMoveSize * 1.0 -> 65.0
            bepSpread >= expectedMoveSize * 0.7 -> 55.0
            else -> 42.0
        }.let { if (isRecommended) it else it - 10.0 }.coerceIn(30.0, 85.0)

        val volatilityRisk = when {
            ivPercentile > 80 -> VolatilityRisk.HIGH
            ivPercentile > 60 -> VolatilityRisk.MEDIUM
            else -> VolatilityRisk.LOW
        }

        return StraddleResult(
            symbol = marketData.symbol,
            atmStrike = atmStrike,
            isRecommended = isRecommended,
            probabilityOfProfit = probabilityOfProfit,
            expectedRangeLow = expectedRangeLow,
            expectedRangeHigh = expectedRangeHigh,
            totalPremium = totalPremium,
            breakEvenUpper = breakEvenUpper,
            breakEvenLower = breakEvenLower,
            ivPercentile = ivPercentile,
            volatilityRisk = volatilityRisk
        )
    }

    fun updateIVHistory(iv: Double) {
        if (ivHistory.size >= 260) ivHistory.removeFirst()
        ivHistory.addLast(iv)
    }

    private fun calculateIVPercentile(marketData: MarketData): Double {
        if (ivHistory.isEmpty()) return 50.0 // default neutral
        val currentIV = marketData.historicalVolatility ?: 15.0
        val below = ivHistory.count { it <= currentIV }
        return (below.toDouble() / ivHistory.size) * 100.0
    }

    private fun calculateATMStrike(symbol: Symbol, price: Double): Int {
        val step = when (symbol) {
            Symbol.NIFTY -> 50
            Symbol.BANKNIFTY -> 100
            Symbol.FINNIFTY -> 50
        }
        return (Math.round(price / step) * step).toInt()
    }

    // Rough Black-Scholes approximation for ATM option premium when no live data
    private fun estimatePremium(marketData: MarketData, atmStrike: Int, type: String): Double {
        val hv = marketData.historicalVolatility ?: 15.0
        val daysToExpiry = 7.0  // assume weekly expiry
        val timeValue = marketData.ltp * (hv / 100.0) * sqrt(daysToExpiry / 365.0) * 0.4
        return maxOf(timeValue, 50.0)
    }
}
