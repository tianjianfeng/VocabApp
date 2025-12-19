package com.vocabapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CardDeckViewModelFactory(
    private val application: Application,
    private val listId: Long
) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CardDeckViewModel::class.java)) {
            return CardDeckViewModel(application, listId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

