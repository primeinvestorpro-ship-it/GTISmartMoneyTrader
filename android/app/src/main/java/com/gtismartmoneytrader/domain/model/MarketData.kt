package com.gtismartmoneytrader.domain.model

enum class Symbol(val displayName: String, val exchange: String, val token: String) {
    NIFTY("NIFTY", "NSE", "26000"),
    BANKNIFTY("BANKNIFTY", "NSE", "26001")
}

data class MarketData(
    val symbol: Symbol,
    val timestamp: Long,
    val ltp: Double,
    val ohlc: OHLC,
    val volume: Long,
    val atr: Double,
    val change: Double,
    val changePercent: Double
)

data class OptionData(
    val strike: Int,
    val type: String,
    val ltp: Double,
    val bid: Double,
    val ask: Double,
    val volume: Long,
    val openInterest: Long,
    val delta: Double?,
    val gamma: Double?,
    val theta: Double?,
    val vega: Double?,
    val iv: Double?,
    val symbol: Symbol,
    val expiry: String
)

data class OptionChain(
    val symbol: Symbol,
    val underlyingPrice: Double,
    val atmStrike: Int,
    val expiry: String,
    val timestamp: Long,
    val options: List<OptionData>
)

data class ATMOption(
    val strike: Int,
    val type: String,
    val ltp: Double,
    val delta: Double?,
    val iv: Double?,
    val expiry: String,
    val symbol: Symbol
) {
    companion object {
        fun fromOptionData(optionData: OptionData): ATMOption {
            return ATMOption(
                strike = optionData.strike,
                type = optionData.type,
                ltp = optionData.ltp,
                delta = optionData.delta,
                iv = optionData.iv,
                expiry = optionData.expiry,
                symbol = optionData.symbol
            )
        }
    }
}
