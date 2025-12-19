package com.vocabapp.data.entities

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Data class that combines a VocabList with all its Words (including meanings)
 */
data class VocabListWithWords(
    @Embedded val vocabList: VocabList,
    @Relation(
        entity = Word::class,
        parentColumn = "id",
        entityColumn = "listId"
    )
    val words: List<WordWithMeanings>
)

