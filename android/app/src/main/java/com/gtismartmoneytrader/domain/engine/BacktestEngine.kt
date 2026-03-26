package com.gtismartmoneytrader.domain.engine

import com.gtismartmoneytrader.domain.model.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * BacktestEngine – replays historical candle data to evaluate GTI + straddle win rates.
 *
 * GTI accuracy: % of BUY_CALL / BUY_PUT signals that went on to hit target price
 * Straddle win rate: % of identified straddle days where price stayed within BEP band
 */
@Singleton
class BacktestEngine @Inject constructor(
    private val gtiEngine: GTIIndicatorEngine,
    private val signalGenerator: SignalGeneratorEngine,
    private val straddleEngine: StraddleEngine
) {

    fun runBacktest(
        candles: List<Candle>,
        marketData: MarketData,
        period: BacktestPeriod
    ): BacktestResult {
        val daysOfData = period.days
        val relevantCandles = candles.takeLast(daysOfData * 78) // ~78 5-min bars per day
        if (relevantCandles.size < 20) return emptyResult(marketData.symbol, period)

        // --- GTI Accuracy ---
        var gtiTotal = 0
        var gtiProfits = 0

        for (i in 20 until relevantCandles.size - 5) {
            val current = relevantCandles[i]
            val previous = relevantCandles.subList(0, i)
            val signal = signalGenerator.generateSignal(current, previous, marketData.symbol, com.gtismartmoneytrader.domain.model.UserSettings())

            if (signal != null) {
                gtiTotal++
                if (didSignalHitTarget(signal, relevantCandles.subList(i, minOf(i + 30, relevantCandles.size)))) {
                    gtiProfits++
                }
            }
        }

        // --- Straddle Win Rate (daily evaluation) ---
        val dailyGroups = relevantCandles.chunked(78)
        var straddleTotal = 0
        var straddleWins = 0

        dailyGroups.forEach { dayCandles ->
            if (dayCandles.isEmpty()) return@forEach
            val mockMarketData = marketData.copy(
                ltp = dayCandles.last().ohlc.close,
                ohlc = dayCandles.last().ohlc
            )
            val straddleResult = straddleEngine.evaluate(mockMarketData, dayCandles)
            if (straddleResult.isRecommended) {
                straddleTotal++
                val highOfDay = dayCandles.maxOf { it.ohlc.high }
                val lowOfDay = dayCandles.minOf { it.ohlc.low }
                if (highOfDay <= straddleResult.breakEvenUpper && lowOfDay >= straddleResult.breakEvenLower) {
                    straddleWins++
                }
            }
        }

        val gtiAccuracy = if (gtiTotal > 0) (gtiProfits.toDouble() / gtiTotal) * 100.0 else 0.0
        val straddleWinRate = if (straddleTotal > 0) (straddleWins.toDouble() / straddleTotal) * 100.0 else 0.0
        val combined = (gtiAccuracy * 0.5 + straddleWinRate * 0.5)

        return BacktestResult(
            symbol = marketData.symbol,
            period = period,
            gtiAccuracyPct = gtiAccuracy,
            straddleWinRatePct = straddleWinRate,
            combinedReturnPct = combined,
            totalGtiTrades = gtiTotal,
            totalStraddleTrades = straddleTotal,
            gtiProfitTrades = gtiProfits,
            straddleWinTrades = straddleWins
        )
    }

    private fun didSignalHitTarget(signal: Signal, futureCa: List<Candle>): Boolean {
        return when (signal.type) {
            SignalType.BUY_CALL -> futureCa.any { it.ohlc.high >= signal.target }
            SignalType.BUY_PUT -> futureCa.any { it.ohlc.low <= signal.target }
        }
    }

    private fun emptyResult(symbol: Symbol, period: BacktestPeriod) = BacktestResult(
        symbol = symbol, period = period,
        gtiAccuracyPct = 0.0, straddleWinRatePct = 0.0, combinedReturnPct = 0.0,
        totalGtiTrades = 0, totalStraddleTrades = 0, gtiProfitTrades = 0, straddleWinTrades = 0
    )
}
