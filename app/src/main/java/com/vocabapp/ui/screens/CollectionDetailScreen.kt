package com.vocabapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.PlaylistAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vocabapp.data.entities.VocabList
import com.vocabapp.ui.theme.*
import com.vocabapp.ui.viewmodel.CollectionDetailUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionDetailScreen(
    uiState: CollectionDetailUiState,
    onBackClick: () -> Unit,
    onListClick: (Long) -> Unit,
    onToggleList: (Long, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddListsSheet by remember { mutableStateOf(false) }
    
    val gradientColors = listOf(
        MaterialTheme.colorScheme.tertiary.copy(alpha = 0.08f),
        MaterialTheme.colorScheme.background
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.collection?.name ?: "Collection",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showAddListsSheet = true }) {
                        Icon(
                            Icons.Outlined.PlaylistAdd,
                            contentDescription = "Add Lists",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showAddListsSheet = true },
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Lists")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Lists")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(gradientColors)
                )
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.tertiary
                )
            } else if (uiState.listsInCollection.isEmpty()) {
                EmptyCollectionDetailState(
                    modifier = Modifier.align(Alignment.Center),
                    onAddClick = { showAddListsSheet = true }
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        if (uiState.collection?.description?.isNotEmpty() == true) {
                            Text(
                                text = uiState.collection.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                        Text(
                            text = "${uiState.listsInCollection.size} ${if (uiState.listsInCollection.size == 1) "list" else "lists"} in this collection",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    items(uiState.listsInCollection, key = { it.id }) { vocabList ->
                        CollectionListItem(
                            vocabList = vocabList,
                            onClick = { onListClick(vocabList.id) },
                            onRemove = { onToggleList(vocabList.id, true) }
                        )
                    }
                }
            }
        }
    }
    
    if (showAddListsSheet) {
        AddListsBottomSheet(
            allLists = uiState.allLists,
            listIdsInCollection = uiState.listIdsInCollection,
            onDismiss = { showAddListsSheet = false },
            onToggleList = onToggleList
        )
    }
}

@Composable
private fun CollectionListItem(
    vocabList: VocabList,
    onClick: () -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.MenuBook,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = vocabList.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (vocabList.description.isNotEmpty()) {
                    Text(
                        text = vocabList.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            
            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Default.RemoveCircleOutline,
                    contentDescription = "Remove from collection",
                    tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
private fun EmptyCollectionDetailState(
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.FolderOpen,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Collection is empty",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Add vocabulary lists to this collection",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onAddClick) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add Lists")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddListsBottomSheet(
    allLists: List<VocabList>,
    listIdsInCollection: Set<Long>,
    onDismiss: () -> Unit,
    onToggleList: (Long, Boolean) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = "Add Lists to Collection",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            if (allLists.isEmpty()) {
                Text(
                    text = "No vocabulary lists available",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 24.dp)
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.heightIn(max = 400.dp)
                ) {
                    items(allLists, key = { it.id }) { vocabList ->
                        val isInCollection = listIdsInCollection.contains(vocabList.id)
                        
                        ListItem(
                            headlineContent = {
                                Text(
                                    text = vocabList.name,
                                    fontWeight = FontWeight.Medium
                                )
                            },
                            supportingContent = if (vocabList.description.isNotEmpty()) {
                                { Text(vocabList.description) }
                            } else null,
                            leadingContent = {
                                Checkbox(
                                    checked = isInCollection,
                                    onCheckedChange = { onToggleList(vocabList.id, isInCollection) }
                                )
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { onToggleList(vocabList.id, isInCollection) }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Done")
            }
        }
    }
}

