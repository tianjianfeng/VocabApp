package com.vocabapp.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Flip
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vocabapp.data.entities.Meaning
import com.vocabapp.data.entities.WordWithMeanings
import com.vocabapp.ui.theme.*
import com.vocabapp.ui.viewmodel.CardDeckUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDeckScreen(
    uiState: CardDeckUiState,
    onBackClick: () -> Unit,
    onFlipCard: () -> Unit,
    onNextCard: () -> Unit,
    onPreviousCard: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gradientColors = listOf(
        MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
        MaterialTheme.colorScheme.background,
        MaterialTheme.colorScheme.tertiary.copy(alpha = 0.05f)
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.vocabList?.name ?: "Loading...",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
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
                    color = MaterialTheme.colorScheme.primary
                )
            } else if (uiState.words.isEmpty()) {
                EmptyDeckState(
                    modifier = Modifier.align(Alignment.Center),
                    onBackClick = onBackClick
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Progress indicator
                    ProgressSection(
                        currentIndex = uiState.currentCardIndex,
                        total = uiState.words.size
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Flashcard
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        FlashCard(
                            wordWithMeanings = uiState.words[uiState.currentCardIndex],
                            isFlipped = uiState.isFlipped,
                            onFlip = onFlipCard,
                            onSwipeLeft = onNextCard,
                            onSwipeRight = onPreviousCard
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Navigation controls
                    NavigationControls(
                        onPrevious = onPreviousCard,
                        onFlip = onFlipCard,
                        onNext = onNextCard,
                        isFlipped = uiState.isFlipped
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun ProgressSection(
    currentIndex: Int,
    total: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(
            progress = (currentIndex + 1).toFloat() / total,
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${currentIndex + 1} / $total",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun FlashCard(
    wordWithMeanings: WordWithMeanings,
    isFlipped: Boolean,
    onFlip: () -> Unit,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        label = "cardRotation"
    )

    val dragThreshold = 100f

    Card(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .aspectRatio(0.7f)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(24.dp),
                ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            )
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onFlip)
            .pointerInput(Unit) {
                var dragOffset = 0f
                detectHorizontalDragGestures(
                    onDragStart = { dragOffset = 0f },
                    onDragEnd = {
                        when {
                            dragOffset < -dragThreshold -> onSwipeLeft()
                            dragOffset > dragThreshold -> onSwipeRight()
                        }
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        dragOffset += dragAmount
                    }
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = if (rotation <= 90f) CardFrontLight else CardBackLight
        ),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    // Counter-rotate content when card is flipped
                    rotationY = if (rotation > 90f) 180f else 0f
                }
        ) {
            if (rotation <= 90f) {
                // Front of card - Word and Phonetic
                CardFront(
                    word = wordWithMeanings.word.word,
                    phonetic = wordWithMeanings.word.phonetic
                )
            } else {
                // Back of card - Meanings
                CardBack(meanings = wordWithMeanings.meanings)
            }
        }
    }
}

@Composable
private fun CardFront(
    word: String,
    phonetic: String,
    modifier: Modifier = Modifier
) {
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            CardFrontLight,
            CardFrontLight.copy(alpha = 0.95f)
        )
    )
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundGradient),
        contentAlignment = Alignment.Center
    ) {
        // Decorative elements
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = (-80).dp, y = (-120).dp)
                .background(
                    color = AccentMint.copy(alpha = 0.1f),
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .size(150.dp)
                .offset(x = 100.dp, y = 150.dp)
                .background(
                    color = AccentLavender.copy(alpha = 0.1f),
                    shape = CircleShape
                )
        )
        
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = word,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = TextPrimaryLight,
                textAlign = TextAlign.Center
            )
            
            if (phonetic.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = phonetic,
                    style = MaterialTheme.typography.headlineSmall,
                    fontStyle = FontStyle.Italic,
                    color = TextSecondaryLight,
                    textAlign = TextAlign.Center
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Flip,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Tap to flip",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
private fun CardBack(
    meanings: List<Meaning>,
    modifier: Modifier = Modifier
) {
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            CardBackLight,
            CardBackLight.copy(alpha = 0.95f)
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        // Decorative elements
        Box(
            modifier = Modifier
                .size(180.dp)
                .offset(x = 100.dp, y = (-100).dp)
                .background(
                    color = AccentCoral.copy(alpha = 0.1f),
                    shape = CircleShape
                )
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(28.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                Text(
                    text = "Definitions",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(20.dp))
            }

            itemsIndexed(meanings) { index, meaning ->
                MeaningItem(
                    meaning = meaning
                )
                if (index < meanings.size - 1) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun MeaningItem(
    meaning: Meaning,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        // Part of speech badge
        Surface(
            color = getPartOfSpeechColor(meaning.partOfSpeech),
            shape = RoundedCornerShape(6.dp)
        ) {
            Text(
                text = meaning.partOfSpeech,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Text(
            text = meaning.definition,
            style = MaterialTheme.typography.bodyLarge,
            color = TextPrimaryLight,
            lineHeight = 24.sp
        )
    }
}

private fun getPartOfSpeechColor(pos: String): Color {
    return when (pos.lowercase()) {
        "noun", "n." -> AccentLavender
        "verb", "v." -> AccentCoral
        "adjective", "adj." -> AccentMint
        "adverb", "adv." -> AccentSky
        else -> Teal500
    }
}

@Composable
private fun NavigationControls(
    onPrevious: () -> Unit,
    onFlip: () -> Unit,
    onNext: () -> Unit,
    isFlipped: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Previous button
        FilledTonalIconButton(
            onClick = onPrevious,
            modifier = Modifier.size(56.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = "Previous",
                modifier = Modifier.size(32.dp)
            )
        }
        
        // Flip button
        FilledIconButton(
            onClick = onFlip,
            modifier = Modifier.size(72.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = if (isFlipped) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                contentDescription = if (isFlipped) "Show Word" else "Show Meaning",
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        
        // Next button
        FilledTonalIconButton(
            onClick = onNext,
            modifier = Modifier.size(56.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Next",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
private fun EmptyDeckState(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Style,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No cards in this deck",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Add some words to start learning",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onBackClick) {
            Icon(Icons.Default.ArrowBack, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Go Back")
        }
    }
}
