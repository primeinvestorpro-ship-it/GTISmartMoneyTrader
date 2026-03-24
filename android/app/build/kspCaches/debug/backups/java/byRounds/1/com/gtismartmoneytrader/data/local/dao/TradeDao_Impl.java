package com.gtismartmoneytrader.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.gtismartmoneytrader.data.local.entity.TradeEntity;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
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
public final class TradeDao_Impl implements TradeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TradeEntity> __insertionAdapterOfTradeEntity;

  private final EntityDeletionOrUpdateAdapter<TradeEntity> __deletionAdapterOfTradeEntity;

  private final EntityDeletionOrUpdateAdapter<TradeEntity> __updateAdapterOfTradeEntity;

  public TradeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTradeEntity = new EntityInsertionAdapter<TradeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `trades` (`id`,`signalId`,`action`,`entryPrice`,`entryTime`,`exitPrice`,`exitTime`,`pnl`,`pnlPercent`,`isPaperTrade`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TradeEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getSignalId());
        statement.bindString(3, entity.getAction());
        if (entity.getEntryPrice() == null) {
          statement.bindNull(4);
        } else {
          statement.bindDouble(4, entity.getEntryPrice());
        }
        if (entity.getEntryTime() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getEntryTime());
        }
        if (entity.getExitPrice() == null) {
          statement.bindNull(6);
        } else {
          statement.bindDouble(6, entity.getExitPrice());
        }
        if (entity.getExitTime() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getExitTime());
        }
        if (entity.getPnl() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getPnl());
        }
        if (entity.getPnlPercent() == null) {
          statement.bindNull(9);
        } else {
          statement.bindDouble(9, entity.getPnlPercent());
        }
        final int _tmp = entity.isPaperTrade() ? 1 : 0;
        statement.bindLong(10, _tmp);
      }
    };
    this.__deletionAdapterOfTradeEntity = new EntityDeletionOrUpdateAdapter<TradeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `trades` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TradeEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTradeEntity = new EntityDeletionOrUpdateAdapter<TradeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `trades` SET `id` = ?,`signalId` = ?,`action` = ?,`entryPrice` = ?,`entryTime` = ?,`exitPrice` = ?,`exitTime` = ?,`pnl` = ?,`pnlPercent` = ?,`isPaperTrade` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TradeEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getSignalId());
        statement.bindString(3, entity.getAction());
        if (entity.getEntryPrice() == null) {
          statement.bindNull(4);
        } else {
          statement.bindDouble(4, entity.getEntryPrice());
        }
        if (entity.getEntryTime() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getEntryTime());
        }
        if (entity.getExitPrice() == null) {
          statement.bindNull(6);
        } else {
          statement.bindDouble(6, entity.getExitPrice());
        }
        if (entity.getExitTime() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getExitTime());
        }
        if (entity.getPnl() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getPnl());
        }
        if (entity.getPnlPercent() == null) {
          statement.bindNull(9);
        } else {
          statement.bindDouble(9, entity.getPnlPercent());
        }
        final int _tmp = entity.isPaperTrade() ? 1 : 0;
        statement.bindLong(10, _tmp);
        statement.bindLong(11, entity.getId());
      }
    };
  }

  @Override
  public Object insertTrade(final TradeEntity trade, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTradeEntity.insertAndReturnId(trade);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteTrade(final TradeEntity trade, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfTradeEntity.handle(trade);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTrade(final TradeEntity trade, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTradeEntity.handle(trade);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<TradeEntity>> getAllTrades() {
    final String _sql = "SELECT * FROM trades ORDER BY entryTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"trades"}, new Callable<List<TradeEntity>>() {
      @Override
      @NonNull
      public List<TradeEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSignalId = CursorUtil.getColumnIndexOrThrow(_cursor, "signalId");
          final int _cursorIndexOfAction = CursorUtil.getColumnIndexOrThrow(_cursor, "action");
          final int _cursorIndexOfEntryPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "entryPrice");
          final int _cursorIndexOfEntryTime = CursorUtil.getColumnIndexOrThrow(_cursor, "entryTime");
          final int _cursorIndexOfExitPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "exitPrice");
          final int _cursorIndexOfExitTime = CursorUtil.getColumnIndexOrThrow(_cursor, "exitTime");
          final int _cursorIndexOfPnl = CursorUtil.getColumnIndexOrThrow(_cursor, "pnl");
          final int _cursorIndexOfPnlPercent = CursorUtil.getColumnIndexOrThrow(_cursor, "pnlPercent");
          final int _cursorIndexOfIsPaperTrade = CursorUtil.getColumnIndexOrThrow(_cursor, "isPaperTrade");
          final List<TradeEntity> _result = new ArrayList<TradeEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TradeEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpSignalId;
            _tmpSignalId = _cursor.getString(_cursorIndexOfSignalId);
            final String _tmpAction;
            _tmpAction = _cursor.getString(_cursorIndexOfAction);
            final Double _tmpEntryPrice;
            if (_cursor.isNull(_cursorIndexOfEntryPrice)) {
              _tmpEntryPrice = null;
            } else {
              _tmpEntryPrice = _cursor.getDouble(_cursorIndexOfEntryPrice);
            }
            final Long _tmpEntryTime;
            if (_cursor.isNull(_cursorIndexOfEntryTime)) {
              _tmpEntryTime = null;
            } else {
              _tmpEntryTime = _cursor.getLong(_cursorIndexOfEntryTime);
            }
            final Double _tmpExitPrice;
            if (_cursor.isNull(_cursorIndexOfExitPrice)) {
              _tmpExitPrice = null;
            } else {
              _tmpExitPrice = _cursor.getDouble(_cursorIndexOfExitPrice);
            }
            final Long _tmpExitTime;
            if (_cursor.isNull(_cursorIndexOfExitTime)) {
              _tmpExitTime = null;
            } else {
              _tmpExitTime = _cursor.getLong(_cursorIndexOfExitTime);
            }
            final Double _tmpPnl;
            if (_cursor.isNull(_cursorIndexOfPnl)) {
              _tmpPnl = null;
            } else {
              _tmpPnl = _cursor.getDouble(_cursorIndexOfPnl);
            }
            final Double _tmpPnlPercent;
            if (_cursor.isNull(_cursorIndexOfPnlPercent)) {
              _tmpPnlPercent = null;
            } else {
              _tmpPnlPercent = _cursor.getDouble(_cursorIndexOfPnlPercent);
            }
            final boolean _tmpIsPaperTrade;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPaperTrade);
            _tmpIsPaperTrade = _tmp != 0;
            _item = new TradeEntity(_tmpId,_tmpSignalId,_tmpAction,_tmpEntryPrice,_tmpEntryTime,_tmpExitPrice,_tmpExitTime,_tmpPnl,_tmpPnlPercent,_tmpIsPaperTrade);
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
  public Object getTradeBySignalId(final String signalId,
      final Continuation<? super TradeEntity> $completion) {
    final String _sql = "SELECT * FROM trades WHERE signalId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, signalId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<TradeEntity>() {
      @Override
      @Nullable
      public TradeEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSignalId = CursorUtil.getColumnIndexOrThrow(_cursor, "signalId");
          final int _cursorIndexOfAction = CursorUtil.getColumnIndexOrThrow(_cursor, "action");
          final int _cursorIndexOfEntryPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "entryPrice");
          final int _cursorIndexOfEntryTime = CursorUtil.getColumnIndexOrThrow(_cursor, "entryTime");
          final int _cursorIndexOfExitPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "exitPrice");
          final int _cursorIndexOfExitTime = CursorUtil.getColumnIndexOrThrow(_cursor, "exitTime");
          final int _cursorIndexOfPnl = CursorUtil.getColumnIndexOrThrow(_cursor, "pnl");
          final int _cursorIndexOfPnlPercent = CursorUtil.getColumnIndexOrThrow(_cursor, "pnlPercent");
          final int _cursorIndexOfIsPaperTrade = CursorUtil.getColumnIndexOrThrow(_cursor, "isPaperTrade");
          final TradeEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpSignalId;
            _tmpSignalId = _cursor.getString(_cursorIndexOfSignalId);
            final String _tmpAction;
            _tmpAction = _cursor.getString(_cursorIndexOfAction);
            final Double _tmpEntryPrice;
            if (_cursor.isNull(_cursorIndexOfEntryPrice)) {
              _tmpEntryPrice = null;
            } else {
              _tmpEntryPrice = _cursor.getDouble(_cursorIndexOfEntryPrice);
            }
            final Long _tmpEntryTime;
            if (_cursor.isNull(_cursorIndexOfEntryTime)) {
              _tmpEntryTime = null;
            } else {
              _tmpEntryTime = _cursor.getLong(_cursorIndexOfEntryTime);
            }
            final Double _tmpExitPrice;
            if (_cursor.isNull(_cursorIndexOfExitPrice)) {
              _tmpExitPrice = null;
            } else {
              _tmpExitPrice = _cursor.getDouble(_cursorIndexOfExitPrice);
            }
            final Long _tmpExitTime;
            if (_cursor.isNull(_cursorIndexOfExitTime)) {
              _tmpExitTime = null;
            } else {
              _tmpExitTime = _cursor.getLong(_cursorIndexOfExitTime);
            }
            final Double _tmpPnl;
            if (_cursor.isNull(_cursorIndexOfPnl)) {
              _tmpPnl = null;
            } else {
              _tmpPnl = _cursor.getDouble(_cursorIndexOfPnl);
            }
            final Double _tmpPnlPercent;
            if (_cursor.isNull(_cursorIndexOfPnlPercent)) {
              _tmpPnlPercent = null;
            } else {
              _tmpPnlPercent = _cursor.getDouble(_cursorIndexOfPnlPercent);
            }
            final boolean _tmpIsPaperTrade;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPaperTrade);
            _tmpIsPaperTrade = _tmp != 0;
            _result = new TradeEntity(_tmpId,_tmpSignalId,_tmpAction,_tmpEntryPrice,_tmpEntryTime,_tmpExitPrice,_tmpExitTime,_tmpPnl,_tmpPnlPercent,_tmpIsPaperTrade);
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

  @Override
  public Flow<List<TradeEntity>> getTradesByType(final boolean isPaper) {
    final String _sql = "SELECT * FROM trades WHERE isPaperTrade = ? ORDER BY entryTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final int _tmp = isPaper ? 1 : 0;
    _statement.bindLong(_argIndex, _tmp);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"trades"}, new Callable<List<TradeEntity>>() {
      @Override
      @NonNull
      public List<TradeEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSignalId = CursorUtil.getColumnIndexOrThrow(_cursor, "signalId");
          final int _cursorIndexOfAction = CursorUtil.getColumnIndexOrThrow(_cursor, "action");
          final int _cursorIndexOfEntryPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "entryPrice");
          final int _cursorIndexOfEntryTime = CursorUtil.getColumnIndexOrThrow(_cursor, "entryTime");
          final int _cursorIndexOfExitPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "exitPrice");
          final int _cursorIndexOfExitTime = CursorUtil.getColumnIndexOrThrow(_cursor, "exitTime");
          final int _cursorIndexOfPnl = CursorUtil.getColumnIndexOrThrow(_cursor, "pnl");
          final int _cursorIndexOfPnlPercent = CursorUtil.getColumnIndexOrThrow(_cursor, "pnlPercent");
          final int _cursorIndexOfIsPaperTrade = CursorUtil.getColumnIndexOrThrow(_cursor, "isPaperTrade");
          final List<TradeEntity> _result = new ArrayList<TradeEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TradeEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpSignalId;
            _tmpSignalId = _cursor.getString(_cursorIndexOfSignalId);
            final String _tmpAction;
            _tmpAction = _cursor.getString(_cursorIndexOfAction);
            final Double _tmpEntryPrice;
            if (_cursor.isNull(_cursorIndexOfEntryPrice)) {
              _tmpEntryPrice = null;
            } else {
              _tmpEntryPrice = _cursor.getDouble(_cursorIndexOfEntryPrice);
            }
            final Long _tmpEntryTime;
            if (_cursor.isNull(_cursorIndexOfEntryTime)) {
              _tmpEntryTime = null;
            } else {
              _tmpEntryTime = _cursor.getLong(_cursorIndexOfEntryTime);
            }
            final Double _tmpExitPrice;
            if (_cursor.isNull(_cursorIndexOfExitPrice)) {
              _tmpExitPrice = null;
            } else {
              _tmpExitPrice = _cursor.getDouble(_cursorIndexOfExitPrice);
            }
            final Long _tmpExitTime;
            if (_cursor.isNull(_cursorIndexOfExitTime)) {
              _tmpExitTime = null;
            } else {
              _tmpExitTime = _cursor.getLong(_cursorIndexOfExitTime);
            }
            final Double _tmpPnl;
            if (_cursor.isNull(_cursorIndexOfPnl)) {
              _tmpPnl = null;
            } else {
              _tmpPnl = _cursor.getDouble(_cursorIndexOfPnl);
            }
            final Double _tmpPnlPercent;
            if (_cursor.isNull(_cursorIndexOfPnlPercent)) {
              _tmpPnlPercent = null;
            } else {
              _tmpPnlPercent = _cursor.getDouble(_cursorIndexOfPnlPercent);
            }
            final boolean _tmpIsPaperTrade;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsPaperTrade);
            _tmpIsPaperTrade = _tmp_1 != 0;
            _item = new TradeEntity(_tmpId,_tmpSignalId,_tmpAction,_tmpEntryPrice,_tmpEntryTime,_tmpExitPrice,_tmpExitTime,_tmpPnl,_tmpPnlPercent,_tmpIsPaperTrade);
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
  public Object getTotalTradeCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM trades";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getWinningTradeCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM trades WHERE pnl > 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTotalPnL(final Continuation<? super Double> $completion) {
    final String _sql = "SELECT SUM(pnl) FROM trades";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
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
