package com.gtismartmoneytrader.data.model

import com.google.gson.annotations.SerializedName

data class MarketDataResponse(
    @SerializedName("symbol") val symbol: String,
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("ohlc") val ohlc: OHLCResponse,
    @SerializedName("volume") val volume: Long,
    @SerializedName("atr") val atr: Double
)

data class OHLCResponse(
    @SerializedName("open") val open: Double,
    @SerializedName("high") val high: Double,
    @SerializedName("low") val low: Double,
    @SerializedName("close") val close: Double
)

data class SignalResponse(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("confidence") val confidence: String,
    @SerializedName("entryPrice") val entryPrice: Double,
    @SerializedName("stopLoss") val stopLoss: Double,
    @SerializedName("target") val target: Double,
    @SerializedName("strike") val strike: Int,
    @SerializedName("optionType") val optionType: String,
    @SerializedName("timestamp") val timestamp: Long,
    @SerializedName("symbol") val symbol: String
)

data class SignalsListResponse(
    @SerializedName("symbol") val symbol: String,
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("signals") val signals: List<SignalResponse>
)

data class OptionChainResponse(
    @SerializedName("symbol") val symbol: String,
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("underlyingPrice") val underlyingPrice: Double,
    @SerializedName("atmStrike") val atmStrike: Int,
    @SerializedName("expiry") val expiry: String,
    @SerializedName("options") val options: List<OptionDataResponse>
)

data class OptionDataResponse(
    @SerializedName("strike") val strike: Int,
    @SerializedName("type") val type: String,
    @SerializedName("ltp") val ltp: Double,
    @SerializedName("bid") val bid: Double,
    @SerializedName("ask") val ask: Double,
    @SerializedName("volume") val volume: Long,
    @SerializedName("oi") val oi: Long,
    @SerializedName("delta") val delta: Double?,
    @SerializedName("gamma") val gamma: Double?,
    @SerializedName("theta") val theta: Double?,
    @SerializedName("vega") val vega: Double?,
    @SerializedName("iv") val iv: Double?
)

data class TradeLogRequest(
    @SerializedName("signalId") val signalId: String,
    @SerializedName("action") val action: String,
    @SerializedName("entryPrice") val entryPrice: Double?,
    @SerializedName("entryTime") val entryTime: Long?,
    @SerializedName("exitPrice") val exitPrice: Double?,
    @SerializedName("exitTime") val exitTime: Long?,
    @SerializedName("pnl") val pnl: Double?,
    @SerializedName("pnlPercent") val pnlPercent: Double?
)

data class TradeLogResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("tradeId") val tradeId: String?
)
