package com.vocabapp.navigation

sealed class NavRoutes(val route: String) {
    object VocabLists : NavRoutes("vocab_lists")
    object CardDeck : NavRoutes("card_deck/{listId}") {
        fun createRoute(listId: Long) = "card_deck/$listId"
    }
    object Collections : NavRoutes("collections")
    object CollectionDetail : NavRoutes("collection_detail/{collectionId}") {
        fun createRoute(collectionId: Long) = "collection_detail/$collectionId"
    }
}
