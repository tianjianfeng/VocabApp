package com.vocabapp.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Represents a vocabulary word with its phonetic symbols
 */
@Entity(
    tableName = "words",
    foreignKeys = [
        ForeignKey(
            entity = VocabList::class,
            parentColumns = ["id"],
            childColumns = ["listId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("listId")]
)
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val listId: Long,
    val word: String,
    val phonetic: String = ""
)

