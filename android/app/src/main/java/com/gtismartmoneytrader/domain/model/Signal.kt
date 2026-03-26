package com.gtismartmoneytrader.domain.model

enum class SignalType {
    BUY_CALL,
    BUY_PUT
}

enum class MomentumStrength { LOW, MEDIUM, HIGH }

enum class MarketBias { BULLISH, BEARISH, NEUTRAL }

enum class FusionAction {
    OPTION_BUY,       // Strong GTI trend – buy call/put
    SELL_STRADDLE,    // Low trend + high IV – sell straddle
    AVOID_CONFLICT,   // Conflicting signals – stay out
    NO_TRADE          // No clear setup
}

enum class MarketMode {
    TRENDING,         // GTI strong signal active
    NEUTRAL,          // Range bound, straddle territory
    VOLATILE          // Conflicting / unpredictable
}

enum class ConfidenceLevel {
    HIGH,
    MEDIUM,
    LOW
}

enum class MarketStatus {
    TRENDING,
    SIDEWAYS
}


enum class TradingStatus {
    OPEN,      // Within allowed trading hours
    CLOSED     // Outside allowed trading hours
}

data class Signal(
    val id: String,
    val type: SignalType,
    val confidence: ConfidenceLevel,
    val entryPrice: Double,
    val stopLoss: Double,
    val target: Double,
    val strike: Int,
    val optionType: String,
    val timestamp: Long,
    val symbol: String,
    val candlePattern: CandlePattern,
    val riskReward: Double = (target - entryPrice) / (entryPrice - stopLoss)
)

data class CandlePattern(
    val yellowCandle: Boolean,
    val breakoutLevel: Double,
    val currentCandle: CandleType
)

data class TradingSignal(
    val signal: Signal,
    val underlyingPrice: Double,
    val optionLtp: Double,
    val delta: Double?,
    val iv: Double?,
    val expiry: String,
    val isActive: Boolean = true
)
