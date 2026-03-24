package com.gtismartmoneytrader.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gtismartmoneytrader.data.local.dao.*
import com.gtismartmoneytrader.data.local.entity.*

@Database(
    entities = [
        SignalEntity::class,
        TradeEntity::class,
        SettingEntity::class,
        CandleEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class GTIDatabase : RoomDatabase() {
    abstract fun signalDao(): SignalDao
    abstract fun tradeDao(): TradeDao
    abstract fun settingDao(): SettingDao
    abstract fun candleDao(): CandleDao
    
    companion object {
        const val DATABASE_NAME = "gti_database"
    }
}
