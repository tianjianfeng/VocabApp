package com.vocabapp.data.dao

import androidx.room.*
import com.vocabapp.data.entities.VocabList
import com.vocabapp.data.entities.VocabListWithWords
import kotlinx.coroutines.flow.Flow

@Dao
interface VocabListDao {
    
    @Query("SELECT * FROM vocab_lists ORDER BY createdAt DESC")
    fun getAllLists(): Flow<List<VocabList>>
    
    @Query("SELECT * FROM vocab_lists WHERE id = :listId")
    suspend fun getListById(listId: Long): VocabList?
    
    @Transaction
    @Query("SELECT * FROM vocab_lists WHERE id = :listId")
    fun getListWithWords(listId: Long): Flow<VocabListWithWords?>
    
    @Query("SELECT COUNT(*) FROM words WHERE listId = :listId")
    fun getWordCount(listId: Long): Flow<Int>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(vocabList: VocabList): Long
    
    @Update
    suspend fun updateList(vocabList: VocabList)
    
    @Delete
    suspend fun deleteList(vocabList: VocabList)
    
    @Query("DELETE FROM vocab_lists WHERE id = :listId")
    suspend fun deleteListById(listId: Long)
}

