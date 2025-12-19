package com.vocabapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.vocabapp.data.VocabDatabase
import com.vocabapp.data.entities.VocabList
import com.vocabapp.data.preferences.UserPreferences
import com.vocabapp.data.repository.VocabRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class VocabListUiState(
    val lists: List<VocabList> = emptyList(),
    val wordCounts: Map<Long, Int> = emptyMap(),
    val lastVisitedListId: Long? = null,
    val isLoading: Boolean = true
)

class VocabListViewModel(application: Application) : AndroidViewModel(application) {

    private val database = VocabDatabase.getDatabase(application)
    private val repository = VocabRepository(
        database.vocabListDao(),
        database.wordDao(),
        database.meaningDao()
    )
    private val userPreferences = UserPreferences(application)

    private val _uiState = MutableStateFlow(VocabListUiState())
    val uiState: StateFlow<VocabListUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            repository.getAllLists().flatMapLatest { lists ->
                if (lists.isEmpty()) {
                    flowOf(VocabListUiState(isLoading = false, lists = emptyList(), wordCounts = emptyMap()))
                } else {
                    val wordCountFlows = lists.map { repository.getWordCount(it.id) }
                    combine(wordCountFlows) { wordCounts ->
                        val wordCountMap = lists.mapIndexed { index, list -> list.id to wordCounts[index] }.toMap()
                        VocabListUiState(
                            lists = lists,
                            wordCounts = wordCountMap,
                            isLoading = false
                        )
                    }
                }
            }.combine(userPreferences.lastVisitedListId) { uiState, lastVisitedId ->
                uiState.copy(lastVisitedListId = lastVisitedId)
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun addList(name: String, description: String = "") {
        viewModelScope.launch {
            repository.insertList(VocabList(name = name, description = description))
        }
    }

    fun deleteList(vocabList: VocabList) {
        viewModelScope.launch {
            repository.deleteList(vocabList)
        }
    }

    fun setLastVisitedList(listId: Long) {
        viewModelScope.launch {
            userPreferences.saveLastVisitedList(listId)
        }
    }
}
