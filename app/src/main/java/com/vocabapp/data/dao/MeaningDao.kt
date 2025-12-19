package com.vocabapp.data.dao

import androidx.room.*
import com.vocabapp.data.entities.Meaning
import kotlinx.coroutines.flow.Flow

@Dao
interface MeaningDao {
    
    @Query("SELECT * FROM meanings WHERE wordId = :wordId ORDER BY id ASC")
    fun getMeaningsByWord(wordId: Long): Flow<List<Meaning>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeaning(meaning: Meaning): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeanings(meanings: List<Meaning>): List<Long>
    
    @Update
    suspend fun updateMeaning(meaning: Meaning)
    
    @Delete
    suspend fun deleteMeaning(meaning: Meaning)
    
    @Query("DELETE FROM meanings WHERE id = :meaningId")
    suspend fun deleteMeaningById(meaningId: Long)
    
    @Query("DELETE FROM meanings WHERE wordId = :wordId")
    suspend fun deleteMeaningsByWord(wordId: Long)
}

