package com.gtismartmoneytrader.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "signals")
data class SignalEntity(
    @PrimaryKey val id: String,
    val symbol: String,
    val timestamp: Long,
    val type: String,
    val confidence: String,
    val entryPrice: Double,
    val stopLoss: Double,
    val target: Double,
    val strike: Int,
    val optionType: String,
    val isAcknowledged: Boolean = false,
    val isActive: Boolean = true
)

@Entity(tableName = "trades")
data class TradeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val signalId: String,
    val action: String,
    val entryPrice: Double?,
    val entryTime: Long?,
    val exitPrice: Double?,
    val exitTime: Long?,
    val pnl: Double?,
    val pnlPercent: Double?,
    val isPaperTrade: Boolean = false
)

@Entity(tableName = "settings")
data class SettingEntity(
    @PrimaryKey val key: String,
    val value: String
)

@Entity(tableName = "candles")
data class CandleEntity(
    @PrimaryKey val id: String,
    val symbol: String,
    val timestamp: Long,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Long,
    val atr: Double,
    val candleType: String,
    val timeframe: String
)
