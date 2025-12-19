package com.vocabapp.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferences(private val context: Context) {
    
    companion object {
        private val LAST_VISITED_LIST_ID = longPreferencesKey("last_visited_list_id")
        private val LAST_CARD_INDEX = intPreferencesKey("last_card_index")
    }
    
    val lastVisitedListId: Flow<Long?> = context.dataStore.data.map { preferences ->
        preferences[LAST_VISITED_LIST_ID]
    }
    
    val lastCardIndex: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[LAST_CARD_INDEX] ?: 0
    }
    
    suspend fun saveLastVisitedList(listId: Long, cardIndex: Int = 0) {
        context.dataStore.edit { preferences ->
            preferences[LAST_VISITED_LIST_ID] = listId
            preferences[LAST_CARD_INDEX] = cardIndex
        }
    }
    
    suspend fun saveLastCardIndex(cardIndex: Int) {
        context.dataStore.edit { preferences ->
            preferences[LAST_CARD_INDEX] = cardIndex
        }
    }
    
    suspend fun clearLastVisited() {
        context.dataStore.edit { preferences ->
            preferences.remove(LAST_VISITED_LIST_ID)
            preferences.remove(LAST_CARD_INDEX)
        }
    }
}

