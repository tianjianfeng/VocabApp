package com.vocabapp.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

/**
 * Cross-reference table for many-to-many relationship between Collections and VocabLists
 */
@Entity(
    tableName = "collection_list_cross_ref",
    primaryKeys = ["collectionId", "listId"],
    foreignKeys = [
        ForeignKey(
            entity = Collection::class,
            parentColumns = ["id"],
            childColumns = ["collectionId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = VocabList::class,
            parentColumns = ["id"],
            childColumns = ["listId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("collectionId"),
        Index("listId")
    ]
)
data class CollectionListCrossRef(
    val collectionId: Long,
    val listId: Long,
    val addedAt: Long = System.currentTimeMillis()
)

