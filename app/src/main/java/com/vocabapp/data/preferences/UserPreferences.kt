package com.vocabapp.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferences(private val context: Context) {
    
    companion object {
        private val LAST_VISITED_LIST_ID = longPreferencesKey("last_visited_list_id")
        
        // Create a unique key for each list's card index
        private fun cardIndexKey(listId: Long) = intPreferencesKey("last_card_index_$listId")
    }
    
    val lastVisitedListId: Flow<Long?> = context.dataStore.data.map { preferences ->
        preferences[LAST_VISITED_LIST_ID]
    }
    
    /**
     * Get the last card index for a specific list
     */
    suspend fun getCardIndexForList(listId: Long): Int {
        val prefs = context.dataStore.data.first()
        return prefs[cardIndexKey(listId)] ?: 0
    }
    
    /**
     * Save the last visited list ID
     */
    suspend fun saveLastVisitedListId(listId: Long) {
        context.dataStore.edit { preferences ->
            preferences[LAST_VISITED_LIST_ID] = listId
        }
    }
    
    /**
     * Save the card index for a specific list
     */
    suspend fun saveCardIndexForList(listId: Long, cardIndex: Int) {
        context.dataStore.edit { preferences ->
            preferences[LAST_VISITED_LIST_ID] = listId
            preferences[cardIndexKey(listId)] = cardIndex
        }
    }
    
    /**
     * Clear the card index for a specific list (e.g., when list is deleted)
     */
    suspend fun clearCardIndexForList(listId: Long) {
        context.dataStore.edit { preferences ->
            preferences.remove(cardIndexKey(listId))
        }
    }
    
    suspend fun clearLastVisited() {
        context.dataStore.edit { preferences ->
            preferences.remove(LAST_VISITED_LIST_ID)
        }
    }
}
