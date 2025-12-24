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
            // Get card index for THIS specific list
            val lastIndex = userPreferences.getCardIndexForList(listId)
            
            // Mark this list as last visited
            userPreferences.saveLastVisitedListId(listId)
            
            repository.getListWithWords(listId).collect { listWithWords ->
                if (listWithWords != null) {
                    val validIndex = lastIndex.coerceIn(0, maxOf(0, listWithWords.words.size - 1))
                    _uiState.value = CardDeckUiState(
                        vocabList = listWithWords.vocabList,
                        words = listWithWords.words,
                        currentCardIndex = validIndex,
                        isFlipped = false,
                        isLoading = false
                    )
                } else {
                    _uiState.value = CardDeckUiState(isLoading = false)
                }
            }
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
            // Save card index for THIS specific list
            userPreferences.saveCardIndexForList(listId, index)
        }
    }
}
