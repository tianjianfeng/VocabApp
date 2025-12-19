package com.vocabapp.data.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.vocabapp.data.entities.Meaning;
import java.lang.Class;
import java.lang.Exception;
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
public final class MeaningDao_Impl implements MeaningDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Meaning> __insertionAdapterOfMeaning;

  private final EntityDeletionOrUpdateAdapter<Meaning> __deletionAdapterOfMeaning;

  private final EntityDeletionOrUpdateAdapter<Meaning> __updateAdapterOfMeaning;

  private final SharedSQLiteStatement __preparedStmtOfDeleteMeaningById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteMeaningsByWord;

  public MeaningDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMeaning = new EntityInsertionAdapter<Meaning>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `meanings` (`id`,`wordId`,`partOfSpeech`,`definition`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Meaning entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getWordId());
        statement.bindString(3, entity.getPartOfSpeech());
        statement.bindString(4, entity.getDefinition());
      }
    };
    this.__deletionAdapterOfMeaning = new EntityDeletionOrUpdateAdapter<Meaning>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `meanings` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Meaning entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfMeaning = new EntityDeletionOrUpdateAdapter<Meaning>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `meanings` SET `id` = ?,`wordId` = ?,`partOfSpeech` = ?,`definition` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Meaning entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getWordId());
        statement.bindString(3, entity.getPartOfSpeech());
        statement.bindString(4, entity.getDefinition());
        statement.bindLong(5, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteMeaningById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM meanings WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteMeaningsByWord = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM meanings WHERE wordId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertMeaning(final Meaning meaning, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfMeaning.insertAndReturnId(meaning);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertMeanings(final List<Meaning> meanings,
      final Continuation<? super List<Long>> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<List<Long>>() {
      @Override
      @NonNull
      public List<Long> call() throws Exception {
        __db.beginTransaction();
        try {
          final List<Long> _result = __insertionAdapterOfMeaning.insertAndReturnIdsList(meanings);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteMeaning(final Meaning meaning, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfMeaning.handle(meaning);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateMeaning(final Meaning meaning, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfMeaning.handle(meaning);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteMeaningById(final long meaningId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteMeaningById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, meaningId);
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
          __preparedStmtOfDeleteMeaningById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteMeaningsByWord(final long wordId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteMeaningsByWord.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, wordId);
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
          __preparedStmtOfDeleteMeaningsByWord.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Meaning>> getMeaningsByWord(final long wordId) {
    final String _sql = "SELECT * FROM meanings WHERE wordId = ? ORDER BY id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, wordId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"meanings"}, new Callable<List<Meaning>>() {
      @Override
      @NonNull
      public List<Meaning> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWordId = CursorUtil.getColumnIndexOrThrow(_cursor, "wordId");
          final int _cursorIndexOfPartOfSpeech = CursorUtil.getColumnIndexOrThrow(_cursor, "partOfSpeech");
          final int _cursorIndexOfDefinition = CursorUtil.getColumnIndexOrThrow(_cursor, "definition");
          final List<Meaning> _result = new ArrayList<Meaning>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Meaning _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpWordId;
            _tmpWordId = _cursor.getLong(_cursorIndexOfWordId);
            final String _tmpPartOfSpeech;
            _tmpPartOfSpeech = _cursor.getString(_cursorIndexOfPartOfSpeech);
            final String _tmpDefinition;
            _tmpDefinition = _cursor.getString(_cursorIndexOfDefinition);
            _item = new Meaning(_tmpId,_tmpWordId,_tmpPartOfSpeech,_tmpDefinition);
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
