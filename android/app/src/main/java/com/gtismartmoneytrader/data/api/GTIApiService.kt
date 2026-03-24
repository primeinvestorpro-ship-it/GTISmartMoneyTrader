package com.gtismartmoneytrader.data.api

import com.gtismartmoneytrader.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface GTIApiService {

    @GET("api/v1/market-data/{symbol}")
    suspend fun getMarketData(
        @Path("symbol") symbol: String
    ): Response<MarketDataResponse>

    @GET("api/v1/signals/{symbol}")
    suspend fun getSignals(
        @Path("symbol") symbol: String
    ): Response<SignalsListResponse>

    @GET("api/v1/option-chain/{symbol}/{strike}")
    suspend fun getOptionChain(
        @Path("symbol") symbol: String,
        @Path("strike") strike: Int
    ): Response<OptionChainResponse>

    @POST("api/v1/trades/log")
    suspend fun logTrade(
        @Body tradeLog: TradeLogRequest
    ): Response<TradeLogResponse>

    @GET("api/v1/user/profile")
    suspend fun getUserProfile(): Response<UserProfileResponse>

    @GET("api/v1/user/settings")
    suspend fun getUserSettings(): Response<UserSettingsResponse>

    @PUT("api/v1/user/settings")
    suspend fun updateUserSettings(
        @Body settings: UserSettingsRequest
    ): Response<UserSettingsResponse>
}

data class UserProfileResponse(
    val id: String,
    val email: String,
    val name: String,
    val isPremium: Boolean,
    val brokerConnected: Boolean
)

data class UserSettingsResponse(
    val selectedSymbol: String,
    val selectedTimeframe: String,
    val stopLossPercent: Double,
    val targetRatio: Double,
    val trailingSLEnabled: Boolean,
    val trailingSLThreshold: Double,
    val atrThreshold: Double,
    val volumeThresholdPercent: Double,
    val adxThreshold: Double,
    val enableGTIAlerts: Boolean,
    val enableSignalAlerts: Boolean,
    val enableCallAlerts: Boolean,
    val enablePutAlerts: Boolean,
    val paperTradingEnabled: Boolean
)

data class UserSettingsRequest(
    val selectedSymbol: String? = null,
    val selectedTimeframe: String? = null,
    val stopLossPercent: Double? = null,
    val targetRatio: Double? = null,
    val trailingSLEnabled: Boolean? = null,
    val trailingSLThreshold: Double? = null,
    val atrThreshold: Double? = null,
    val volumeThresholdPercent: Double? = null,
    val adxThreshold: Double? = null,
    val enableGTIAlerts: Boolean? = null,
    val enableSignalAlerts: Boolean? = null,
    val enableCallAlerts: Boolean? = null,
    val enablePutAlerts: Boolean? = null,
    val paperTradingEnabled: Boolean? = null
)
