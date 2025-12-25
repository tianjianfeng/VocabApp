package com.vocabapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a user-created collection that can contain multiple vocabulary lists
 */
@Entity(tableName = "collections")
data class Collection(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

