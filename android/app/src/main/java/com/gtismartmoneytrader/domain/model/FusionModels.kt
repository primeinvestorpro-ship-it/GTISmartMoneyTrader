package com.gtismartmoneytrader.domain.model

data class FusionDecision(
    val action: FusionAction,
    val marketMode: MarketMode,
    val gtiSignal: SignalType?,         // null = no GTI signal
    val gtiStrength: MomentumStrength,
    val marketBias: MarketBias,
    val straddleResult: StraddleResult?,
    val confidenceScore: Int,           // 0–100
    val reasoning: String,
    val recommendedStrike: Int? = null,
    val timestamp: Long = System.currentTimeMillis()
)

data class BacktestResult(
    val symbol: Symbol,
    val period: BacktestPeriod,
    val gtiAccuracyPct: Double,          // % of GTI signals that hit target
    val straddleWinRatePct: Double,      // % of straddle days that expired profitably
    val combinedReturnPct: Double,       // blended return %
    val totalGtiTrades: Int,
    val totalStraddleTrades: Int,
    val gtiProfitTrades: Int,
    val straddleWinTrades: Int,
    val timestamp: Long = System.currentTimeMillis()
)

enum class BacktestPeriod(val days: Int, val label: String) {
    WEEK_1(7, "7D"),
    MONTH_1(30, "30D"),
    MONTH_3(90, "90D")
}
