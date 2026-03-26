package com.gtismartmoneytrader.domain.model

enum class VolatilityRisk { LOW, MEDIUM, HIGH }

data class StraddleResult(
    val symbol: Symbol,
    val atmStrike: Int,
    val isRecommended: Boolean,
    val probabilityOfProfit: Double,       // 0–100 %
    val expectedRangeLow: Double,
    val expectedRangeHigh: Double,
    val totalPremium: Double,              // CE + PE ATM premium combined
    val breakEvenUpper: Double,            // ATM + premium
    val breakEvenLower: Double,            // ATM – premium
    val ivPercentile: Double,              // Rolling IV rank 0–100
    val volatilityRisk: VolatilityRisk,
    val timestamp: Long = System.currentTimeMillis(),
    val expiry: String = ""
)
