package com.vocabapp.data.entities

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Data class that combines a Word with all its Meanings
 */
data class WordWithMeanings(
    @Embedded val word: Word,
    @Relation(
        parentColumn = "id",
        entityColumn = "wordId"
    )
    val meanings: List<Meaning>
)

