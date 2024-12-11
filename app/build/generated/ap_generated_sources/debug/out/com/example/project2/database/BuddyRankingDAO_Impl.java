package com.example.project2.database;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.project2.database.entities.BuddyRanking;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class BuddyRankingDAO_Impl implements BuddyRankingDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BuddyRanking> __insertionAdapterOfBuddyRanking;

  private final SharedSQLiteStatement __preparedStmtOfIncrementWins;

  private final SharedSQLiteStatement __preparedStmtOfIncrementLosses;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfDeleteRanking;

  public BuddyRankingDAO_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBuddyRanking = new EntityInsertionAdapter<BuddyRanking>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `buddyRankingTable` (`id`,`buddyId`,`wins`,`losses`,`winPercentage`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final BuddyRanking entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getBuddyId());
        statement.bindLong(3, entity.getWins());
        statement.bindLong(4, entity.getLosses());
        statement.bindDouble(5, entity.getWinPercentage());
      }
    };
    this.__preparedStmtOfIncrementWins = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE buddyRankingTable SET wins = wins + 1, winPercentage = (CAST((wins + 1) AS FLOAT) / (wins + losses + 1)) * 100 WHERE buddyId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfIncrementLosses = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE buddyRankingTable SET losses = losses + 1, winPercentage = (CAST(wins AS FLOAT) / (wins + losses + 1)) * 100 WHERE buddyId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM buddyRankingTable";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteRanking = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM buddyRankingTable WHERE buddyId = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final BuddyRanking... rankings) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfBuddyRanking.insert(rankings);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void incrementWins(final int buddyId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfIncrementWins.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, buddyId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfIncrementWins.release(_stmt);
    }
  }

  @Override
  public void incrementLosses(final int buddyId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfIncrementLosses.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, buddyId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfIncrementLosses.release(_stmt);
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public void deleteRanking(final int buddyId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteRanking.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, buddyId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteRanking.release(_stmt);
    }
  }

  @Override
  public LiveData<List<BuddyRanking>> getAllRankings() {
    final String _sql = "SELECT * FROM buddyRankingTable ORDER BY winPercentage DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"buddyRankingTable"}, false, new Callable<List<BuddyRanking>>() {
      @Override
      @Nullable
      public List<BuddyRanking> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBuddyId = CursorUtil.getColumnIndexOrThrow(_cursor, "buddyId");
          final int _cursorIndexOfWins = CursorUtil.getColumnIndexOrThrow(_cursor, "wins");
          final int _cursorIndexOfLosses = CursorUtil.getColumnIndexOrThrow(_cursor, "losses");
          final int _cursorIndexOfWinPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "winPercentage");
          final List<BuddyRanking> _result = new ArrayList<BuddyRanking>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BuddyRanking _item;
            _item = new BuddyRanking();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final int _tmpBuddyId;
            _tmpBuddyId = _cursor.getInt(_cursorIndexOfBuddyId);
            _item.setBuddyId(_tmpBuddyId);
            final int _tmpWins;
            _tmpWins = _cursor.getInt(_cursorIndexOfWins);
            _item.setWins(_tmpWins);
            final int _tmpLosses;
            _tmpLosses = _cursor.getInt(_cursorIndexOfLosses);
            _item.setLosses(_tmpLosses);
            final float _tmpWinPercentage;
            _tmpWinPercentage = _cursor.getFloat(_cursorIndexOfWinPercentage);
            _item.setWinPercentage(_tmpWinPercentage);
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
  public List<BuddyRanking> getAllRankingsSync() {
    final String _sql = "SELECT * FROM buddyRankingTable ORDER BY winPercentage DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfBuddyId = CursorUtil.getColumnIndexOrThrow(_cursor, "buddyId");
      final int _cursorIndexOfWins = CursorUtil.getColumnIndexOrThrow(_cursor, "wins");
      final int _cursorIndexOfLosses = CursorUtil.getColumnIndexOrThrow(_cursor, "losses");
      final int _cursorIndexOfWinPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "winPercentage");
      final List<BuddyRanking> _result = new ArrayList<BuddyRanking>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final BuddyRanking _item;
        _item = new BuddyRanking();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpBuddyId;
        _tmpBuddyId = _cursor.getInt(_cursorIndexOfBuddyId);
        _item.setBuddyId(_tmpBuddyId);
        final int _tmpWins;
        _tmpWins = _cursor.getInt(_cursorIndexOfWins);
        _item.setWins(_tmpWins);
        final int _tmpLosses;
        _tmpLosses = _cursor.getInt(_cursorIndexOfLosses);
        _item.setLosses(_tmpLosses);
        final float _tmpWinPercentage;
        _tmpWinPercentage = _cursor.getFloat(_cursorIndexOfWinPercentage);
        _item.setWinPercentage(_tmpWinPercentage);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<BuddyRanking> getRankingByBuddyId(final int buddyId) {
    final String _sql = "SELECT * FROM buddyRankingTable WHERE buddyId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, buddyId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"buddyRankingTable"}, false, new Callable<BuddyRanking>() {
      @Override
      @Nullable
      public BuddyRanking call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBuddyId = CursorUtil.getColumnIndexOrThrow(_cursor, "buddyId");
          final int _cursorIndexOfWins = CursorUtil.getColumnIndexOrThrow(_cursor, "wins");
          final int _cursorIndexOfLosses = CursorUtil.getColumnIndexOrThrow(_cursor, "losses");
          final int _cursorIndexOfWinPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "winPercentage");
          final BuddyRanking _result;
          if (_cursor.moveToFirst()) {
            _result = new BuddyRanking();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _result.setId(_tmpId);
            final int _tmpBuddyId;
            _tmpBuddyId = _cursor.getInt(_cursorIndexOfBuddyId);
            _result.setBuddyId(_tmpBuddyId);
            final int _tmpWins;
            _tmpWins = _cursor.getInt(_cursorIndexOfWins);
            _result.setWins(_tmpWins);
            final int _tmpLosses;
            _tmpLosses = _cursor.getInt(_cursorIndexOfLosses);
            _result.setLosses(_tmpLosses);
            final float _tmpWinPercentage;
            _tmpWinPercentage = _cursor.getFloat(_cursorIndexOfWinPercentage);
            _result.setWinPercentage(_tmpWinPercentage);
          } else {
            _result = null;
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
  public BuddyRanking getRankingByBuddyIdSync(final int buddyId) {
    final String _sql = "SELECT * FROM buddyRankingTable WHERE buddyId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, buddyId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfBuddyId = CursorUtil.getColumnIndexOrThrow(_cursor, "buddyId");
      final int _cursorIndexOfWins = CursorUtil.getColumnIndexOrThrow(_cursor, "wins");
      final int _cursorIndexOfLosses = CursorUtil.getColumnIndexOrThrow(_cursor, "losses");
      final int _cursorIndexOfWinPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "winPercentage");
      final BuddyRanking _result;
      if (_cursor.moveToFirst()) {
        _result = new BuddyRanking();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final int _tmpBuddyId;
        _tmpBuddyId = _cursor.getInt(_cursorIndexOfBuddyId);
        _result.setBuddyId(_tmpBuddyId);
        final int _tmpWins;
        _tmpWins = _cursor.getInt(_cursorIndexOfWins);
        _result.setWins(_tmpWins);
        final int _tmpLosses;
        _tmpLosses = _cursor.getInt(_cursorIndexOfLosses);
        _result.setLosses(_tmpLosses);
        final float _tmpWinPercentage;
        _tmpWinPercentage = _cursor.getFloat(_cursorIndexOfWinPercentage);
        _result.setWinPercentage(_tmpWinPercentage);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
