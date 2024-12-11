package com.example.project2.database;

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
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class CreatureBuddyDatabase_Impl extends CreatureBuddyDatabase {
  private volatile UserDAO _userDAO;

  private volatile BuddiesDAO _buddiesDAO;

  private volatile BuddyRankingDAO _buddyRankingDAO;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(6) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `usertable` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `username` TEXT, `password` TEXT, `isAdmin` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `buddiesTable` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `health` INTEGER NOT NULL, `attack` INTEGER NOT NULL, `defense` INTEGER NOT NULL, `exp` INTEGER NOT NULL, `imageSource` TEXT, `isStarter` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `buddyRankingTable` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `buddyId` INTEGER NOT NULL, `wins` INTEGER NOT NULL, `losses` INTEGER NOT NULL, `winPercentage` REAL NOT NULL, FOREIGN KEY(`buddyId`) REFERENCES `buddiesTable`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'f71091726b6f87b80e005ac41ee09375')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `usertable`");
        db.execSQL("DROP TABLE IF EXISTS `buddiesTable`");
        db.execSQL("DROP TABLE IF EXISTS `buddyRankingTable`");
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
        db.execSQL("PRAGMA foreign_keys = ON");
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
        final HashMap<String, TableInfo.Column> _columnsUsertable = new HashMap<String, TableInfo.Column>(4);
        _columnsUsertable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsertable.put("username", new TableInfo.Column("username", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsertable.put("password", new TableInfo.Column("password", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsertable.put("isAdmin", new TableInfo.Column("isAdmin", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsertable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsertable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsertable = new TableInfo("usertable", _columnsUsertable, _foreignKeysUsertable, _indicesUsertable);
        final TableInfo _existingUsertable = TableInfo.read(db, "usertable");
        if (!_infoUsertable.equals(_existingUsertable)) {
          return new RoomOpenHelper.ValidationResult(false, "usertable(com.example.project2.database.entities.User).\n"
                  + " Expected:\n" + _infoUsertable + "\n"
                  + " Found:\n" + _existingUsertable);
        }
        final HashMap<String, TableInfo.Column> _columnsBuddiesTable = new HashMap<String, TableInfo.Column>(8);
        _columnsBuddiesTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBuddiesTable.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBuddiesTable.put("health", new TableInfo.Column("health", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBuddiesTable.put("attack", new TableInfo.Column("attack", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBuddiesTable.put("defense", new TableInfo.Column("defense", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBuddiesTable.put("exp", new TableInfo.Column("exp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBuddiesTable.put("imageSource", new TableInfo.Column("imageSource", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBuddiesTable.put("isStarter", new TableInfo.Column("isStarter", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBuddiesTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBuddiesTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBuddiesTable = new TableInfo("buddiesTable", _columnsBuddiesTable, _foreignKeysBuddiesTable, _indicesBuddiesTable);
        final TableInfo _existingBuddiesTable = TableInfo.read(db, "buddiesTable");
        if (!_infoBuddiesTable.equals(_existingBuddiesTable)) {
          return new RoomOpenHelper.ValidationResult(false, "buddiesTable(com.example.project2.database.entities.Buddies).\n"
                  + " Expected:\n" + _infoBuddiesTable + "\n"
                  + " Found:\n" + _existingBuddiesTable);
        }
        final HashMap<String, TableInfo.Column> _columnsBuddyRankingTable = new HashMap<String, TableInfo.Column>(5);
        _columnsBuddyRankingTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBuddyRankingTable.put("buddyId", new TableInfo.Column("buddyId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBuddyRankingTable.put("wins", new TableInfo.Column("wins", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBuddyRankingTable.put("losses", new TableInfo.Column("losses", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBuddyRankingTable.put("winPercentage", new TableInfo.Column("winPercentage", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBuddyRankingTable = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysBuddyRankingTable.add(new TableInfo.ForeignKey("buddiesTable", "CASCADE", "NO ACTION", Arrays.asList("buddyId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesBuddyRankingTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBuddyRankingTable = new TableInfo("buddyRankingTable", _columnsBuddyRankingTable, _foreignKeysBuddyRankingTable, _indicesBuddyRankingTable);
        final TableInfo _existingBuddyRankingTable = TableInfo.read(db, "buddyRankingTable");
        if (!_infoBuddyRankingTable.equals(_existingBuddyRankingTable)) {
          return new RoomOpenHelper.ValidationResult(false, "buddyRankingTable(com.example.project2.database.entities.BuddyRanking).\n"
                  + " Expected:\n" + _infoBuddyRankingTable + "\n"
                  + " Found:\n" + _existingBuddyRankingTable);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "f71091726b6f87b80e005ac41ee09375", "7290cd098950097df7ce548cb500a2f8");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "usertable","buddiesTable","buddyRankingTable");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `usertable`");
      _db.execSQL("DELETE FROM `buddiesTable`");
      _db.execSQL("DELETE FROM `buddyRankingTable`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
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
    _typeConvertersMap.put(UserDAO.class, UserDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(BuddiesDAO.class, BuddiesDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(BuddyRankingDAO.class, BuddyRankingDAO_Impl.getRequiredConverters());
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
  public UserDAO userDAO() {
    if (_userDAO != null) {
      return _userDAO;
    } else {
      synchronized(this) {
        if(_userDAO == null) {
          _userDAO = new UserDAO_Impl(this);
        }
        return _userDAO;
      }
    }
  }

  @Override
  public BuddiesDAO buddiesDAO() {
    if (_buddiesDAO != null) {
      return _buddiesDAO;
    } else {
      synchronized(this) {
        if(_buddiesDAO == null) {
          _buddiesDAO = new BuddiesDAO_Impl(this);
        }
        return _buddiesDAO;
      }
    }
  }

  @Override
  public BuddyRankingDAO buddyRankingDAO() {
    if (_buddyRankingDAO != null) {
      return _buddyRankingDAO;
    } else {
      synchronized(this) {
        if(_buddyRankingDAO == null) {
          _buddyRankingDAO = new BuddyRankingDAO_Impl(this);
        }
        return _buddyRankingDAO;
      }
    }
  }
}
