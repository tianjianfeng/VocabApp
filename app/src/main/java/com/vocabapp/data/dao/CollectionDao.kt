package com.vocabapp.data.dao

import androidx.room.*
import com.vocabapp.data.entities.Collection
import com.vocabapp.data.entities.CollectionListCrossRef
import com.vocabapp.data.entities.CollectionWithLists
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionDao {
    
    @Query("SELECT * FROM collections ORDER BY createdAt DESC")
    fun getAllCollections(): Flow<List<Collection>>
    
    @Query("SELECT * FROM collections WHERE id = :collectionId")
    suspend fun getCollectionById(collectionId: Long): Collection?
    
    @Transaction
    @Query("SELECT * FROM collections WHERE id = :collectionId")
    fun getCollectionWithLists(collectionId: Long): Flow<CollectionWithLists?>
    
    @Transaction
    @Query("SELECT * FROM collections ORDER BY createdAt DESC")
    fun getAllCollectionsWithLists(): Flow<List<CollectionWithLists>>
    
    @Query("SELECT COUNT(*) FROM collection_list_cross_ref WHERE collectionId = :collectionId")
    fun getListCount(collectionId: Long): Flow<Int>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollection(collection: Collection): Long
    
    @Update
    suspend fun updateCollection(collection: Collection)
    
    @Delete
    suspend fun deleteCollection(collection: Collection)
    
    @Query("DELETE FROM collections WHERE id = :collectionId")
    suspend fun deleteCollectionById(collectionId: Long)
    
    // Cross-reference operations
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addListToCollection(crossRef: CollectionListCrossRef)
    
    @Delete
    suspend fun removeListFromCollection(crossRef: CollectionListCrossRef)
    
    @Query("DELETE FROM collection_list_cross_ref WHERE collectionId = :collectionId AND listId = :listId")
    suspend fun removeListFromCollectionById(collectionId: Long, listId: Long)
    
    @Query("SELECT EXISTS(SELECT 1 FROM collection_list_cross_ref WHERE collectionId = :collectionId AND listId = :listId)")
    suspend fun isListInCollection(collectionId: Long, listId: Long): Boolean
    
    @Query("SELECT listId FROM collection_list_cross_ref WHERE collectionId = :collectionId")
    fun getListIdsInCollection(collectionId: Long): Flow<List<Long>>
}

