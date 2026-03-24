package com.gtismartmoneytrader.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.gtismartmoneytrader.data.local.dao.CandleDao;
import com.gtismartmoneytrader.data.local.dao.CandleDao_Impl;
import com.gtismartmoneytrader.data.local.dao.SettingDao;
import com.gtismartmoneytrader.data.local.dao.SettingDao_Impl;
import com.gtismartmoneytrader.data.local.dao.SignalDao;
import com.gtismartmoneytrader.data.local.dao.SignalDao_Impl;
import com.gtismartmoneytrader.data.local.dao.TradeDao;
import com.gtismartmoneytrader.data.local.dao.TradeDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class GTIDatabase_Impl extends GTIDatabase {
  private volatile SignalDao _signalDao;

  private volatile TradeDao _tradeDao;

  private volatile SettingDao _settingDao;

  private volatile CandleDao _candleDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `signals` (`id` TEXT NOT NULL, `symbol` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `type` TEXT NOT NULL, `confidence` TEXT NOT NULL, `entryPrice` REAL NOT NULL, `stopLoss` REAL NOT NULL, `target` REAL NOT NULL, `strike` INTEGER NOT NULL, `optionType` TEXT NOT NULL, `isAcknowledged` INTEGER NOT NULL, `isActive` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `trades` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `signalId` TEXT NOT NULL, `action` TEXT NOT NULL, `entryPrice` REAL, `entryTime` INTEGER, `exitPrice` REAL, `exitTime` INTEGER, `pnl` REAL, `pnlPercent` REAL, `isPaperTrade` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `settings` (`key` TEXT NOT NULL, `value` TEXT NOT NULL, PRIMARY KEY(`key`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `candles` (`id` TEXT NOT NULL, `symbol` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `open` REAL NOT NULL, `high` REAL NOT NULL, `low` REAL NOT NULL, `close` REAL NOT NULL, `volume` INTEGER NOT NULL, `atr` REAL NOT NULL, `candleType` TEXT NOT NULL, `timeframe` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '6ecedba525e349aec321f79e237943c4')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `signals`");
        db.execSQL("DROP TABLE IF EXISTS `trades`");
        db.execSQL("DROP TABLE IF EXISTS `settings`");
        db.execSQL("DROP TABLE IF EXISTS `candles`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsSignals = new HashMap<String, TableInfo.Column>(12);
        _columnsSignals.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSignals.put("symbol", new TableInfo.Column("symbol", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSignals.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSignals.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSignals.put("confidence", new TableInfo.Column("confidence", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSignals.put("entryPrice", new TableInfo.Column("entryPrice", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSignals.put("stopLoss", new TableInfo.Column("stopLoss", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSignals.put("target", new TableInfo.Column("target", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSignals.put("strike", new TableInfo.Column("strike", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSignals.put("optionType", new TableInfo.Column("optionType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSignals.put("isAcknowledged", new TableInfo.Column("isAcknowledged", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSignals.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSignals = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSignals = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSignals = new TableInfo("signals", _columnsSignals, _foreignKeysSignals, _indicesSignals);
        final TableInfo _existingSignals = TableInfo.read(db, "signals");
        if (!_infoSignals.equals(_existingSignals)) {
          return new RoomOpenHelper.ValidationResult(false, "signals(com.gtismartmoneytrader.data.local.entity.SignalEntity).\n"
                  + " Expected:\n" + _infoSignals + "\n"
                  + " Found:\n" + _existingSignals);
        }
        final HashMap<String, TableInfo.Column> _columnsTrades = new HashMap<String, TableInfo.Column>(10);
        _columnsTrades.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrades.put("signalId", new TableInfo.Column("signalId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrades.put("action", new TableInfo.Column("action", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrades.put("entryPrice", new TableInfo.Column("entryPrice", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrades.put("entryTime", new TableInfo.Column("entryTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrades.put("exitPrice", new TableInfo.Column("exitPrice", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrades.put("exitTime", new TableInfo.Column("exitTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrades.put("pnl", new TableInfo.Column("pnl", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrades.put("pnlPercent", new TableInfo.Column("pnlPercent", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrades.put("isPaperTrade", new TableInfo.Column("isPaperTrade", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTrades = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTrades = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTrades = new TableInfo("trades", _columnsTrades, _foreignKeysTrades, _indicesTrades);
        final TableInfo _existingTrades = TableInfo.read(db, "trades");
        if (!_infoTrades.equals(_existingTrades)) {
          return new RoomOpenHelper.ValidationResult(false, "trades(com.gtismartmoneytrader.data.local.entity.TradeEntity).\n"
                  + " Expected:\n" + _infoTrades + "\n"
                  + " Found:\n" + _existingTrades);
        }
        final HashMap<String, TableInfo.Column> _columnsSettings = new HashMap<String, TableInfo.Column>(2);
        _columnsSettings.put("key", new TableInfo.Column("key", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSettings.put("value", new TableInfo.Column("value", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSettings = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSettings = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSettings = new TableInfo("settings", _columnsSettings, _foreignKeysSettings, _indicesSettings);
        final TableInfo _existingSettings = TableInfo.read(db, "settings");
        if (!_infoSettings.equals(_existingSettings)) {
          return new RoomOpenHelper.ValidationResult(false, "settings(com.gtismartmoneytrader.data.local.entity.SettingEntity).\n"
                  + " Expected:\n" + _infoSettings + "\n"
                  + " Found:\n" + _existingSettings);
        }
        final HashMap<String, TableInfo.Column> _columnsCandles = new HashMap<String, TableInfo.Column>(11);
        _columnsCandles.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCandles.put("symbol", new TableInfo.Column("symbol", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCandles.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCandles.put("open", new TableInfo.Column("open", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCandles.put("high", new TableInfo.Column("high", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCandles.put("low", new TableInfo.Column("low", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCandles.put("close", new TableInfo.Column("close", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCandles.put("volume", new TableInfo.Column("volume", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCandles.put("atr", new TableInfo.Column("atr", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCandles.put("candleType", new TableInfo.Column("candleType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCandles.put("timeframe", new TableInfo.Column("timeframe", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCandles = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCandles = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCandles = new TableInfo("candles", _columnsCandles, _foreignKeysCandles, _indicesCandles);
        final TableInfo _existingCandles = TableInfo.read(db, "candles");
        if (!_infoCandles.equals(_existingCandles)) {
          return new RoomOpenHelper.ValidationResult(false, "candles(com.gtismartmoneytrader.data.local.entity.CandleEntity).\n"
                  + " Expected:\n" + _infoCandles + "\n"
                  + " Found:\n" + _existingCandles);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "6ecedba525e349aec321f79e237943c4", "141238ec55ec8edde586ddfd20c1d5f5");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "signals","trades","settings","candles");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `signals`");
      _db.execSQL("DELETE FROM `trades`");
      _db.execSQL("DELETE FROM `settings`");
      _db.execSQL("DELETE FROM `candles`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(SignalDao.class, SignalDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TradeDao.class, TradeDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SettingDao.class, SettingDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CandleDao.class, CandleDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public SignalDao signalDao() {
    if (_signalDao != null) {
      return _signalDao;
    } else {
      synchronized(this) {
        if(_signalDao == null) {
          _signalDao = new SignalDao_Impl(this);
        }
        return _signalDao;
      }
    }
  }

  @Override
  public TradeDao tradeDao() {
    if (_tradeDao != null) {
      return _tradeDao;
    } else {
      synchronized(this) {
        if(_tradeDao == null) {
          _tradeDao = new TradeDao_Impl(this);
        }
        return _tradeDao;
      }
    }
  }

  @Override
  public SettingDao settingDao() {
    if (_settingDao != null) {
      return _settingDao;
    } else {
      synchronized(this) {
        if(_settingDao == null) {
          _settingDao = new SettingDao_Impl(this);
        }
        return _settingDao;
      }
    }
  }

  @Override
  public CandleDao candleDao() {
    if (_candleDao != null) {
      return _candleDao;
    } else {
      synchronized(this) {
        if(_candleDao == null) {
          _candleDao = new CandleDao_Impl(this);
        }
        return _candleDao;
      }
    }
  }
}
