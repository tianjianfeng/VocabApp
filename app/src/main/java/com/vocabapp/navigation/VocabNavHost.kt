package com.vocabapp.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.vocabapp.ui.screens.CardDeckScreen
import com.vocabapp.ui.screens.CollectionDetailScreen
import com.vocabapp.ui.screens.CollectionsScreen
import com.vocabapp.ui.screens.VocabListScreen
import com.vocabapp.ui.viewmodel.CardDeckViewModel
import com.vocabapp.ui.viewmodel.CardDeckViewModelFactory
import com.vocabapp.ui.viewmodel.CollectionViewModel
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
                },
                onCollectionsClick = {
                    navController.navigate(NavRoutes.Collections.route)
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
        
        composable(NavRoutes.Collections.route) {
            val viewModel: CollectionViewModel = viewModel()
            val uiState by viewModel.listUiState.collectAsState()
            
            CollectionsScreen(
                uiState = uiState,
                onCollectionClick = { collectionId ->
                    navController.navigate(NavRoutes.CollectionDetail.createRoute(collectionId))
                },
                onCreateCollection = { name, description ->
                    viewModel.createCollection(name, description)
                },
                onDeleteCollection = { collection ->
                    viewModel.deleteCollection(collection)
                },
                onBackClick = { navController.popBackStack() }
            )
        }
        
        composable(
            route = NavRoutes.CollectionDetail.route,
            arguments = listOf(
                navArgument("collectionId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val collectionId = backStackEntry.arguments?.getLong("collectionId") ?: 0L
            val viewModel: CollectionViewModel = viewModel()
            
            // Load collection detail when entering this screen
            remember(collectionId) {
                viewModel.loadCollectionDetail(collectionId)
                true
            }
            
            val uiState by viewModel.detailUiState.collectAsState()
            
            CollectionDetailScreen(
                uiState = uiState,
                onBackClick = { navController.popBackStack() },
                onListClick = { listId ->
                    navController.navigate(NavRoutes.CardDeck.createRoute(listId))
                },
                onToggleList = { listId, isInCollection ->
                    viewModel.toggleListInCollection(collectionId, listId, isInCollection)
                }
            )
        }
    }
}
