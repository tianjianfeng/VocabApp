package com.vocabapp.data.dao

import androidx.room.*
import com.vocabapp.data.entities.Word
import com.vocabapp.data.entities.WordWithMeanings
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    
    @Query("SELECT * FROM words WHERE listId = :listId ORDER BY word ASC")
    fun getWordsByList(listId: Long): Flow<List<Word>>
    
    @Transaction
    @Query("SELECT * FROM words WHERE listId = :listId ORDER BY word ASC")
    fun getWordsWithMeaningsByList(listId: Long): Flow<List<WordWithMeanings>>
    
    @Transaction
    @Query("SELECT * FROM words WHERE id = :wordId")
    suspend fun getWordWithMeanings(wordId: Long): WordWithMeanings?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: Word): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<Word>): List<Long>
    
    @Update
    suspend fun updateWord(word: Word)
    
    @Delete
    suspend fun deleteWord(word: Word)
    
    @Query("DELETE FROM words WHERE id = :wordId")
    suspend fun deleteWordById(wordId: Long)
}

