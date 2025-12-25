package com.vocabapp.data.repository

import com.vocabapp.data.dao.CollectionDao
import com.vocabapp.data.dao.MeaningDao
import com.vocabapp.data.dao.VocabListDao
import com.vocabapp.data.dao.WordDao
import com.vocabapp.data.entities.CollectionListCrossRef
import com.vocabapp.data.entities.CollectionWithLists
import com.vocabapp.data.entities.Meaning
import com.vocabapp.data.entities.VocabList
import com.vocabapp.data.entities.VocabListWithWords
import com.vocabapp.data.entities.Word
import com.vocabapp.data.entities.WordWithMeanings
import com.vocabapp.data.entities.Collection as VocabCollection
import kotlinx.coroutines.flow.Flow

class VocabRepository(
    private val vocabListDao: VocabListDao,
    private val wordDao: WordDao,
    private val meaningDao: MeaningDao,
    private val collectionDao: CollectionDao? = null
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
    
    // Collection operations
    fun getAllCollections(): Flow<List<VocabCollection>> = 
        collectionDao?.getAllCollections() ?: throw IllegalStateException("CollectionDao not initialized")
    
    fun getAllCollectionsWithLists(): Flow<List<CollectionWithLists>> =
        collectionDao?.getAllCollectionsWithLists() ?: throw IllegalStateException("CollectionDao not initialized")
    
    fun getCollectionWithLists(collectionId: Long): Flow<CollectionWithLists?> =
        collectionDao?.getCollectionWithLists(collectionId) ?: throw IllegalStateException("CollectionDao not initialized")
    
    fun getListCountInCollection(collectionId: Long): Flow<Int> =
        collectionDao?.getListCount(collectionId) ?: throw IllegalStateException("CollectionDao not initialized")
    
    suspend fun insertCollection(collection: VocabCollection): Long =
        collectionDao?.insertCollection(collection) ?: throw IllegalStateException("CollectionDao not initialized")
    
    suspend fun updateCollection(collection: VocabCollection) =
        collectionDao?.updateCollection(collection) ?: throw IllegalStateException("CollectionDao not initialized")
    
    suspend fun deleteCollection(collection: VocabCollection) =
        collectionDao?.deleteCollection(collection) ?: throw IllegalStateException("CollectionDao not initialized")
    
    suspend fun addListToCollection(collectionId: Long, listId: Long) =
        collectionDao?.addListToCollection(CollectionListCrossRef(collectionId, listId))
            ?: throw IllegalStateException("CollectionDao not initialized")
    
    suspend fun removeListFromCollection(collectionId: Long, listId: Long) =
        collectionDao?.removeListFromCollectionById(collectionId, listId)
            ?: throw IllegalStateException("CollectionDao not initialized")
    
    suspend fun isListInCollection(collectionId: Long, listId: Long): Boolean =
        collectionDao?.isListInCollection(collectionId, listId)
            ?: throw IllegalStateException("CollectionDao not initialized")
    
    fun getListIdsInCollection(collectionId: Long): Flow<List<Long>> =
        collectionDao?.getListIdsInCollection(collectionId)
            ?: throw IllegalStateException("CollectionDao not initialized")
}
