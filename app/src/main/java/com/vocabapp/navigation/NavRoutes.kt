package com.vocabapp.navigation

sealed class NavRoutes(val route: String) {
    object VocabLists : NavRoutes("vocab_lists")
    object CardDeck : NavRoutes("card_deck/{listId}") {
        fun createRoute(listId: Long) = "card_deck/$listId"
    }
}

