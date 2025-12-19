package com.vocabapp.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.vocabapp.ui.screens.CardDeckScreen
import com.vocabapp.ui.screens.VocabListScreen
import com.vocabapp.ui.viewmodel.CardDeckViewModel
import com.vocabapp.ui.viewmodel.CardDeckViewModelFactory
import com.vocabapp.ui.viewmodel.VocabListViewModel

@Composable
fun VocabNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val application = context.applicationContext as Application
    
    NavHost(
        navController = navController,
        startDestination = NavRoutes.VocabLists.route,
        modifier = modifier
    ) {
        composable(NavRoutes.VocabLists.route) {
            val viewModel: VocabListViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsState()
            
            VocabListScreen(
                uiState = uiState,
                onListClick = { listId ->
                    viewModel.setLastVisitedList(listId)
                    navController.navigate(NavRoutes.CardDeck.createRoute(listId))
                },
                onAddList = { name, description ->
                    viewModel.addList(name, description)
                },
                onDeleteList = { vocabList ->
                    viewModel.deleteList(vocabList)
                }
            )
        }
        
        composable(
            route = NavRoutes.CardDeck.route,
            arguments = listOf(
                navArgument("listId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val listId = backStackEntry.arguments?.getLong("listId") ?: 0L
            val viewModel: CardDeckViewModel = viewModel(
                factory = CardDeckViewModelFactory(application, listId)
            )
            val uiState by viewModel.uiState.collectAsState()
            
            CardDeckScreen(
                uiState = uiState,
                onBackClick = { navController.popBackStack() },
                onFlipCard = { viewModel.flipCard() },
                onNextCard = { viewModel.nextCard() },
                onPreviousCard = { viewModel.previousCard() }
            )
        }
    }
}

