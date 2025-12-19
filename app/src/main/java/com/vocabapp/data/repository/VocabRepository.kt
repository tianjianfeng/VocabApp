package com.vocabapp.data.repository

import com.vocabapp.data.dao.MeaningDao
import com.vocabapp.data.dao.VocabListDao
import com.vocabapp.data.dao.WordDao
import com.vocabapp.data.entities.*
import kotlinx.coroutines.flow.Flow

class VocabRepository(
    private val vocabListDao: VocabListDao,
    private val wordDao: WordDao,
    private val meaningDao: MeaningDao
) {
    // VocabList operations
    fun getAllLists(): Flow<List<VocabList>> = vocabListDao.getAllLists()
    
    suspend fun getListById(listId: Long): VocabList? = vocabListDao.getListById(listId)
    
    fun getListWithWords(listId: Long): Flow<VocabListWithWords?> = vocabListDao.getListWithWords(listId)
    
    fun getWordCount(listId: Long): Flow<Int> = vocabListDao.getWordCount(listId)
    
    suspend fun insertList(vocabList: VocabList): Long = vocabListDao.insertList(vocabList)
    
    suspend fun updateList(vocabList: VocabList) = vocabListDao.updateList(vocabList)
    
    suspend fun deleteList(vocabList: VocabList) = vocabListDao.deleteList(vocabList)
    
    // Word operations
    fun getWordsWithMeaningsByList(listId: Long): Flow<List<WordWithMeanings>> = 
        wordDao.getWordsWithMeaningsByList(listId)
    
    suspend fun insertWord(word: Word): Long = wordDao.insertWord(word)
    
    suspend fun insertWordWithMeanings(word: Word, meanings: List<Meaning>): Long {
        val wordId = wordDao.insertWord(word)
        val meaningsWithWordId = meanings.map { it.copy(wordId = wordId) }
        meaningDao.insertMeanings(meaningsWithWordId)
        return wordId
    }
    
    suspend fun deleteWord(word: Word) = wordDao.deleteWord(word)
    
    // Meaning operations
    suspend fun insertMeaning(meaning: Meaning): Long = meaningDao.insertMeaning(meaning)
    
    suspend fun insertMeanings(meanings: List<Meaning>): List<Long> = meaningDao.insertMeanings(meanings)
}

