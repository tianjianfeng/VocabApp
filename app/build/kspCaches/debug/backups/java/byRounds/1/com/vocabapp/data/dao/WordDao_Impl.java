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
import com.vocabapp.data.entities.Word;
import com.vocabapp.data.entities.WordWithMeanings;
import java.lang.Class;
import java.lang.Exception;
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
public final class WordDao_Impl implements WordDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Word> __insertionAdapterOfWord;

  private final EntityDeletionOrUpdateAdapter<Word> __deletionAdapterOfWord;

  private final EntityDeletionOrUpdateAdapter<Word> __updateAdapterOfWord;

  private final SharedSQLiteStatement __preparedStmtOfDeleteWordById;

  public WordDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWord = new EntityInsertionAdapter<Word>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `words` (`id`,`listId`,`word`,`phonetic`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Word entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getListId());
        statement.bindString(3, entity.getWord());
        statement.bindString(4, entity.getPhonetic());
      }
    };
    this.__deletionAdapterOfWord = new EntityDeletionOrUpdateAdapter<Word>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `words` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Word entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfWord = new EntityDeletionOrUpdateAdapter<Word>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `words` SET `id` = ?,`listId` = ?,`word` = ?,`phonetic` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Word entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getListId());
        statement.bindString(3, entity.getWord());
        statement.bindString(4, entity.getPhonetic());
        statement.bindLong(5, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteWordById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM words WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertWord(final Word word, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfWord.insertAndReturnId(word);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertWords(final List<Word> words,
      final Continuation<? super List<Long>> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<List<Long>>() {
      @Override
      @NonNull
      public List<Long> call() throws Exception {
        __db.beginTransaction();
        try {
          final List<Long> _result = __insertionAdapterOfWord.insertAndReturnIdsList(words);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteWord(final Word word, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfWord.handle(word);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateWord(final Word word, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfWord.handle(word);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteWordById(final long wordId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteWordById.acquire();
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
          __preparedStmtOfDeleteWordById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Word>> getWordsByList(final long listId) {
    final String _sql = "SELECT * FROM words WHERE listId = ? ORDER BY word ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, listId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"words"}, new Callable<List<Word>>() {
      @Override
      @NonNull
      public List<Word> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfListId = CursorUtil.getColumnIndexOrThrow(_cursor, "listId");
          final int _cursorIndexOfWord = CursorUtil.getColumnIndexOrThrow(_cursor, "word");
          final int _cursorIndexOfPhonetic = CursorUtil.getColumnIndexOrThrow(_cursor, "phonetic");
          final List<Word> _result = new ArrayList<Word>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Word _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpListId;
            _tmpListId = _cursor.getLong(_cursorIndexOfListId);
            final String _tmpWord;
            _tmpWord = _cursor.getString(_cursorIndexOfWord);
            final String _tmpPhonetic;
            _tmpPhonetic = _cursor.getString(_cursorIndexOfPhonetic);
            _item = new Word(_tmpId,_tmpListId,_tmpWord,_tmpPhonetic);
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
  public Flow<List<WordWithMeanings>> getWordsWithMeaningsByList(final long listId) {
    final String _sql = "SELECT * FROM words WHERE listId = ? ORDER BY word ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, listId);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"meanings",
        "words"}, new Callable<List<WordWithMeanings>>() {
      @Override
      @NonNull
      public List<WordWithMeanings> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfListId = CursorUtil.getColumnIndexOrThrow(_cursor, "listId");
            final int _cursorIndexOfWord = CursorUtil.getColumnIndexOrThrow(_cursor, "word");
            final int _cursorIndexOfPhonetic = CursorUtil.getColumnIndexOrThrow(_cursor, "phonetic");
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
            final List<WordWithMeanings> _result = new ArrayList<WordWithMeanings>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final WordWithMeanings _item;
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
              final long _tmpKey_1;
              _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
              _tmpMeaningsCollection = _collectionMeanings.get(_tmpKey_1);
              _item = new WordWithMeanings(_tmpWord,_tmpMeaningsCollection);
              _result.add(_item);
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
  public Object getWordWithMeanings(final long wordId,
      final Continuation<? super WordWithMeanings> $completion) {
    final String _sql = "SELECT * FROM words WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, wordId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, true, _cancellationSignal, new Callable<WordWithMeanings>() {
      @Override
      @Nullable
      public WordWithMeanings call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfListId = CursorUtil.getColumnIndexOrThrow(_cursor, "listId");
            final int _cursorIndexOfWord = CursorUtil.getColumnIndexOrThrow(_cursor, "word");
            final int _cursorIndexOfPhonetic = CursorUtil.getColumnIndexOrThrow(_cursor, "phonetic");
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
            final WordWithMeanings _result;
            if (_cursor.moveToFirst()) {
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
              final long _tmpKey_1;
              _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
              _tmpMeaningsCollection = _collectionMeanings.get(_tmpKey_1);
              _result = new WordWithMeanings(_tmpWord,_tmpMeaningsCollection);
            } else {
              _result = null;
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
            _statement.release();
          }
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
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
}
