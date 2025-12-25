package com.vocabapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.vocabapp.data.VocabDatabase
import com.vocabapp.data.entities.Collection
import com.vocabapp.data.entities.CollectionWithLists
import com.vocabapp.data.entities.VocabList
import com.vocabapp.data.repository.VocabRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class CollectionListUiState(
    val collections: List<CollectionWithLists> = emptyList(),
    val isLoading: Boolean = true
)

data class CollectionDetailUiState(
    val collection: Collection? = null,
    val listsInCollection: List<VocabList> = emptyList(),
    val allLists: List<VocabList> = emptyList(),
    val listIdsInCollection: Set<Long> = emptySet(),
    val isLoading: Boolean = true
)

class CollectionViewModel(application: Application) : AndroidViewModel(application) {
    
    private val database = VocabDatabase.getDatabase(application)
    private val repository = VocabRepository(
        database.vocabListDao(),
        database.wordDao(),
        database.meaningDao(),
        database.collectionDao()
    )
    
    private val _listUiState = MutableStateFlow(CollectionListUiState())
    val listUiState: StateFlow<CollectionListUiState> = _listUiState.asStateFlow()
    
    private val _detailUiState = MutableStateFlow(CollectionDetailUiState())
    val detailUiState: StateFlow<CollectionDetailUiState> = _detailUiState.asStateFlow()
    
    init {
        loadCollections()
    }
    
    private fun loadCollections() {
        viewModelScope.launch {
            repository.getAllCollectionsWithLists().collect { collections ->
                _listUiState.value = CollectionListUiState(
                    collections = collections,
                    isLoading = false
                )
            }
        }
    }
    
    fun loadCollectionDetail(collectionId: Long) {
        viewModelScope.launch {
            combine(
                repository.getCollectionWithLists(collectionId),
                repository.getAllLists(),
                repository.getListIdsInCollection(collectionId)
            ) { collectionWithLists, allLists, listIds ->
                CollectionDetailUiState(
                    collection = collectionWithLists?.collection,
                    listsInCollection = collectionWithLists?.lists ?: emptyList(),
                    allLists = allLists,
                    listIdsInCollection = listIds.toSet(),
                    isLoading = false
                )
            }.collect { state ->
                _detailUiState.value = state
            }
        }
    }
    
    fun createCollection(name: String, description: String = "") {
        viewModelScope.launch {
            repository.insertCollection(Collection(name = name, description = description))
        }
    }
    
    fun deleteCollection(collection: Collection) {
        viewModelScope.launch {
            repository.deleteCollection(collection)
        }
    }
    
    fun addListToCollection(collectionId: Long, listId: Long) {
        viewModelScope.launch {
            repository.addListToCollection(collectionId, listId)
        }
    }
    
    fun removeListFromCollection(collectionId: Long, listId: Long) {
        viewModelScope.launch {
            repository.removeListFromCollection(collectionId, listId)
        }
    }
    
    fun toggleListInCollection(collectionId: Long, listId: Long, isCurrentlyInCollection: Boolean) {
        viewModelScope.launch {
            if (isCurrentlyInCollection) {
                repository.removeListFromCollection(collectionId, listId)
            } else {
                repository.addListToCollection(collectionId, listId)
            }
        }
    }
}

