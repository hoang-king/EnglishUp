package com.example.englishup.frameworks.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.englishup.core.utils.AudioPlayer
import com.example.englishup.entities.ReviewGrade
import com.example.englishup.entities.Vocabulary
import com.example.englishup.entities.WordStatus
import com.example.englishup.ui.theme.*
import com.example.englishup.view_model.VocabularyViewModel
import kotlinx.coroutines.launch

@Composable
fun VocabularyScreen(
    onBack: () -> Unit = {},
    viewModel: VocabularyViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Tất cả", "Cần ôn", "Đã nhớ")
    
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val filteredWords = remember(selectedTab, uiState.allWords) {
        when (selectedTab) {
            0 -> uiState.allWords
            1 -> uiState.dueWords
            2 -> uiState.allWords.filter { it.status == WordStatus.KNOWN }
            else -> uiState.allWords
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize().background(Bg2),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // ── Header ──
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp, 16.dp, 16.dp, 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier.size(36.dp).background(Surface, RoundedCornerShape(10.dp))
                        .border(1.dp, Border, RoundedCornerShape(10.dp))
                        .clickable { onBack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = TextSecondary, modifier = Modifier.size(18.dp))
                }
                Spacer(Modifier.width(12.dp))
                Text("Flashcard TOEIC", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.weight(1f))
                VocabChipBadge("SRS", Purple, PurpleDim)
            }
        }

        // ── Tabs ──
        item {
            Row(Modifier.padding(horizontal = 20.dp).padding(bottom = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                tabs.forEachIndexed { i, tab ->
                    val isSelected = i == selectedTab
                    Box(
                        Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) GreenDim else Color.Transparent)
                            .border(1.dp, if (isSelected) Green.copy(0.3f) else Border, RoundedCornerShape(20.dp))
                            .clickable { selectedTab = i }
                            .padding(horizontal = 16.dp, vertical = 7.dp)
                    ) {
                        Text(tab, style = MaterialTheme.typography.labelLarge, color = if (isSelected) Green else TextSecondary)
                    }
                }
            }
        }

        // ── Flashcard ──
        item {
            if (uiState.currentCard != null) {
                FlashCardView(
                    vocabulary = uiState.currentCard!!,
                    isFlipped = uiState.isFlipped,
                    onFlip = { viewModel.flipCard() },
                    currentIndex = uiState.cardIndex + 1,
                    total = uiState.dueWords.size
                )
            } else if (selectedTab == 1) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(20.dp).height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Chúc mừng! Bạn đã hoàn thành các từ cần ôn hôm nay.", 
                        textAlign = TextAlign.Center, color = TextSecondary)
                }
            }
        }

        // ── SRS Action Buttons ──
        if (uiState.currentCard != null && uiState.isFlipped) {
            item {
                Row(
                    Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SrsButton(Modifier.weight(1f), "✕", "Again", Red, RedDim) {
                        viewModel.reviewCard(ReviewGrade.AGAIN)
                    }
                    SrsButton(Modifier.weight(1f), "~", "Hard", Amber, AmberDim) {
                        viewModel.reviewCard(ReviewGrade.HARD)
                    }
                    SrsButton(Modifier.weight(1f), "✓", "Good", Blue, BlueDim) {
                        viewModel.reviewCard(ReviewGrade.GOOD)
                    }
                    SrsButton(Modifier.weight(1f), "★", "Easy", Green, GreenDim) {
                        viewModel.reviewCard(ReviewGrade.EASY)
                    }
                }
            }
        }

        // ── Word List ──
        item {
            Column(Modifier.padding(horizontal = 20.dp)) {
                Text("Danh sách từ", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(14.dp))
            }
        }

        items(filteredWords) { word ->
            WordListItem(
                word = word.word,
                def = word.definitionVi,
                badge = when(word.status) {
                    WordStatus.NEW -> "Mới"
                    WordStatus.LEARNING -> "Đang học"
                    WordStatus.REVIEW -> "Ôn lại"
                    WordStatus.KNOWN -> "Đã nhớ"
                    else -> "Mới"
                },
                badgeColor = when(word.status) {
                    WordStatus.NEW -> Blue
                    WordStatus.LEARNING -> Amber
                    WordStatus.REVIEW -> Purple
                    WordStatus.KNOWN -> Green
                    else -> Blue
                },
                badgeBg = when(word.status) {
                    WordStatus.NEW -> BlueDim
                    WordStatus.LEARNING -> AmberDim
                    WordStatus.REVIEW -> PurpleDim
                    WordStatus.KNOWN -> GreenDim
                    else -> BlueDim
                },
                reviewTime = "Ôn lại: ${word.nextReviewDate}",
                onClick = {
                    viewModel.selectWord(word)
                    scope.launch {
                        listState.animateScrollToItem(0)
                    }
                }
            )
        }
    }
}

