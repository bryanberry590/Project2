package com.example.project2.database;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.project2.database.entities.Buddies;
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
public final class BuddiesDAO_Impl implements BuddiesDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Buddies> __insertionAdapterOfBuddies;

  private final EntityDeletionOrUpdateAdapter<Buddies> __deletionAdapterOfBuddies;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfUpdateBuddies;

  private final SharedSQLiteStatement __preparedStmtOfDeleteBuddies;

  public BuddiesDAO_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBuddies = new EntityInsertionAdapter<Buddies>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `buddiesTable` (`id`,`name`,`health`,`attack`,`defense`,`exp`,`imageSource`,`isStarter`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Buddies entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        statement.bindLong(3, entity.getHealth());
        statement.bindLong(4, entity.getAttack());
        statement.bindLong(5, entity.getDefense());
        statement.bindLong(6, entity.getExp());
        if (entity.getImageSource() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getImageSource());
        }
        final int _tmp = entity.isStarter() ? 1 : 0;
        statement.bindLong(8, _tmp);
      }
    };
    this.__deletionAdapterOfBuddies = new EntityDeletionOrUpdateAdapter<Buddies>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `buddiesTable` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Buddies entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM buddiesTable";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateBuddies = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE buddiesTable SET name = ?, health = ?, attack = ?, defense = ?, exp = ?, imageSource = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteBuddies = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM buddiesTable WHERE id == ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Buddies... buddies) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfBuddies.insert(buddies);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Buddies buddies) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfBuddies.handle(buddies);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
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
  public void updateBuddies(final int buddiesId, final String newName, final int newHealth,
      final int newAttack, final int newDefense, final int newExp, final String newImage) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateBuddies.acquire();
    int _argIndex = 1;
    if (newName == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, newName);
    }
    _argIndex = 2;
    _stmt.bindLong(_argIndex, newHealth);
    _argIndex = 3;
    _stmt.bindLong(_argIndex, newAttack);
    _argIndex = 4;
    _stmt.bindLong(_argIndex, newDefense);
    _argIndex = 5;
    _stmt.bindLong(_argIndex, newExp);
    _argIndex = 6;
    if (newImage == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, newImage);
    }
    _argIndex = 7;
    _stmt.bindLong(_argIndex, buddiesId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfUpdateBuddies.release(_stmt);
    }
  }

  @Override
  public void deleteBuddies(final int buddiesId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteBuddies.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, buddiesId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteBuddies.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Buddies>> getAllBuddies() {
    final String _sql = "SELECT * FROM buddiesTable ORDER BY name";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"buddiesTable"}, false, new Callable<List<Buddies>>() {
      @Override
      @Nullable
      public List<Buddies> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfHealth = CursorUtil.getColumnIndexOrThrow(_cursor, "health");
          final int _cursorIndexOfAttack = CursorUtil.getColumnIndexOrThrow(_cursor, "attack");
          final int _cursorIndexOfDefense = CursorUtil.getColumnIndexOrThrow(_cursor, "defense");
          final int _cursorIndexOfExp = CursorUtil.getColumnIndexOrThrow(_cursor, "exp");
          final int _cursorIndexOfImageSource = CursorUtil.getColumnIndexOrThrow(_cursor, "imageSource");
          final int _cursorIndexOfIsStarter = CursorUtil.getColumnIndexOrThrow(_cursor, "isStarter");
          final List<Buddies> _result = new ArrayList<Buddies>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Buddies _item;
            _item = new Buddies();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _item.setName(_tmpName);
            final int _tmpHealth;
            _tmpHealth = _cursor.getInt(_cursorIndexOfHealth);
            _item.setHealth(_tmpHealth);
            final int _tmpAttack;
            _tmpAttack = _cursor.getInt(_cursorIndexOfAttack);
            _item.setAttack(_tmpAttack);
            final int _tmpDefense;
            _tmpDefense = _cursor.getInt(_cursorIndexOfDefense);
            _item.setDefense(_tmpDefense);
            final int _tmpExp;
            _tmpExp = _cursor.getInt(_cursorIndexOfExp);
            _item.setExp(_tmpExp);
            final String _tmpImageSource;
            if (_cursor.isNull(_cursorIndexOfImageSource)) {
              _tmpImageSource = null;
            } else {
              _tmpImageSource = _cursor.getString(_cursorIndexOfImageSource);
            }
            _item.setImageSource(_tmpImageSource);
            final boolean _tmpIsStarter;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsStarter);
            _tmpIsStarter = _tmp != 0;
            _item.setStarter(_tmpIsStarter);
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
  public List<Buddies> getAllBuddiesSync() {
    final String _sql = "SELECT * FROM buddiesTable ORDER BY name";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfHealth = CursorUtil.getColumnIndexOrThrow(_cursor, "health");
      final int _cursorIndexOfAttack = CursorUtil.getColumnIndexOrThrow(_cursor, "attack");
      final int _cursorIndexOfDefense = CursorUtil.getColumnIndexOrThrow(_cursor, "defense");
      final int _cursorIndexOfExp = CursorUtil.getColumnIndexOrThrow(_cursor, "exp");
      final int _cursorIndexOfImageSource = CursorUtil.getColumnIndexOrThrow(_cursor, "imageSource");
      final int _cursorIndexOfIsStarter = CursorUtil.getColumnIndexOrThrow(_cursor, "isStarter");
      final List<Buddies> _result = new ArrayList<Buddies>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Buddies _item;
        _item = new Buddies();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _item.setName(_tmpName);
        final int _tmpHealth;
        _tmpHealth = _cursor.getInt(_cursorIndexOfHealth);
        _item.setHealth(_tmpHealth);
        final int _tmpAttack;
        _tmpAttack = _cursor.getInt(_cursorIndexOfAttack);
        _item.setAttack(_tmpAttack);
        final int _tmpDefense;
        _tmpDefense = _cursor.getInt(_cursorIndexOfDefense);
        _item.setDefense(_tmpDefense);
        final int _tmpExp;
        _tmpExp = _cursor.getInt(_cursorIndexOfExp);
        _item.setExp(_tmpExp);
        final String _tmpImageSource;
        if (_cursor.isNull(_cursorIndexOfImageSource)) {
          _tmpImageSource = null;
        } else {
          _tmpImageSource = _cursor.getString(_cursorIndexOfImageSource);
        }
        _item.setImageSource(_tmpImageSource);
        final boolean _tmpIsStarter;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsStarter);
        _tmpIsStarter = _tmp != 0;
        _item.setStarter(_tmpIsStarter);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Buddies> getBuddiesByName(final String name) {
    final String _sql = "SELECT * FROM buddiesTable WHERE name == ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"buddiesTable"}, false, new Callable<Buddies>() {
      @Override
      @Nullable
      public Buddies call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfHealth = CursorUtil.getColumnIndexOrThrow(_cursor, "health");
          final int _cursorIndexOfAttack = CursorUtil.getColumnIndexOrThrow(_cursor, "attack");
          final int _cursorIndexOfDefense = CursorUtil.getColumnIndexOrThrow(_cursor, "defense");
          final int _cursorIndexOfExp = CursorUtil.getColumnIndexOrThrow(_cursor, "exp");
          final int _cursorIndexOfImageSource = CursorUtil.getColumnIndexOrThrow(_cursor, "imageSource");
          final int _cursorIndexOfIsStarter = CursorUtil.getColumnIndexOrThrow(_cursor, "isStarter");
          final Buddies _result;
          if (_cursor.moveToFirst()) {
            _result = new Buddies();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _result.setId(_tmpId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _result.setName(_tmpName);
            final int _tmpHealth;
            _tmpHealth = _cursor.getInt(_cursorIndexOfHealth);
            _result.setHealth(_tmpHealth);
            final int _tmpAttack;
            _tmpAttack = _cursor.getInt(_cursorIndexOfAttack);
            _result.setAttack(_tmpAttack);
            final int _tmpDefense;
            _tmpDefense = _cursor.getInt(_cursorIndexOfDefense);
            _result.setDefense(_tmpDefense);
            final int _tmpExp;
            _tmpExp = _cursor.getInt(_cursorIndexOfExp);
            _result.setExp(_tmpExp);
            final String _tmpImageSource;
            if (_cursor.isNull(_cursorIndexOfImageSource)) {
              _tmpImageSource = null;
            } else {
              _tmpImageSource = _cursor.getString(_cursorIndexOfImageSource);
            }
            _result.setImageSource(_tmpImageSource);
            final boolean _tmpIsStarter;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsStarter);
            _tmpIsStarter = _tmp != 0;
            _result.setStarter(_tmpIsStarter);
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
  public Buddies getBuddiesByNameSync(final String name) {
    final String _sql = "SELECT * FROM buddiesTable WHERE name == ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfHealth = CursorUtil.getColumnIndexOrThrow(_cursor, "health");
      final int _cursorIndexOfAttack = CursorUtil.getColumnIndexOrThrow(_cursor, "attack");
      final int _cursorIndexOfDefense = CursorUtil.getColumnIndexOrThrow(_cursor, "defense");
      final int _cursorIndexOfExp = CursorUtil.getColumnIndexOrThrow(_cursor, "exp");
      final int _cursorIndexOfImageSource = CursorUtil.getColumnIndexOrThrow(_cursor, "imageSource");
      final int _cursorIndexOfIsStarter = CursorUtil.getColumnIndexOrThrow(_cursor, "isStarter");
      final Buddies _result;
      if (_cursor.moveToFirst()) {
        _result = new Buddies();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _result.setName(_tmpName);
        final int _tmpHealth;
        _tmpHealth = _cursor.getInt(_cursorIndexOfHealth);
        _result.setHealth(_tmpHealth);
        final int _tmpAttack;
        _tmpAttack = _cursor.getInt(_cursorIndexOfAttack);
        _result.setAttack(_tmpAttack);
        final int _tmpDefense;
        _tmpDefense = _cursor.getInt(_cursorIndexOfDefense);
        _result.setDefense(_tmpDefense);
        final int _tmpExp;
        _tmpExp = _cursor.getInt(_cursorIndexOfExp);
        _result.setExp(_tmpExp);
        final String _tmpImageSource;
        if (_cursor.isNull(_cursorIndexOfImageSource)) {
          _tmpImageSource = null;
        } else {
          _tmpImageSource = _cursor.getString(_cursorIndexOfImageSource);
        }
        _result.setImageSource(_tmpImageSource);
        final boolean _tmpIsStarter;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsStarter);
        _tmpIsStarter = _tmp != 0;
        _result.setStarter(_tmpIsStarter);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Buddies> getBuddiesById(final int buddiesId) {
    final String _sql = "SELECT * FROM buddiesTable WHERE id == ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, buddiesId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"buddiesTable"}, false, new Callable<Buddies>() {
      @Override
      @Nullable
      public Buddies call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfHealth = CursorUtil.getColumnIndexOrThrow(_cursor, "health");
          final int _cursorIndexOfAttack = CursorUtil.getColumnIndexOrThrow(_cursor, "attack");
          final int _cursorIndexOfDefense = CursorUtil.getColumnIndexOrThrow(_cursor, "defense");
          final int _cursorIndexOfExp = CursorUtil.getColumnIndexOrThrow(_cursor, "exp");
          final int _cursorIndexOfImageSource = CursorUtil.getColumnIndexOrThrow(_cursor, "imageSource");
          final int _cursorIndexOfIsStarter = CursorUtil.getColumnIndexOrThrow(_cursor, "isStarter");
          final Buddies _result;
          if (_cursor.moveToFirst()) {
            _result = new Buddies();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _result.setId(_tmpId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _result.setName(_tmpName);
            final int _tmpHealth;
            _tmpHealth = _cursor.getInt(_cursorIndexOfHealth);
            _result.setHealth(_tmpHealth);
            final int _tmpAttack;
            _tmpAttack = _cursor.getInt(_cursorIndexOfAttack);
            _result.setAttack(_tmpAttack);
            final int _tmpDefense;
            _tmpDefense = _cursor.getInt(_cursorIndexOfDefense);
            _result.setDefense(_tmpDefense);
            final int _tmpExp;
            _tmpExp = _cursor.getInt(_cursorIndexOfExp);
            _result.setExp(_tmpExp);
            final String _tmpImageSource;
            if (_cursor.isNull(_cursorIndexOfImageSource)) {
              _tmpImageSource = null;
            } else {
              _tmpImageSource = _cursor.getString(_cursorIndexOfImageSource);
            }
            _result.setImageSource(_tmpImageSource);
            final boolean _tmpIsStarter;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsStarter);
            _tmpIsStarter = _tmp != 0;
            _result.setStarter(_tmpIsStarter);
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
  public Buddies getBuddiesByIdSync(final int buddiesId) {
    final String _sql = "SELECT * FROM buddiesTable WHERE id == ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, buddiesId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfHealth = CursorUtil.getColumnIndexOrThrow(_cursor, "health");
      final int _cursorIndexOfAttack = CursorUtil.getColumnIndexOrThrow(_cursor, "attack");
      final int _cursorIndexOfDefense = CursorUtil.getColumnIndexOrThrow(_cursor, "defense");
      final int _cursorIndexOfExp = CursorUtil.getColumnIndexOrThrow(_cursor, "exp");
      final int _cursorIndexOfImageSource = CursorUtil.getColumnIndexOrThrow(_cursor, "imageSource");
      final int _cursorIndexOfIsStarter = CursorUtil.getColumnIndexOrThrow(_cursor, "isStarter");
      final Buddies _result;
      if (_cursor.moveToFirst()) {
        _result = new Buddies();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _result.setName(_tmpName);
        final int _tmpHealth;
        _tmpHealth = _cursor.getInt(_cursorIndexOfHealth);
        _result.setHealth(_tmpHealth);
        final int _tmpAttack;
        _tmpAttack = _cursor.getInt(_cursorIndexOfAttack);
        _result.setAttack(_tmpAttack);
        final int _tmpDefense;
        _tmpDefense = _cursor.getInt(_cursorIndexOfDefense);
        _result.setDefense(_tmpDefense);
        final int _tmpExp;
        _tmpExp = _cursor.getInt(_cursorIndexOfExp);
        _result.setExp(_tmpExp);
        final String _tmpImageSource;
        if (_cursor.isNull(_cursorIndexOfImageSource)) {
          _tmpImageSource = null;
        } else {
          _tmpImageSource = _cursor.getString(_cursorIndexOfImageSource);
        }
        _result.setImageSource(_tmpImageSource);
        final boolean _tmpIsStarter;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsStarter);
        _tmpIsStarter = _tmp != 0;
        _result.setStarter(_tmpIsStarter);
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
