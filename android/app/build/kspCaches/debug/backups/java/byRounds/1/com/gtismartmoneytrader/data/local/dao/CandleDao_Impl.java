package com.gtismartmoneytrader.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.gtismartmoneytrader.data.local.entity.CandleEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CandleDao_Impl implements CandleDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CandleEntity> __insertionAdapterOfCandleEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldCandles;

  public CandleDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCandleEntity = new EntityInsertionAdapter<CandleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `candles` (`id`,`symbol`,`timestamp`,`open`,`high`,`low`,`close`,`volume`,`atr`,`candleType`,`timeframe`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CandleEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getSymbol());
        statement.bindLong(3, entity.getTimestamp());
        statement.bindDouble(4, entity.getOpen());
        statement.bindDouble(5, entity.getHigh());
        statement.bindDouble(6, entity.getLow());
        statement.bindDouble(7, entity.getClose());
        statement.bindLong(8, entity.getVolume());
        statement.bindDouble(9, entity.getAtr());
        statement.bindString(10, entity.getCandleType());
        statement.bindString(11, entity.getTimeframe());
      }
    };
    this.__preparedStmtOfDeleteOldCandles = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM candles WHERE symbol = ? AND timeframe = ? AND timestamp < ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertCandle(final CandleEntity candle,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCandleEntity.insert(candle);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertCandles(final List<CandleEntity> candles,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCandleEntity.insert(candles);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOldCandles(final String symbol, final String timeframe, final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldCandles.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, symbol);
        _argIndex = 2;
        _stmt.bindString(_argIndex, timeframe);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, timestamp);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteOldCandles.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<CandleEntity>> getRecentCandles(final String symbol, final String timeframe,
      final int limit) {
    final String _sql = "SELECT * FROM candles WHERE symbol = ? AND timeframe = ? ORDER BY timestamp DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, symbol);
    _argIndex = 2;
    _statement.bindString(_argIndex, timeframe);
    _argIndex = 3;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"candles"}, new Callable<List<CandleEntity>>() {
      @Override
      @NonNull
      public List<CandleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSymbol = CursorUtil.getColumnIndexOrThrow(_cursor, "symbol");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfOpen = CursorUtil.getColumnIndexOrThrow(_cursor, "open");
          final int _cursorIndexOfHigh = CursorUtil.getColumnIndexOrThrow(_cursor, "high");
          final int _cursorIndexOfLow = CursorUtil.getColumnIndexOrThrow(_cursor, "low");
          final int _cursorIndexOfClose = CursorUtil.getColumnIndexOrThrow(_cursor, "close");
          final int _cursorIndexOfVolume = CursorUtil.getColumnIndexOrThrow(_cursor, "volume");
          final int _cursorIndexOfAtr = CursorUtil.getColumnIndexOrThrow(_cursor, "atr");
          final int _cursorIndexOfCandleType = CursorUtil.getColumnIndexOrThrow(_cursor, "candleType");
          final int _cursorIndexOfTimeframe = CursorUtil.getColumnIndexOrThrow(_cursor, "timeframe");
          final List<CandleEntity> _result = new ArrayList<CandleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CandleEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSymbol;
            _tmpSymbol = _cursor.getString(_cursorIndexOfSymbol);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final double _tmpOpen;
            _tmpOpen = _cursor.getDouble(_cursorIndexOfOpen);
            final double _tmpHigh;
            _tmpHigh = _cursor.getDouble(_cursorIndexOfHigh);
            final double _tmpLow;
            _tmpLow = _cursor.getDouble(_cursorIndexOfLow);
            final double _tmpClose;
            _tmpClose = _cursor.getDouble(_cursorIndexOfClose);
            final long _tmpVolume;
            _tmpVolume = _cursor.getLong(_cursorIndexOfVolume);
            final double _tmpAtr;
            _tmpAtr = _cursor.getDouble(_cursorIndexOfAtr);
            final String _tmpCandleType;
            _tmpCandleType = _cursor.getString(_cursorIndexOfCandleType);
            final String _tmpTimeframe;
            _tmpTimeframe = _cursor.getString(_cursorIndexOfTimeframe);
            _item = new CandleEntity(_tmpId,_tmpSymbol,_tmpTimestamp,_tmpOpen,_tmpHigh,_tmpLow,_tmpClose,_tmpVolume,_tmpAtr,_tmpCandleType,_tmpTimeframe);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<CandleEntity>> getCandlesFrom(final String symbol, final String timeframe,
      final long fromTimestamp) {
    final String _sql = "SELECT * FROM candles WHERE symbol = ? AND timeframe = ? AND timestamp >= ? ORDER BY timestamp ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, symbol);
    _argIndex = 2;
    _statement.bindString(_argIndex, timeframe);
    _argIndex = 3;
    _statement.bindLong(_argIndex, fromTimestamp);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"candles"}, new Callable<List<CandleEntity>>() {
      @Override
      @NonNull
      public List<CandleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSymbol = CursorUtil.getColumnIndexOrThrow(_cursor, "symbol");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfOpen = CursorUtil.getColumnIndexOrThrow(_cursor, "open");
          final int _cursorIndexOfHigh = CursorUtil.getColumnIndexOrThrow(_cursor, "high");
          final int _cursorIndexOfLow = CursorUtil.getColumnIndexOrThrow(_cursor, "low");
          final int _cursorIndexOfClose = CursorUtil.getColumnIndexOrThrow(_cursor, "close");
          final int _cursorIndexOfVolume = CursorUtil.getColumnIndexOrThrow(_cursor, "volume");
          final int _cursorIndexOfAtr = CursorUtil.getColumnIndexOrThrow(_cursor, "atr");
          final int _cursorIndexOfCandleType = CursorUtil.getColumnIndexOrThrow(_cursor, "candleType");
          final int _cursorIndexOfTimeframe = CursorUtil.getColumnIndexOrThrow(_cursor, "timeframe");
          final List<CandleEntity> _result = new ArrayList<CandleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CandleEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSymbol;
            _tmpSymbol = _cursor.getString(_cursorIndexOfSymbol);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final double _tmpOpen;
            _tmpOpen = _cursor.getDouble(_cursorIndexOfOpen);
            final double _tmpHigh;
            _tmpHigh = _cursor.getDouble(_cursorIndexOfHigh);
            final double _tmpLow;
            _tmpLow = _cursor.getDouble(_cursorIndexOfLow);
            final double _tmpClose;
            _tmpClose = _cursor.getDouble(_cursorIndexOfClose);
            final long _tmpVolume;
            _tmpVolume = _cursor.getLong(_cursorIndexOfVolume);
            final double _tmpAtr;
            _tmpAtr = _cursor.getDouble(_cursorIndexOfAtr);
            final String _tmpCandleType;
            _tmpCandleType = _cursor.getString(_cursorIndexOfCandleType);
            final String _tmpTimeframe;
            _tmpTimeframe = _cursor.getString(_cursorIndexOfTimeframe);
            _item = new CandleEntity(_tmpId,_tmpSymbol,_tmpTimestamp,_tmpOpen,_tmpHigh,_tmpLow,_tmpClose,_tmpVolume,_tmpAtr,_tmpCandleType,_tmpTimeframe);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getLatestCandle(final String symbol, final String timeframe,
      final Continuation<? super CandleEntity> $completion) {
    final String _sql = "SELECT * FROM candles WHERE symbol = ? AND timeframe = ? ORDER BY timestamp DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, symbol);
    _argIndex = 2;
    _statement.bindString(_argIndex, timeframe);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<CandleEntity>() {
      @Override
      @Nullable
      public CandleEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSymbol = CursorUtil.getColumnIndexOrThrow(_cursor, "symbol");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfOpen = CursorUtil.getColumnIndexOrThrow(_cursor, "open");
          final int _cursorIndexOfHigh = CursorUtil.getColumnIndexOrThrow(_cursor, "high");
          final int _cursorIndexOfLow = CursorUtil.getColumnIndexOrThrow(_cursor, "low");
          final int _cursorIndexOfClose = CursorUtil.getColumnIndexOrThrow(_cursor, "close");
          final int _cursorIndexOfVolume = CursorUtil.getColumnIndexOrThrow(_cursor, "volume");
          final int _cursorIndexOfAtr = CursorUtil.getColumnIndexOrThrow(_cursor, "atr");
          final int _cursorIndexOfCandleType = CursorUtil.getColumnIndexOrThrow(_cursor, "candleType");
          final int _cursorIndexOfTimeframe = CursorUtil.getColumnIndexOrThrow(_cursor, "timeframe");
          final CandleEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSymbol;
            _tmpSymbol = _cursor.getString(_cursorIndexOfSymbol);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final double _tmpOpen;
            _tmpOpen = _cursor.getDouble(_cursorIndexOfOpen);
            final double _tmpHigh;
            _tmpHigh = _cursor.getDouble(_cursorIndexOfHigh);
            final double _tmpLow;
            _tmpLow = _cursor.getDouble(_cursorIndexOfLow);
            final double _tmpClose;
            _tmpClose = _cursor.getDouble(_cursorIndexOfClose);
            final long _tmpVolume;
            _tmpVolume = _cursor.getLong(_cursorIndexOfVolume);
            final double _tmpAtr;
            _tmpAtr = _cursor.getDouble(_cursorIndexOfAtr);
            final String _tmpCandleType;
            _tmpCandleType = _cursor.getString(_cursorIndexOfCandleType);
            final String _tmpTimeframe;
            _tmpTimeframe = _cursor.getString(_cursorIndexOfTimeframe);
            _result = new CandleEntity(_tmpId,_tmpSymbol,_tmpTimestamp,_tmpOpen,_tmpHigh,_tmpLow,_tmpClose,_tmpVolume,_tmpAtr,_tmpCandleType,_tmpTimeframe);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
