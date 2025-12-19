package com.vocabapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a vocabulary list (e.g., TOEFL, GRE, The Old Man and the Sea)
 */
@Entity(tableName = "vocab_lists")
data class VocabList(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

