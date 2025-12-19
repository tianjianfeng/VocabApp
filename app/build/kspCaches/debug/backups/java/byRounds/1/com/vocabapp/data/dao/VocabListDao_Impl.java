package com.vocabapp.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LongSparseArray;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.RelationUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.vocabapp.data.entities.Meaning;
import com.vocabapp.data.entities.VocabList;
import com.vocabapp.data.entities.VocabListWithWords;
import com.vocabapp.data.entities.Word;
import com.vocabapp.data.entities.WordWithMeanings;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
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
public final class VocabListDao_Impl implements VocabListDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<VocabList> __insertionAdapterOfVocabList;

  private final EntityDeletionOrUpdateAdapter<VocabList> __deletionAdapterOfVocabList;

  private final EntityDeletionOrUpdateAdapter<VocabList> __updateAdapterOfVocabList;

  private final SharedSQLiteStatement __preparedStmtOfDeleteListById;

  public VocabListDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVocabList = new EntityInsertionAdapter<VocabList>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `vocab_lists` (`id`,`name`,`description`,`createdAt`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VocabList entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getDescription());
        statement.bindLong(4, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfVocabList = new EntityDeletionOrUpdateAdapter<VocabList>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `vocab_lists` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VocabList entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfVocabList = new EntityDeletionOrUpdateAdapter<VocabList>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `vocab_lists` SET `id` = ?,`name` = ?,`description` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VocabList entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getDescription());
        statement.bindLong(4, entity.getCreatedAt());
        statement.bindLong(5, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteListById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM vocab_lists WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertList(final VocabList vocabList,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfVocabList.insertAndReturnId(vocabList);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteList(final VocabList vocabList,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfVocabList.handle(vocabList);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateList(final VocabList vocabList,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfVocabList.handle(vocabList);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteListById(final long listId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteListById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, listId);
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
          __preparedStmtOfDeleteListById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<VocabList>> getAllLists() {
    final String _sql = "SELECT * FROM vocab_lists ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"vocab_lists"}, new Callable<List<VocabList>>() {
      @Override
      @NonNull
      public List<VocabList> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<VocabList> _result = new ArrayList<VocabList>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VocabList _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new VocabList(_tmpId,_tmpName,_tmpDescription,_tmpCreatedAt);
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
  public Object getListById(final long listId, final Continuation<? super VocabList> $completion) {
    final String _sql = "SELECT * FROM vocab_lists WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, listId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<VocabList>() {
      @Override
      @Nullable
      public VocabList call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final VocabList _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new VocabList(_tmpId,_tmpName,_tmpDescription,_tmpCreatedAt);
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
  public Flow<VocabListWithWords> getListWithWords(final long listId) {
    final String _sql = "SELECT * FROM vocab_lists WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, listId);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"meanings", "words",
        "vocab_lists"}, new Callable<VocabListWithWords>() {
      @Override
      @Nullable
      public VocabListWithWords call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
            final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
            final LongSparseArray<ArrayList<WordWithMeanings>> _collectionWords = new LongSparseArray<ArrayList<WordWithMeanings>>();
            while (_cursor.moveToNext()) {
              final long _tmpKey;
              _tmpKey = _cursor.getLong(_cursorIndexOfId);
              if (!_collectionWords.containsKey(_tmpKey)) {
                _collectionWords.put(_tmpKey, new ArrayList<WordWithMeanings>());
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipwordsAscomVocabappDataEntitiesWordWithMeanings(_collectionWords);
            final VocabListWithWords _result;
            if (_cursor.moveToFirst()) {
              final VocabList _tmpVocabList;
              final long _tmpId;
              _tmpId = _cursor.getLong(_cursorIndexOfId);
              final String _tmpName;
              _tmpName = _cursor.getString(_cursorIndexOfName);
              final String _tmpDescription;
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
              final long _tmpCreatedAt;
              _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
              _tmpVocabList = new VocabList(_tmpId,_tmpName,_tmpDescription,_tmpCreatedAt);
              final ArrayList<WordWithMeanings> _tmpWordsCollection;
              final long _tmpKey_1;
              _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
              _tmpWordsCollection = _collectionWords.get(_tmpKey_1);
              _result = new VocabListWithWords(_tmpVocabList,_tmpWordsCollection);
            } else {
              _result = null;
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Integer> getWordCount(final long listId) {
    final String _sql = "SELECT COUNT(*) FROM words WHERE listId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, listId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"words"}, new Callable<Integer>() {
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

  private void __fetchRelationshipmeaningsAscomVocabappDataEntitiesMeaning(
      @NonNull final LongSparseArray<ArrayList<Meaning>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchLongSparseArray(_map, true, (map) -> {
        __fetchRelationshipmeaningsAscomVocabappDataEntitiesMeaning(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`wordId`,`partOfSpeech`,`definition` FROM `meanings` WHERE `wordId` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      final long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "wordId");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfWordId = 1;
      final int _cursorIndexOfPartOfSpeech = 2;
      final int _cursorIndexOfDefinition = 3;
      while (_cursor.moveToNext()) {
        final long _tmpKey;
        _tmpKey = _cursor.getLong(_itemKeyIndex);
        final ArrayList<Meaning> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final Meaning _item_1;
          final long _tmpId;
          _tmpId = _cursor.getLong(_cursorIndexOfId);
          final long _tmpWordId;
          _tmpWordId = _cursor.getLong(_cursorIndexOfWordId);
          final String _tmpPartOfSpeech;
          _tmpPartOfSpeech = _cursor.getString(_cursorIndexOfPartOfSpeech);
          final String _tmpDefinition;
          _tmpDefinition = _cursor.getString(_cursorIndexOfDefinition);
          _item_1 = new Meaning(_tmpId,_tmpWordId,_tmpPartOfSpeech,_tmpDefinition);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private void __fetchRelationshipwordsAscomVocabappDataEntitiesWordWithMeanings(
      @NonNull final LongSparseArray<ArrayList<WordWithMeanings>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchLongSparseArray(_map, true, (map) -> {
        __fetchRelationshipwordsAscomVocabappDataEntitiesWordWithMeanings(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`listId`,`word`,`phonetic` FROM `words` WHERE `listId` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      final long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, true, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "listId");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfListId = 1;
      final int _cursorIndexOfWord = 2;
      final int _cursorIndexOfPhonetic = 3;
      final LongSparseArray<ArrayList<Meaning>> _collectionMeanings = new LongSparseArray<ArrayList<Meaning>>();
      while (_cursor.moveToNext()) {
        final long _tmpKey;
        _tmpKey = _cursor.getLong(_cursorIndexOfId);
        if (!_collectionMeanings.containsKey(_tmpKey)) {
          _collectionMeanings.put(_tmpKey, new ArrayList<Meaning>());
        }
      }
      _cursor.moveToPosition(-1);
      __fetchRelationshipmeaningsAscomVocabappDataEntitiesMeaning(_collectionMeanings);
      while (_cursor.moveToNext()) {
        final long _tmpKey_1;
        _tmpKey_1 = _cursor.getLong(_itemKeyIndex);
        final ArrayList<WordWithMeanings> _tmpRelation = _map.get(_tmpKey_1);
        if (_tmpRelation != null) {
          final WordWithMeanings _item_1;
          final Word _tmpWord;
          final long _tmpId;
          _tmpId = _cursor.getLong(_cursorIndexOfId);
          final long _tmpListId;
          _tmpListId = _cursor.getLong(_cursorIndexOfListId);
          final String _tmpWord_1;
          _tmpWord_1 = _cursor.getString(_cursorIndexOfWord);
          final String _tmpPhonetic;
          _tmpPhonetic = _cursor.getString(_cursorIndexOfPhonetic);
          _tmpWord = new Word(_tmpId,_tmpListId,_tmpWord_1,_tmpPhonetic);
          final ArrayList<Meaning> _tmpMeaningsCollection;
          final long _tmpKey_2;
          _tmpKey_2 = _cursor.getLong(_cursorIndexOfId);
          _tmpMeaningsCollection = _collectionMeanings.get(_tmpKey_2);
          _item_1 = new WordWithMeanings(_tmpWord,_tmpMeaningsCollection);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
