package com.vocabapp.data;

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
import com.vocabapp.data.dao.MeaningDao;
import com.vocabapp.data.dao.MeaningDao_Impl;
import com.vocabapp.data.dao.VocabListDao;
import com.vocabapp.data.dao.VocabListDao_Impl;
import com.vocabapp.data.dao.WordDao;
import com.vocabapp.data.dao.WordDao_Impl;
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
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class VocabDatabase_Impl extends VocabDatabase {
  private volatile VocabListDao _vocabListDao;

  private volatile WordDao _wordDao;

  private volatile MeaningDao _meaningDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `vocab_lists` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `words` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `listId` INTEGER NOT NULL, `word` TEXT NOT NULL, `phonetic` TEXT NOT NULL, FOREIGN KEY(`listId`) REFERENCES `vocab_lists`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_words_listId` ON `words` (`listId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `meanings` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `wordId` INTEGER NOT NULL, `partOfSpeech` TEXT NOT NULL, `definition` TEXT NOT NULL, FOREIGN KEY(`wordId`) REFERENCES `words`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_meanings_wordId` ON `meanings` (`wordId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '463a6b1e3d64c3744c8072df06f624e6')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `vocab_lists`");
        db.execSQL("DROP TABLE IF EXISTS `words`");
        db.execSQL("DROP TABLE IF EXISTS `meanings`");
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
        final HashMap<String, TableInfo.Column> _columnsVocabLists = new HashMap<String, TableInfo.Column>(4);
        _columnsVocabLists.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVocabLists.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVocabLists.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVocabLists.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVocabLists = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVocabLists = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVocabLists = new TableInfo("vocab_lists", _columnsVocabLists, _foreignKeysVocabLists, _indicesVocabLists);
        final TableInfo _existingVocabLists = TableInfo.read(db, "vocab_lists");
        if (!_infoVocabLists.equals(_existingVocabLists)) {
          return new RoomOpenHelper.ValidationResult(false, "vocab_lists(com.vocabapp.data.entities.VocabList).\n"
                  + " Expected:\n" + _infoVocabLists + "\n"
                  + " Found:\n" + _existingVocabLists);
        }
        final HashMap<String, TableInfo.Column> _columnsWords = new HashMap<String, TableInfo.Column>(4);
        _columnsWords.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("listId", new TableInfo.Column("listId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("word", new TableInfo.Column("word", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWords.put("phonetic", new TableInfo.Column("phonetic", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWords = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysWords.add(new TableInfo.ForeignKey("vocab_lists", "CASCADE", "NO ACTION", Arrays.asList("listId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesWords = new HashSet<TableInfo.Index>(1);
        _indicesWords.add(new TableInfo.Index("index_words_listId", false, Arrays.asList("listId"), Arrays.asList("ASC")));
        final TableInfo _infoWords = new TableInfo("words", _columnsWords, _foreignKeysWords, _indicesWords);
        final TableInfo _existingWords = TableInfo.read(db, "words");
        if (!_infoWords.equals(_existingWords)) {
          return new RoomOpenHelper.ValidationResult(false, "words(com.vocabapp.data.entities.Word).\n"
                  + " Expected:\n" + _infoWords + "\n"
                  + " Found:\n" + _existingWords);
        }
        final HashMap<String, TableInfo.Column> _columnsMeanings = new HashMap<String, TableInfo.Column>(4);
        _columnsMeanings.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMeanings.put("wordId", new TableInfo.Column("wordId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMeanings.put("partOfSpeech", new TableInfo.Column("partOfSpeech", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMeanings.put("definition", new TableInfo.Column("definition", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMeanings = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysMeanings.add(new TableInfo.ForeignKey("words", "CASCADE", "NO ACTION", Arrays.asList("wordId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesMeanings = new HashSet<TableInfo.Index>(1);
        _indicesMeanings.add(new TableInfo.Index("index_meanings_wordId", false, Arrays.asList("wordId"), Arrays.asList("ASC")));
        final TableInfo _infoMeanings = new TableInfo("meanings", _columnsMeanings, _foreignKeysMeanings, _indicesMeanings);
        final TableInfo _existingMeanings = TableInfo.read(db, "meanings");
        if (!_infoMeanings.equals(_existingMeanings)) {
          return new RoomOpenHelper.ValidationResult(false, "meanings(com.vocabapp.data.entities.Meaning).\n"
                  + " Expected:\n" + _infoMeanings + "\n"
                  + " Found:\n" + _existingMeanings);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "463a6b1e3d64c3744c8072df06f624e6", "a1357b75df650efd417ca1db21d3fc80");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "vocab_lists","words","meanings");
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
      _db.execSQL("DELETE FROM `vocab_lists`");
      _db.execSQL("DELETE FROM `words`");
      _db.execSQL("DELETE FROM `meanings`");
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
    _typeConvertersMap.put(VocabListDao.class, VocabListDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(WordDao.class, WordDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MeaningDao.class, MeaningDao_Impl.getRequiredConverters());
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
  public VocabListDao vocabListDao() {
    if (_vocabListDao != null) {
      return _vocabListDao;
    } else {
      synchronized(this) {
        if(_vocabListDao == null) {
          _vocabListDao = new VocabListDao_Impl(this);
        }
        return _vocabListDao;
      }
    }
  }

  @Override
  public WordDao wordDao() {
    if (_wordDao != null) {
      return _wordDao;
    } else {
      synchronized(this) {
        if(_wordDao == null) {
          _wordDao = new WordDao_Impl(this);
        }
        return _wordDao;
      }
    }
  }

  @Override
  public MeaningDao meaningDao() {
    if (_meaningDao != null) {
      return _meaningDao;
    } else {
      synchronized(this) {
        if(_meaningDao == null) {
          _meaningDao = new MeaningDao_Impl(this);
        }
        return _meaningDao;
      }
    }
  }
}
