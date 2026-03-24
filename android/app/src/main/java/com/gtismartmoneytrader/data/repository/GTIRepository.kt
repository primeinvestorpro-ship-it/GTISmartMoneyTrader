package com.gtismartmoneytrader.data.repository

import com.gtismartmoneytrader.data.api.GTIApiService
import com.gtismartmoneytrader.data.local.dao.*
import com.gtismartmoneytrader.data.local.entity.*
import com.gtismartmoneytrader.data.model.*
import com.gtismartmoneytrader.domain.model.*
import com.gtismartmoneytrader.domain.usecase.MarketDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GTIRepository @Inject constructor(
    private val apiService: GTIApiService,
    private val signalDao: SignalDao,
    private val tradeDao: TradeDao,
    private val settingDao: SettingDao,
    private val candleDao: CandleDao
) : MarketDataRepository {

    override fun getMarketDataFlow(symbol: Symbol): Flow<MarketData> = flow {
        // In a real app, this would connect to WebSocket for real-time data
        // For now, we'll use polling
        while (true) {
            try {
                val response = apiService.getMarketData(symbol.displayName)
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()!!
                    emit(data.toDomain(symbol))
                }
            } catch (e: Exception) {
                // Handle error
            }
            kotlinx.coroutines.delay(1000) // Poll every second
        }
    }

    override suspend fun getOptionChain(symbol: Symbol, expiry: String): OptionChain? {
        return try {
            val response = apiService.getOptionChain(symbol.displayName, 0)
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.toDomain()
            } else null
        } catch (e: Exception) {
            null
        }
    }

    // Signal operations
    fun getAllSignals(): Flow<List<Signal>> {
        return signalDao.getAllSignals().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    fun getSignalsBySymbol(symbol: Symbol): Flow<List<Signal>> {
        return signalDao.getSignalsBySymbol(symbol.displayName).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun getLatestActiveSignal(): Signal? {
        return signalDao.getLatestActiveSignal()?.toDomain()
    }

    suspend fun saveSignal(signal: Signal) {
        signalDao.insertSignal(signal.toEntity())
    }

    suspend fun acknowledgeSignal(signalId: String) {
        signalDao.acknowledgeSignal(signalId)
    }

    suspend fun deactivateSignal(signalId: String) {
        signalDao.deactivateSignal(signalId)
    }

    // Trade operations
    fun getAllTrades(): Flow<List<Trade>> {
        return tradeDao.getAllTrades().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    fun getPaperTrades(): Flow<List<Trade>> {
        return tradeDao.getTradesByType(true).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun saveTrade(trade: Trade) {
        tradeDao.insertTrade(trade.toEntity())
    }

    suspend fun getTradeStats(): TradeStats {
        val total = tradeDao.getTotalTradeCount()
        val winning = tradeDao.getWinningTradeCount()
        val pnl = tradeDao.getTotalPnL() ?: 0.0
        
        return TradeStats(
            totalTrades = total,
            winningTrades = winning,
            losingTrades = total - winning,
            winRate = if (total > 0) (winning.toDouble() / total) * 100 else 0.0,
            totalPnL = pnl
        )
    }

    // Settings operations
    suspend fun getSetting(key: String): String? {
        return settingDao.getSetting(key)?.value
    }

    suspend fun saveSetting(key: String, value: String) {
        settingDao.insertSetting(SettingEntity(key, value))
    }

    // Candle operations
    fun getRecentCandles(symbol: Symbol, timeframe: Timeframe, limit: Int = 100): Flow<List<Candle>> {
        return candleDao.getRecentCandles(symbol.displayName, timeframe.name, limit).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun saveCandle(candle: Candle, symbol: Symbol, timeframe: Timeframe) {
        candleDao.insertCandle(candle.toEntity(symbol, timeframe))
    }

    suspend fun getLatestCandle(symbol: Symbol, timeframe: Timeframe): Candle? {
        return candleDao.getLatestCandle(symbol.displayName, timeframe.name)?.toDomain()
    }
}

// Extension functions for mapping
private fun MarketDataResponse.toDomain(symbol: Symbol): MarketData {
    return MarketData(
        symbol = symbol,
        timestamp = System.currentTimeMillis(),
        ltp = ohlc.close,
        ohlc = OHLC(ohlc.open, ohlc.high, ohlc.low, ohlc.close),
        volume = volume,
        atr = atr,
        change = ohlc.close - ohlc.open,
        changePercent = ((ohlc.close - ohlc.open) / ohlc.open) * 100
    )
}

private fun OptionChainResponse.toDomain(): OptionChain {
    return OptionChain(
        symbol = Symbol.NIFTY,
        underlyingPrice = underlyingPrice,
        atmStrike = atmStrike,
        expiry = expiry,
        timestamp = System.currentTimeMillis(),
        options = options.map { it.toDomain() }
    )
}

private fun OptionDataResponse.toDomain(): OptionData {
    return OptionData(
        strike = strike,
        type = type,
        ltp = ltp,
        bid = bid,
        ask = ask,
        volume = volume,
        openInterest = oi,
        delta = delta,
        gamma = gamma,
        theta = theta,
        vega = vega,
        iv = iv,
        symbol = Symbol.NIFTY,
        expiry = ""
    )
}

private fun SignalEntity.toDomain(): Signal {
    return Signal(
        id = id,
        type = SignalType.valueOf(type),
        confidence = ConfidenceLevel.valueOf(confidence),
        entryPrice = entryPrice,
        stopLoss = stopLoss,
        target = target,
        strike = strike,
        optionType = optionType,
        timestamp = timestamp,
        symbol = symbol,
        candlePattern = CandlePattern(false, 0.0, CandleType.BLUE)
    )
}

private fun Signal.toEntity(): SignalEntity {
    return SignalEntity(
        id = id,
        symbol = symbol,
        timestamp = timestamp,
        type = type.name,
        confidence = confidence.name,
        entryPrice = entryPrice,
        stopLoss = stopLoss,
        target = target,
        strike = strike,
        optionType = optionType
    )
}

private fun CandleEntity.toDomain(): Candle {
    return Candle(
        timestamp = timestamp,
        ohlc = OHLC(open, high, low, close),
        volume = volume,
        atr = atr,
        candleType = CandleType.valueOf(candleType)
    )
}

private fun Candle.toEntity(symbol: Symbol, timeframe: Timeframe): CandleEntity {
    return CandleEntity(
        id = "${symbol.displayName}_${timeframe.name}_$timestamp",
        symbol = symbol.displayName,
        timestamp = timestamp,
        open = ohlc.open,
        high = ohlc.high,
        low = ohlc.low,
        close = ohlc.close,
        volume = volume,
        atr = atr,
        candleType = candleType.name,
        timeframe = timeframe.name
    )
}

data class Trade(
    val id: Long = 0,
    val signalId: String,
    val action: TradeAction,
    val entryPrice: Double?,
    val entryTime: Long?,
    val exitPrice: Double?,
    val exitTime: Long?,
    val pnl: Double?,
    val pnlPercent: Double?,
    val isPaperTrade: Boolean = false
)

enum class TradeAction {
    TAKEN, MISSED, IGNORED
}

private fun TradeEntity.toDomain(): Trade {
    return Trade(
        id = id,
        signalId = signalId,
        action = TradeAction.valueOf(action),
        entryPrice = entryPrice,
        entryTime = entryTime,
        exitPrice = exitPrice,
        exitTime = exitTime,
        pnl = pnl,
        pnlPercent = pnlPercent,
        isPaperTrade = isPaperTrade
    )
}

private fun Trade.toEntity(): TradeEntity {
    return TradeEntity(
        id = id,
        signalId = signalId,
        action = action.name,
        entryPrice = entryPrice,
        entryTime = entryTime,
        exitPrice = exitPrice,
        exitTime = exitTime,
        pnl = pnl,
        pnlPercent = pnlPercent,
        isPaperTrade = isPaperTrade
    )
}

data class TradeStats(
    val totalTrades: Int,
    val winningTrades: Int,
    val losingTrades: Int,
    val winRate: Double,
    val totalPnL: Double
)
