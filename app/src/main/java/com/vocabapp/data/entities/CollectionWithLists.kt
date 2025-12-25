package com.vocabapp.data.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
 * Data class that combines a Collection with all its VocabLists
 */
data class CollectionWithLists(
    @Embedded val collection: Collection,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = CollectionListCrossRef::class,
            parentColumn = "collectionId",
            entityColumn = "listId"
        )
    )
    val lists: List<VocabList>
)

