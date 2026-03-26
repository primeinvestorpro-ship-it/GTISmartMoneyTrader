package com.gtismartmoneytrader.domain.engine

import com.gtismartmoneytrader.domain.model.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Fusion AI Engine – the "Master Brain" that combines GTI + Straddle signals.
 *
 * Decision Matrix:
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ GTI Signal            │ Straddle │ → Final Action              │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ Strong (BLUE/BLACK)   │ NO       │ → OPTION_BUY                │
 * │ Neutral / No signal   │ YES      │ → SELL_STRADDLE             │
 * │ Strong (BLUE/BLACK)   │ YES      │ → AVOID_CONFLICT            │
 * │ No signal             │ NO       │ → NO_TRADE                  │
 * └─────────────────────────────────────────────────────────────────┘
 */
@Singleton
class FusionAIEngine @Inject constructor() {

    fun decide(
        latestCandles: List<Candle>,
        latestSignal: Signal?,
        straddleResult: StraddleResult,
        adx: Double
    ): FusionDecision {
        val hasStrongGTI = latestSignal != null && latestSignal.confidence == ConfidenceLevel.HIGH ||
                latestSignal?.confidence == ConfidenceLevel.MEDIUM && adx >= 25.0

        val straddleYes = straddleResult.isRecommended

        // Determine market bias
        val marketBias = when {
            latestSignal?.type == SignalType.BUY_CALL -> MarketBias.BULLISH
            latestSignal?.type == SignalType.BUY_PUT -> MarketBias.BEARISH
            adx < 20.0 -> MarketBias.NEUTRAL
            else -> MarketBias.NEUTRAL
        }

        // Momentum strength from ADX
        val momentumStrength = when {
            adx >= 35.0 -> MomentumStrength.HIGH
            adx >= 20.0 -> MomentumStrength.MEDIUM
            else -> MomentumStrength.LOW
        }

        // Apply fusion matrix
        return when {
            hasStrongGTI && !straddleYes -> buildOptionBuyDecision(
                latestSignal!!, momentumStrength, marketBias, straddleResult
            )
            !hasStrongGTI && straddleYes -> buildStraddleDecision(
                momentumStrength, marketBias, straddleResult, adx
            )
            hasStrongGTI && straddleYes -> buildConflictDecision(
                latestSignal!!, momentumStrength, marketBias, straddleResult
            )
            else -> buildNoTradeDecision(momentumStrength, marketBias, straddleResult)
        }
    }

    private fun buildOptionBuyDecision(
        signal: Signal,
        strength: MomentumStrength,
        bias: MarketBias,
        straddle: StraddleResult
    ): FusionDecision {
        val confidenceScore = when (signal.confidence) {
            ConfidenceLevel.HIGH -> 85
            ConfidenceLevel.MEDIUM -> 70
            ConfidenceLevel.LOW -> 55
        }
        val action = if (signal.type == SignalType.BUY_CALL) "BUY CALL 🔵" else "BUY PUT ⚫"
        return FusionDecision(
            action = FusionAction.OPTION_BUY,
            marketMode = MarketMode.TRENDING,
            gtiSignal = signal.type,
            gtiStrength = strength,
            marketBias = bias,
            straddleResult = straddle,
            confidenceScore = confidenceScore,
            reasoning = "Strong GTI momentum detected ($action). IV is low – straddle not active. Smart money entry confirmed.",
            recommendedStrike = signal.strike
        )
    }

    private fun buildStraddleDecision(
        strength: MomentumStrength,
        bias: MarketBias,
        straddle: StraddleResult,
        adx: Double
    ): FusionDecision {
        val pop = straddle.probabilityOfProfit
        val confidenceScore = (pop * 0.9).toInt().coerceIn(55, 85)
        return FusionDecision(
            action = FusionAction.SELL_STRADDLE,
            marketMode = MarketMode.NEUTRAL,
            gtiSignal = null,
            gtiStrength = strength,
            marketBias = bias,
            straddleResult = straddle,
            confidenceScore = confidenceScore,
            reasoning = "No directional momentum (ADX=${String.format("%.0f", adx)}). High IV Rank (${String.format("%.0f", straddle.ivPercentile)}%). Short straddle favoured. PoP: ${String.format("%.0f", pop)}%.",
            recommendedStrike = straddle.atmStrike
        )
    }

    private fun buildConflictDecision(
        signal: Signal,
        strength: MomentumStrength,
        bias: MarketBias,
        straddle: StraddleResult
    ): FusionDecision {
        return FusionDecision(
            action = FusionAction.AVOID_CONFLICT,
            marketMode = MarketMode.VOLATILE,
            gtiSignal = signal.type,
            gtiStrength = strength,
            marketBias = bias,
            straddleResult = straddle,
            confidenceScore = 25,
            reasoning = "⚠️ Conflict detected – GTI shows directional momentum but IV is also elevated. Both strategies clash. Avoid trading."
        )
    }

    private fun buildNoTradeDecision(
        strength: MomentumStrength,
        bias: MarketBias,
        straddle: StraddleResult
    ): FusionDecision {
        return FusionDecision(
            action = FusionAction.NO_TRADE,
            marketMode = MarketMode.NEUTRAL,
            gtiSignal = null,
            gtiStrength = strength,
            marketBias = bias,
            straddleResult = straddle,
            confidenceScore = 0,
            reasoning = "No clear setup. GTI signals absent. Low IV – straddle not favourable. Wait for opportunity."
        )
    }
}
