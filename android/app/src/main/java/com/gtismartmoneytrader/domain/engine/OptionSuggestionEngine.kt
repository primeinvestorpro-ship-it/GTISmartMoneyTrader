package com.gtismartmoneytrader.domain.engine

import com.gtismartmoneytrader.domain.model.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.abs
import kotlin.math.roundToInt

@Singleton
class OptionSuggestionEngine @Inject constructor() {

    companion object {
        private const val NIFTY_LOT_SIZE = 50
        private const val BANKNIFTY_LOT_SIZE = 15
        private const val MINIMUM_STRIKE_DISTANCE = 100
    }

    fun calculateATMStrike(symbol: Symbol, underlyingPrice: Double): Int {
        val lotSize = when (symbol) {
            Symbol.NIFTY -> NIFTY_LOT_SIZE
            Symbol.BANKNIFTY -> BANKNIFTY_LOT_SIZE
        }

        // Round to nearest lot size
        val roundedPrice = (underlyingPrice / lotSize).roundToInt() * lotSize
        
        return roundedPrice
    }

    fun calculateITMStrike(symbol: Symbol, atmStrike: Int, stepsAway: Int): Int {
        val strikeDistance = when (symbol) {
            Symbol.NIFTY -> MINIMUM_STRIKE_DISTANCE
            Symbol.BANKNIFTY -> MINIMUM_STRIKE_DISTANCE * 2
        }
        
        return atmStrike - (stepsAway * strikeDistance)
    }

    fun calculateOTMStrike(symbol: Symbol, atmStrike: Int, stepsAway: Int): Int {
        val strikeDistance = when (symbol) {
            Symbol.NIFTY -> MINIMUM_STRIKE_DISTANCE
            Symbol.BANKNIFTY -> MINIMUM_STRIKE_DISTANCE * 2
        }
        
        return atmStrike + (stepsAway * strikeDistance)
    }

    fun suggestOption(
        signal: Signal,
        underlyingPrice: Double,
        optionChain: OptionChain?
    ): ATMOption? {
        val atmStrike = calculateATMStrike(
            when (signal.symbol) {
                "NIFTY" -> Symbol.NIFTY
                "BANKNIFTY" -> Symbol.BANKNIFTY
                else -> Symbol.NIFTY
            },
            underlyingPrice
        )

        // Find ATM option from chain if available
        if (optionChain != null) {
            val atmOption = optionChain.options.find {
                it.strike == atmStrike && it.type == signal.optionType
            }
            
            if (atmOption != null) {
                return ATMOption.fromOptionData(atmOption)
            }
        }

        // Return basic option suggestion
        return ATMOption(
            strike = atmStrike,
            type = signal.optionType,
            ltp = signal.entryPrice,
            delta = calculateDelta(signal.entryPrice, underlyingPrice, signal.optionType == "CE"),
            iv = null,
            expiry = getNextWeeklyExpiry(),
            symbol = when (signal.symbol) {
                "NIFTY" -> Symbol.NIFTY
                "BANKNIFTY" -> Symbol.BANKNIFTY
                else -> Symbol.NIFTY
            }
        )
    }

    private fun calculateDelta(optionPrice: Double, underlyingPrice: Double, isCall: Boolean): Double {
        // Simplified delta calculation
        val strike = calculateATMStrike(Symbol.NIFTY, underlyingPrice)
        val distanceFromATM = abs(underlyingPrice - strike) / underlyingPrice
        
        val baseDelta = if (isCall) 0.5 else -0.5
        val deltaAdjustment = if (isCall) -distanceFromATM else distanceFromATM
        
        return (baseDelta + deltaAdjustment).coerceIn(if (isCall) 0.0 else -1.0, if (isCall) 1.0 else 0.0)
    }

    fun getNextWeeklyExpiry(): String {
        val calendar = java.util.Calendar.getInstance()
        
        // Find next Thursday
        while (calendar.get(java.util.Calendar.DAY_OF_WEEK) != java.util.Calendar.THURSDAY) {
            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1)
        }
        
        // If it's past 3:30 PM IST, move to next Thursday
        val hour = calendar.get(java.util.Calendar.HOUR_OF_DAY)
        val minute = calendar.get(java.util.Calendar.MINUTE)
        if (hour > 15 || (hour == 15 && minute > 30)) {
            calendar.add(java.util.Calendar.WEEK_OF_YEAR, 1)
        }
        
        val dateFormat = java.text.SimpleDateFormat("ddMMM", java.util.Locale.US)
        return dateFormat.format(calendar.time).uppercase()
    }

    fun calculateRiskReward(signal: Signal): Pair<Double, Double> {
        val risk = abs(signal.entryPrice - signal.stopLoss)
        val reward = abs(signal.target - signal.entryPrice)
        val ratio = if (risk > 0) reward / risk else 0.0
        
        return Pair(risk, reward)
    }

    fun suggestPositionSize(
        accountBalance: Double,
        riskPerTradePercent: Double,
        entryPrice: Double,
        stopLoss: Double
    ): Int {
        val riskAmount = accountBalance * (riskPerTradePercent / 100)
        val riskPerLot = abs(entryPrice - stopLoss)
        
        if (riskPerLot <= 0) return 0
        
        val lots = (riskAmount / riskPerLot).toInt()
        
        return maxOf(1, lots)
    }
}
