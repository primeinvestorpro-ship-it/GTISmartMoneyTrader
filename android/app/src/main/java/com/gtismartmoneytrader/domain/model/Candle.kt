package com.gtismartmoneytrader.domain.model

enum class CandleType {
    YELLOW,  // Smart Money Interest (Accumulation)
    BLUE,    // Buying Pressure (Bullish Momentum)
    BLACK    // Selling Pressure (Bearish Momentum)
}

data class OHLC(
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double
)

data class Candle(
    val timestamp: Long,
    val ohlc: OHLC,
    val volume: Long,
    val atr: Double,
    val candleType: CandleType,
    val isBullish: Boolean = ohlc.close > ohlc.open
)

data class GTICandle(
    val timestamp: Long,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Long,
    val atr: Double,
    val candleType: CandleType,
    val isBullish: Boolean = close > open
) {
    companion object {
        fun fromCandle(candle: Candle): GTICandle {
            return GTICandle(
                timestamp = candle.timestamp,
                open = candle.ohlc.open,
                high = candle.ohlc.high,
                low = candle.ohlc.low,
                close = candle.ohlc.close,
                volume = candle.volume,
                atr = candle.atr,
                candleType = candle.candleType,
                isBullish = candle.isBullish
            )
        }
    }
}
