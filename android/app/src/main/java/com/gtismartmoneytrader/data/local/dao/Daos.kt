package com.gtismartmoneytrader.data.local.dao

import androidx.room.*
import com.gtismartmoneytrader.data.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SignalDao {
    @Query("SELECT * FROM signals ORDER BY timestamp DESC")
    fun getAllSignals(): Flow<List<SignalEntity>>
    
    @Query("SELECT * FROM signals WHERE symbol = :symbol ORDER BY timestamp DESC")
    fun getSignalsBySymbol(symbol: String): Flow<List<SignalEntity>>
    
    @Query("SELECT * FROM signals WHERE id = :id")
    suspend fun getSignalById(id: String): SignalEntity?
    
    @Query("SELECT * FROM signals WHERE isActive = 1 ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestActiveSignal(): SignalEntity?
    
    @Query("SELECT * FROM signals ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentSignals(limit: Int): Flow<List<SignalEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSignal(signal: SignalEntity)
    
    @Update
    suspend fun updateSignal(signal: SignalEntity)
    
    @Query("UPDATE signals SET isActive = 0 WHERE id = :id")
    suspend fun deactivateSignal(id: String)
    
    @Query("UPDATE signals SET isAcknowledged = 1 WHERE id = :id")
    suspend fun acknowledgeSignal(id: String)
    
    @Delete
    suspend fun deleteSignal(signal: SignalEntity)
    
    @Query("DELETE FROM signals WHERE timestamp < :timestamp")
    suspend fun deleteOldSignals(timestamp: Long)
}

@Dao
interface TradeDao {
    @Query("SELECT * FROM trades ORDER BY entryTime DESC")
    fun getAllTrades(): Flow<List<TradeEntity>>
    
    @Query("SELECT * FROM trades WHERE signalId = :signalId")
    suspend fun getTradeBySignalId(signalId: String): TradeEntity?
    
    @Query("SELECT * FROM trades WHERE isPaperTrade = :isPaper ORDER BY entryTime DESC")
    fun getTradesByType(isPaper: Boolean): Flow<List<TradeEntity>>
    
    @Query("SELECT COUNT(*) FROM trades")
    suspend fun getTotalTradeCount(): Int
    
    @Query("SELECT COUNT(*) FROM trades WHERE pnl > 0")
    suspend fun getWinningTradeCount(): Int
    
    @Query("SELECT SUM(pnl) FROM trades")
    suspend fun getTotalPnL(): Double?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrade(trade: TradeEntity): Long
    
    @Update
    suspend fun updateTrade(trade: TradeEntity)
    
    @Delete
    suspend fun deleteTrade(trade: TradeEntity)
}

@Dao
interface SettingDao {
    @Query("SELECT * FROM settings WHERE `key` = :key")
    suspend fun getSetting(key: String): SettingEntity?
    
    @Query("SELECT * FROM settings")
    fun getAllSettings(): Flow<List<SettingEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetting(setting: SettingEntity)
    
    @Query("DELETE FROM settings WHERE `key` = :key")
    suspend fun deleteSetting(key: String)
    
    @Query("DELETE FROM settings")
    suspend fun deleteAllSettings()
}

@Dao
interface CandleDao {
    @Query("SELECT * FROM candles WHERE symbol = :symbol AND timeframe = :timeframe ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentCandles(symbol: String, timeframe: String, limit: Int): Flow<List<CandleEntity>>
    
    @Query("SELECT * FROM candles WHERE symbol = :symbol AND timeframe = :timeframe AND timestamp >= :fromTimestamp ORDER BY timestamp ASC")
    fun getCandlesFrom(symbol: String, timeframe: String, fromTimestamp: Long): Flow<List<CandleEntity>>
    
    @Query("SELECT * FROM candles WHERE symbol = :symbol AND timeframe = :timeframe ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestCandle(symbol: String, timeframe: String): CandleEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCandle(candle: CandleEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCandles(candles: List<CandleEntity>)
    
    @Query("DELETE FROM candles WHERE symbol = :symbol AND timeframe = :timeframe AND timestamp < :timestamp")
    suspend fun deleteOldCandles(symbol: String, timeframe: String, timestamp: Long)
}
