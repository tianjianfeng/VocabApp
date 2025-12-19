package com.vocabapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.vocabapp.data.VocabDatabase
import com.vocabapp.data.entities.VocabList
import com.vocabapp.data.entities.WordWithMeanings
import com.vocabapp.data.preferences.UserPreferences
import com.vocabapp.data.repository.VocabRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class CardDeckUiState(
    val vocabList: VocabList? = null,
    val words: List<WordWithMeanings> = emptyList(),
    val currentCardIndex: Int = 0,
    val isFlipped: Boolean = false,
    val isLoading: Boolean = true
)

class CardDeckViewModel(
    application: Application,
    private val listId: Long
) : AndroidViewModel(application) {
    
    private val database = VocabDatabase.getDatabase(application)
    private val repository = VocabRepository(
        database.vocabListDao(),
        database.wordDao(),
        database.meaningDao()
    )
    private val userPreferences = UserPreferences(application)
    
    private val _uiState = MutableStateFlow(CardDeckUiState())
    val uiState: StateFlow<CardDeckUiState> = _uiState.asStateFlow()
    
    init {
        loadData()
    }
    
    private fun loadData() {
        viewModelScope.launch {
            // Load last card index
            val lastIndex = userPreferences.lastCardIndex.first()
            
            combine(
                repository.getListWithWords(listId),
                flowOf(lastIndex)
            ) { listWithWords, savedIndex ->
                if (listWithWords != null) {
                    val validIndex = savedIndex.coerceIn(0, maxOf(0, listWithWords.words.size - 1))
                    CardDeckUiState(
                        vocabList = listWithWords.vocabList,
                        words = listWithWords.words,
                        currentCardIndex = validIndex,
                        isFlipped = false,
                        isLoading = false
                    )
                } else {
                    CardDeckUiState(isLoading = false)
                }
            }.collect { state ->
                _uiState.value = state
            }
        }
        
        // Save last visited list
        viewModelScope.launch {
            userPreferences.saveLastVisitedList(listId)
        }
    }
    
    fun flipCard() {
        _uiState.update { it.copy(isFlipped = !it.isFlipped) }
    }
    
    fun nextCard() {
        _uiState.update { current ->
            val newIndex = if (current.currentCardIndex < current.words.size - 1) {
                current.currentCardIndex + 1
            } else {
                0 // Loop back to start
            }
            saveCardIndex(newIndex)
            current.copy(currentCardIndex = newIndex, isFlipped = false)
        }
    }
    
    fun previousCard() {
        _uiState.update { current ->
            val newIndex = if (current.currentCardIndex > 0) {
                current.currentCardIndex - 1
            } else {
                current.words.size - 1 // Loop to end
            }
            saveCardIndex(newIndex)
            current.copy(currentCardIndex = newIndex, isFlipped = false)
        }
    }
    
    fun goToCard(index: Int) {
        _uiState.update { current ->
            val validIndex = index.coerceIn(0, maxOf(0, current.words.size - 1))
            saveCardIndex(validIndex)
            current.copy(currentCardIndex = validIndex, isFlipped = false)
        }
    }
    
    private fun saveCardIndex(index: Int) {
        viewModelScope.launch {
            userPreferences.saveLastCardIndex(index)
        }
    }
}

