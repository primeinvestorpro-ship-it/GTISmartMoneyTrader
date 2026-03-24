package com.gtismartmoneytrader.domain.model

data class UserSettings(
    val selectedSymbol: Symbol = Symbol.NIFTY,
    val selectedTimeframe: Timeframe = Timeframe.FIVE_MIN,
    val stopLossPercent: Double = 20.0,
    val targetRatio: Double = 2.0,
    val trailingSLEnabled: Boolean = true,
    val trailingSLThreshold: Double = 20.0,
    val atrThreshold: Double = 50.0,
    val volumeThresholdPercent: Double = 50.0,
    val adxThreshold: Double = 25.0,
    val enableGTIAlerts: Boolean = true,
    val enableSignalAlerts: Boolean = true,
    val enableCallAlerts: Boolean = true,
    val enablePutAlerts: Boolean = true,
    val paperTradingEnabled: Boolean = true,
    val paperTradingBalance: Double = 100000.0,
    val isDarkMode: Boolean = true
)

enum class Timeframe(val minutes: Int, val displayName: String) {
    ONE_MIN(1, "1m"),
    FIVE_MIN(5, "5m"),
    FIFTEEN_MIN(15, "15m"),
    THIRTY_MIN(30, "30m"),
    ONE_HOUR(60, "1h")
}

data class TradingWindow(
    val morningStart: String = "09:25",
    val morningEnd: String = "11:30",
    val afternoonStart: String = "13:30",
    val afternoonEnd: String = "15:15",
    val timezone: String = "IST"
)
