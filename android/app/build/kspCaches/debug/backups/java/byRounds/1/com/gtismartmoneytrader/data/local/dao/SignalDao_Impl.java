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
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.gtismartmoneytrader.data.local.entity.SignalEntity;
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
public final class SignalDao_Impl implements SignalDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SignalEntity> __insertionAdapterOfSignalEntity;

  private final EntityDeletionOrUpdateAdapter<SignalEntity> __deletionAdapterOfSignalEntity;

  private final EntityDeletionOrUpdateAdapter<SignalEntity> __updateAdapterOfSignalEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeactivateSignal;

  private final SharedSQLiteStatement __preparedStmtOfAcknowledgeSignal;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldSignals;

  public SignalDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSignalEntity = new EntityInsertionAdapter<SignalEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `signals` (`id`,`symbol`,`timestamp`,`type`,`confidence`,`entryPrice`,`stopLoss`,`target`,`strike`,`optionType`,`isAcknowledged`,`isActive`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SignalEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getSymbol());
        statement.bindLong(3, entity.getTimestamp());
        statement.bindString(4, entity.getType());
        statement.bindString(5, entity.getConfidence());
        statement.bindDouble(6, entity.getEntryPrice());
        statement.bindDouble(7, entity.getStopLoss());
        statement.bindDouble(8, entity.getTarget());
        statement.bindLong(9, entity.getStrike());
        statement.bindString(10, entity.getOptionType());
        final int _tmp = entity.isAcknowledged() ? 1 : 0;
        statement.bindLong(11, _tmp);
        final int _tmp_1 = entity.isActive() ? 1 : 0;
        statement.bindLong(12, _tmp_1);
      }
    };
    this.__deletionAdapterOfSignalEntity = new EntityDeletionOrUpdateAdapter<SignalEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `signals` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SignalEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfSignalEntity = new EntityDeletionOrUpdateAdapter<SignalEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `signals` SET `id` = ?,`symbol` = ?,`timestamp` = ?,`type` = ?,`confidence` = ?,`entryPrice` = ?,`stopLoss` = ?,`target` = ?,`strike` = ?,`optionType` = ?,`isAcknowledged` = ?,`isActive` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SignalEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getSymbol());
        statement.bindLong(3, entity.getTimestamp());
        statement.bindString(4, entity.getType());
        statement.bindString(5, entity.getConfidence());
        statement.bindDouble(6, entity.getEntryPrice());
        statement.bindDouble(7, entity.getStopLoss());
        statement.bindDouble(8, entity.getTarget());
        statement.bindLong(9, entity.getStrike());
        statement.bindString(10, entity.getOptionType());
        final int _tmp = entity.isAcknowledged() ? 1 : 0;
        statement.bindLong(11, _tmp);
        final int _tmp_1 = entity.isActive() ? 1 : 0;
        statement.bindLong(12, _tmp_1);
        statement.bindString(13, entity.getId());
      }
    };
    this.__preparedStmtOfDeactivateSignal = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE signals SET isActive = 0 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfAcknowledgeSignal = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE signals SET isAcknowledged = 1 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOldSignals = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM signals WHERE timestamp < ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertSignal(final SignalEntity signal,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSignalEntity.insert(signal);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteSignal(final SignalEntity signal,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfSignalEntity.handle(signal);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSignal(final SignalEntity signal,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfSignalEntity.handle(signal);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deactivateSignal(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeactivateSignal.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, id);
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
          __preparedStmtOfDeactivateSignal.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object acknowledgeSignal(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfAcknowledgeSignal.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, id);
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
          __preparedStmtOfAcknowledgeSignal.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOldSignals(final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldSignals.acquire();
        int _argIndex = 1;
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
          __preparedStmtOfDeleteOldSignals.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<SignalEntity>> getAllSignals() {
    final String _sql = "SELECT * FROM signals ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"signals"}, new Callable<List<SignalEntity>>() {
      @Override
      @NonNull
      public List<SignalEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSymbol = CursorUtil.getColumnIndexOrThrow(_cursor, "symbol");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "confidence");
          final int _cursorIndexOfEntryPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "entryPrice");
          final int _cursorIndexOfStopLoss = CursorUtil.getColumnIndexOrThrow(_cursor, "stopLoss");
          final int _cursorIndexOfTarget = CursorUtil.getColumnIndexOrThrow(_cursor, "target");
          final int _cursorIndexOfStrike = CursorUtil.getColumnIndexOrThrow(_cursor, "strike");
          final int _cursorIndexOfOptionType = CursorUtil.getColumnIndexOrThrow(_cursor, "optionType");
          final int _cursorIndexOfIsAcknowledged = CursorUtil.getColumnIndexOrThrow(_cursor, "isAcknowledged");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final List<SignalEntity> _result = new ArrayList<SignalEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SignalEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSymbol;
            _tmpSymbol = _cursor.getString(_cursorIndexOfSymbol);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpConfidence;
            _tmpConfidence = _cursor.getString(_cursorIndexOfConfidence);
            final double _tmpEntryPrice;
            _tmpEntryPrice = _cursor.getDouble(_cursorIndexOfEntryPrice);
            final double _tmpStopLoss;
            _tmpStopLoss = _cursor.getDouble(_cursorIndexOfStopLoss);
            final double _tmpTarget;
            _tmpTarget = _cursor.getDouble(_cursorIndexOfTarget);
            final int _tmpStrike;
            _tmpStrike = _cursor.getInt(_cursorIndexOfStrike);
            final String _tmpOptionType;
            _tmpOptionType = _cursor.getString(_cursorIndexOfOptionType);
            final boolean _tmpIsAcknowledged;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsAcknowledged);
            _tmpIsAcknowledged = _tmp != 0;
            final boolean _tmpIsActive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_1 != 0;
            _item = new SignalEntity(_tmpId,_tmpSymbol,_tmpTimestamp,_tmpType,_tmpConfidence,_tmpEntryPrice,_tmpStopLoss,_tmpTarget,_tmpStrike,_tmpOptionType,_tmpIsAcknowledged,_tmpIsActive);
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
  public Flow<List<SignalEntity>> getSignalsBySymbol(final String symbol) {
    final String _sql = "SELECT * FROM signals WHERE symbol = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, symbol);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"signals"}, new Callable<List<SignalEntity>>() {
      @Override
      @NonNull
      public List<SignalEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSymbol = CursorUtil.getColumnIndexOrThrow(_cursor, "symbol");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "confidence");
          final int _cursorIndexOfEntryPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "entryPrice");
          final int _cursorIndexOfStopLoss = CursorUtil.getColumnIndexOrThrow(_cursor, "stopLoss");
          final int _cursorIndexOfTarget = CursorUtil.getColumnIndexOrThrow(_cursor, "target");
          final int _cursorIndexOfStrike = CursorUtil.getColumnIndexOrThrow(_cursor, "strike");
          final int _cursorIndexOfOptionType = CursorUtil.getColumnIndexOrThrow(_cursor, "optionType");
          final int _cursorIndexOfIsAcknowledged = CursorUtil.getColumnIndexOrThrow(_cursor, "isAcknowledged");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final List<SignalEntity> _result = new ArrayList<SignalEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SignalEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSymbol;
            _tmpSymbol = _cursor.getString(_cursorIndexOfSymbol);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpConfidence;
            _tmpConfidence = _cursor.getString(_cursorIndexOfConfidence);
            final double _tmpEntryPrice;
            _tmpEntryPrice = _cursor.getDouble(_cursorIndexOfEntryPrice);
            final double _tmpStopLoss;
            _tmpStopLoss = _cursor.getDouble(_cursorIndexOfStopLoss);
            final double _tmpTarget;
            _tmpTarget = _cursor.getDouble(_cursorIndexOfTarget);
            final int _tmpStrike;
            _tmpStrike = _cursor.getInt(_cursorIndexOfStrike);
            final String _tmpOptionType;
            _tmpOptionType = _cursor.getString(_cursorIndexOfOptionType);
            final boolean _tmpIsAcknowledged;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsAcknowledged);
            _tmpIsAcknowledged = _tmp != 0;
            final boolean _tmpIsActive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_1 != 0;
            _item = new SignalEntity(_tmpId,_tmpSymbol,_tmpTimestamp,_tmpType,_tmpConfidence,_tmpEntryPrice,_tmpStopLoss,_tmpTarget,_tmpStrike,_tmpOptionType,_tmpIsAcknowledged,_tmpIsActive);
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
  public Object getSignalById(final String id,
      final Continuation<? super SignalEntity> $completion) {
    final String _sql = "SELECT * FROM signals WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SignalEntity>() {
      @Override
      @Nullable
      public SignalEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSymbol = CursorUtil.getColumnIndexOrThrow(_cursor, "symbol");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "confidence");
          final int _cursorIndexOfEntryPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "entryPrice");
          final int _cursorIndexOfStopLoss = CursorUtil.getColumnIndexOrThrow(_cursor, "stopLoss");
          final int _cursorIndexOfTarget = CursorUtil.getColumnIndexOrThrow(_cursor, "target");
          final int _cursorIndexOfStrike = CursorUtil.getColumnIndexOrThrow(_cursor, "strike");
          final int _cursorIndexOfOptionType = CursorUtil.getColumnIndexOrThrow(_cursor, "optionType");
          final int _cursorIndexOfIsAcknowledged = CursorUtil.getColumnIndexOrThrow(_cursor, "isAcknowledged");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final SignalEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSymbol;
            _tmpSymbol = _cursor.getString(_cursorIndexOfSymbol);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpConfidence;
            _tmpConfidence = _cursor.getString(_cursorIndexOfConfidence);
            final double _tmpEntryPrice;
            _tmpEntryPrice = _cursor.getDouble(_cursorIndexOfEntryPrice);
            final double _tmpStopLoss;
            _tmpStopLoss = _cursor.getDouble(_cursorIndexOfStopLoss);
            final double _tmpTarget;
            _tmpTarget = _cursor.getDouble(_cursorIndexOfTarget);
            final int _tmpStrike;
            _tmpStrike = _cursor.getInt(_cursorIndexOfStrike);
            final String _tmpOptionType;
            _tmpOptionType = _cursor.getString(_cursorIndexOfOptionType);
            final boolean _tmpIsAcknowledged;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsAcknowledged);
            _tmpIsAcknowledged = _tmp != 0;
            final boolean _tmpIsActive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_1 != 0;
            _result = new SignalEntity(_tmpId,_tmpSymbol,_tmpTimestamp,_tmpType,_tmpConfidence,_tmpEntryPrice,_tmpStopLoss,_tmpTarget,_tmpStrike,_tmpOptionType,_tmpIsAcknowledged,_tmpIsActive);
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
  public Object getLatestActiveSignal(final Continuation<? super SignalEntity> $completion) {
    final String _sql = "SELECT * FROM signals WHERE isActive = 1 ORDER BY timestamp DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SignalEntity>() {
      @Override
      @Nullable
      public SignalEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSymbol = CursorUtil.getColumnIndexOrThrow(_cursor, "symbol");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "confidence");
          final int _cursorIndexOfEntryPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "entryPrice");
          final int _cursorIndexOfStopLoss = CursorUtil.getColumnIndexOrThrow(_cursor, "stopLoss");
          final int _cursorIndexOfTarget = CursorUtil.getColumnIndexOrThrow(_cursor, "target");
          final int _cursorIndexOfStrike = CursorUtil.getColumnIndexOrThrow(_cursor, "strike");
          final int _cursorIndexOfOptionType = CursorUtil.getColumnIndexOrThrow(_cursor, "optionType");
          final int _cursorIndexOfIsAcknowledged = CursorUtil.getColumnIndexOrThrow(_cursor, "isAcknowledged");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final SignalEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSymbol;
            _tmpSymbol = _cursor.getString(_cursorIndexOfSymbol);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpConfidence;
            _tmpConfidence = _cursor.getString(_cursorIndexOfConfidence);
            final double _tmpEntryPrice;
            _tmpEntryPrice = _cursor.getDouble(_cursorIndexOfEntryPrice);
            final double _tmpStopLoss;
            _tmpStopLoss = _cursor.getDouble(_cursorIndexOfStopLoss);
            final double _tmpTarget;
            _tmpTarget = _cursor.getDouble(_cursorIndexOfTarget);
            final int _tmpStrike;
            _tmpStrike = _cursor.getInt(_cursorIndexOfStrike);
            final String _tmpOptionType;
            _tmpOptionType = _cursor.getString(_cursorIndexOfOptionType);
            final boolean _tmpIsAcknowledged;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsAcknowledged);
            _tmpIsAcknowledged = _tmp != 0;
            final boolean _tmpIsActive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_1 != 0;
            _result = new SignalEntity(_tmpId,_tmpSymbol,_tmpTimestamp,_tmpType,_tmpConfidence,_tmpEntryPrice,_tmpStopLoss,_tmpTarget,_tmpStrike,_tmpOptionType,_tmpIsAcknowledged,_tmpIsActive);
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
  public Flow<List<SignalEntity>> getRecentSignals(final int limit) {
    final String _sql = "SELECT * FROM signals ORDER BY timestamp DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"signals"}, new Callable<List<SignalEntity>>() {
      @Override
      @NonNull
      public List<SignalEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSymbol = CursorUtil.getColumnIndexOrThrow(_cursor, "symbol");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "confidence");
          final int _cursorIndexOfEntryPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "entryPrice");
          final int _cursorIndexOfStopLoss = CursorUtil.getColumnIndexOrThrow(_cursor, "stopLoss");
          final int _cursorIndexOfTarget = CursorUtil.getColumnIndexOrThrow(_cursor, "target");
          final int _cursorIndexOfStrike = CursorUtil.getColumnIndexOrThrow(_cursor, "strike");
          final int _cursorIndexOfOptionType = CursorUtil.getColumnIndexOrThrow(_cursor, "optionType");
          final int _cursorIndexOfIsAcknowledged = CursorUtil.getColumnIndexOrThrow(_cursor, "isAcknowledged");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final List<SignalEntity> _result = new ArrayList<SignalEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SignalEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSymbol;
            _tmpSymbol = _cursor.getString(_cursorIndexOfSymbol);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpConfidence;
            _tmpConfidence = _cursor.getString(_cursorIndexOfConfidence);
            final double _tmpEntryPrice;
            _tmpEntryPrice = _cursor.getDouble(_cursorIndexOfEntryPrice);
            final double _tmpStopLoss;
            _tmpStopLoss = _cursor.getDouble(_cursorIndexOfStopLoss);
            final double _tmpTarget;
            _tmpTarget = _cursor.getDouble(_cursorIndexOfTarget);
            final int _tmpStrike;
            _tmpStrike = _cursor.getInt(_cursorIndexOfStrike);
            final String _tmpOptionType;
            _tmpOptionType = _cursor.getString(_cursorIndexOfOptionType);
            final boolean _tmpIsAcknowledged;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsAcknowledged);
            _tmpIsAcknowledged = _tmp != 0;
            final boolean _tmpIsActive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_1 != 0;
            _item = new SignalEntity(_tmpId,_tmpSymbol,_tmpTimestamp,_tmpType,_tmpConfidence,_tmpEntryPrice,_tmpStopLoss,_tmpTarget,_tmpStrike,_tmpOptionType,_tmpIsAcknowledged,_tmpIsActive);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