@Composable
fun FlashCardView(
    vocabulary: Vocabulary,
    isFlipped: Boolean,
    onFlip: () -> Unit,
    currentIndex: Int,
    total: Int
) {
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 400),
        label = "cardRotation"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 16.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .background(Surface, RoundedCornerShape(24.dp))
            .border(1.dp, Border, RoundedCornerShape(24.dp))
            .clickable { onFlip() }
            .padding(32.dp, 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Content that doesn't flip (or counter-rotates)
        Box(modifier = Modifier.fillMaxWidth().graphicsLayer { 
            if (rotation > 90f) rotationY = 180f 
        }) {
            Text("$currentIndex / $total", style = MaterialTheme.typography.labelMedium, color = TextTertiary,
                modifier = Modifier.align(Alignment.TopEnd))
            
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                if (rotation <= 90f) {
                    // Front side
                    Text(vocabulary.category.uppercase(), style = MaterialTheme.typography.labelSmall, color = TextTertiary)
                    Spacer(Modifier.height(12.dp))
                    Text(vocabulary.word, fontSize = 34.sp, fontWeight = FontWeight.Bold, letterSpacing = (-0.5).sp)
                    Spacer(Modifier.height(8.dp))
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(vocabulary.phonetic, style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
                        if (vocabulary.audioUrl.isNotEmpty()) {
                            Spacer(Modifier.width(8.dp))
                            IconButton(
                                onClick = { AudioPlayer.play(vocabulary.audioUrl) },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Text("🔊", fontSize = 16.sp)
                            }
                        }
                    }
                    
                    Spacer(Modifier.height(12.dp))
                    VocabChipBadge(vocabulary.partOfSpeech, Blue, BlueDim)
                } else {
                    // Back side
                    val definition = if (vocabulary.definitionVi.isNotEmpty()) vocabulary.definitionVi else vocabulary.definitionEn
                    Text("ĐỊNH NGHĨA", style = MaterialTheme.typography.labelSmall, color = Green)
                    Spacer(Modifier.height(14.dp))
                    Text(definition,
                        style = MaterialTheme.typography.bodyLarge, color = TextPrimary, textAlign = TextAlign.Center)
                    
                    if (vocabulary.example.isNotEmpty()) {
                        Spacer(Modifier.height(12.dp))
                        Text("\"${vocabulary.example}\"",
                            style = MaterialTheme.typography.bodySmall, color = TextSecondary,
                            fontStyle = FontStyle.Italic, textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}

@Composable
fun VocabChipBadge(text: String, color: Color, bgColor: Color) {
    Box(
        Modifier.background(bgColor, RoundedCornerShape(20.dp))
            .border(1.dp, color.copy(0.3f), RoundedCornerShape(20.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(text, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold, color = color)
    }
}

@Composable
private fun SrsButton(
    modifier: Modifier, icon: String, label: String,
    color: Color, bg: Color,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .background(bg, RoundedCornerShape(16.dp))
            .border(1.dp, color.copy(0.3f), RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(icon, fontSize = 16.sp, color = color)
        Spacer(Modifier.height(4.dp))
        Text(label, style = MaterialTheme.typography.labelLarge, color = color)
    }
}

@Composable
private fun WordListItem(word: String, def: String, badge: String, badgeColor: Color, badgeBg: Color, reviewTime: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 4.dp)
            .background(Surface, RoundedCornerShape(10.dp))
            .border(1.dp, Border, RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.size(36.dp).background(GreenDim, RoundedCornerShape(9.dp)), contentAlignment = Alignment.Center) {
            Text("📝", fontSize = 14.sp)
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(word, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(2.dp))
            Text(def, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
        }
        Column(horizontalAlignment = Alignment.End) {
            VocabChipBadge(badge, badgeColor, badgeBg)
            Spacer(Modifier.height(4.dp))
            Text(reviewTime, fontSize = 9.sp, color = TextTertiary)
        }
    }
}
