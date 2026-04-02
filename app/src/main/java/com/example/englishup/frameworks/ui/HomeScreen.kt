package com.example.englishup.frameworks.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.englishup.view_model.VocabularyViewModel
import com.example.englishup.ui.theme.*

@Composable
fun HomeScreen(
    onNavigateToVocab: () -> Unit = {},
    onNavigateToGrammar: () -> Unit = {},
    onNavigateToReading: () -> Unit = {},
    onNavigateToListening: () -> Unit = {},
    vocabViewModel: VocabularyViewModel = hiltViewModel()
) {
    val vocabState by vocabViewModel.state.collectAsStateWithLifecycle()
    val totalWords = vocabState.allWords.size
    val learnedWords = totalWords - vocabState.dueWords.size
    val progress = if (totalWords > 0) learnedWords.toFloat() / totalWords else 0f

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Bg2),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // ── Header ──
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(20.dp, 20.dp, 20.dp, 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text("Chào buổi sáng 👋", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                    Spacer(Modifier.height(3.dp))
                    Text("Người dùng", style = MaterialTheme.typography.headlineLarge)
                }
                Box(
                    modifier = Modifier.size(42.dp)
                        .clip(CircleShape)
                        .background(Brush.linearGradient(listOf(Green, Blue))),
                    contentAlignment = Alignment.Center
                ) {
                    Text("US", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Bg)
                }
            }
        }

        // ── Streak Bar ──
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 20.dp)
                    .background(Surface, RoundedCornerShape(16.dp))
                    .border(1.dp, Border, RoundedCornerShape(16.dp))
                    .padding(14.dp, 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(44.dp)
                        .background(AmberDim, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) { Text("🔥", fontSize = 22.sp) }
                Spacer(Modifier.width(14.dp))
                Column {
                    Text("Streak hiện tại", style = MaterialTheme.typography.labelMedium, color = TextSecondary)
                    Spacer(Modifier.height(2.dp))
                    Text("1 ngày", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Amber)
                }
                Spacer(Modifier.weight(1f))
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    repeat(1) {
                        Box(
                            modifier = Modifier.size(8.dp).clip(CircleShape)
                                .background(Amber)
                        )
                    }
                    repeat(6) {
                        Box(
                            modifier = Modifier.size(8.dp).clip(CircleShape)
                                .background(Bg3)
                        )
                    }
                }
            }
        }

        // ── Today Goal ──
        item {
            val goalProgress = if (totalWords > 0) (learnedWords.toFloat() / 10).coerceAtMost(1f) else 0f
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 20.dp)
                    .background(Surface, RoundedCornerShape(16.dp))
                    .border(1.dp, Border, RoundedCornerShape(16.dp))
                    .padding(14.dp, 14.dp)
            ) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Mục tiêu hôm nay", style = MaterialTheme.typography.titleSmall)
                    Text("${(goalProgress * 100).toInt()}%", style = MaterialTheme.typography.titleSmall, color = Green)
                }
                Spacer(Modifier.height(10.dp))
                Box(
                    Modifier.fillMaxWidth().height(6.dp)
                        .clip(RoundedCornerShape(3.dp)).background(Bg3)
                ) {
                    Box(
                        Modifier.fillMaxWidth(goalProgress).fillMaxHeight()
                            .clip(RoundedCornerShape(3.dp)).background(Green)
                    )
                }
                Spacer(Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    GoalSub("$learnedWords đã học", Green)
                    GoalSub("${vocabState.dueWords.size} cần ôn", Amber)
                    GoalSub("$totalWords tổng cộng", TextTertiary)
                }
            }
        }

        // ── Skill Cards ──
        item {
            Column(Modifier.padding(horizontal = 20.dp).padding(bottom = 20.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Kỹ năng", style = MaterialTheme.typography.titleMedium)
                }
                Spacer(Modifier.height(14.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SkillCard(Modifier.weight(1f), "📚", "Từ vựng", "$learnedWords / $totalWords từ", progress, Purple, PurpleDim) { onNavigateToVocab() }
                    SkillCard(Modifier.weight(1f), "✏️", "Ngữ pháp", "0 / 20 chủ đề", 0.0f, Blue, BlueDim) { onNavigateToGrammar() }
                }
                Spacer(Modifier.height(12.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SkillCard(Modifier.weight(1f), "📖", "Đọc hiểu", "Accuracy 0%", 0.0f, Green, GreenDim) { onNavigateToReading() }
                    SkillCard(Modifier.weight(1f), "🎧", "Nghe", "Accuracy 0%", 0.0f, Amber, AmberDim) { onNavigateToListening() }
                }
            }
        }

        // ── Recent Words ──
        if (vocabState.allWords.isNotEmpty()) {
            item {
                Column(Modifier.padding(horizontal = 20.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("Từ vựng", style = MaterialTheme.typography.titleMedium)
                        Text("Xem tất cả →", style = MaterialTheme.typography.bodySmall, color = Green, modifier = Modifier.clickable { onNavigateToVocab() })
                    }
                    Spacer(Modifier.height(14.dp))
                    vocabState.allWords.take(3).forEach { word ->
                        RecentWordItem(word.word, word.definitionVi.ifEmpty { word.definitionEn }, "Mới", Blue, BlueDim, PurpleDim)
                    }
                }
            }
        }
    }
}

@Composable
private fun GoalSub(text: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        Box(Modifier.size(6.dp).clip(CircleShape).background(color))
        Text(text, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
    }
}

@Composable
private fun SkillCard(
    modifier: Modifier, icon: String, name: String, sub: String,
    progress: Float, color: Color,
    dimColor: Color, onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .background(Surface, RoundedCornerShape(16.dp))
            .border(1.dp, Border, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Box(
            Modifier.size(40.dp).background(dimColor, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) { Text(icon, fontSize = 18.sp) }
        Spacer(Modifier.height(10.dp))
        Text(name, style = MaterialTheme.typography.titleSmall)
        Spacer(Modifier.height(4.dp))
        Text(sub, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
        Spacer(Modifier.height(10.dp))
        Box(Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)).background(Bg3)) {
            Box(Modifier.fillMaxWidth(progress).fillMaxHeight().clip(RoundedCornerShape(2.dp)).background(color))
        }
    }
}

@Composable
private fun RecentWordItem(
    word: String, def: String, badge: String,
    badgeColor: Color,
    badgeBg: Color,
    iconBg: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.size(36.dp).background(iconBg, RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center) {
            Text("📝", fontSize = 16.sp)
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(word, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(2.dp))
            Text(def, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
        }
        Box(
            Modifier.background(badgeBg, RoundedCornerShape(20.dp))
                .border(1.dp, badgeColor.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(badge, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold, color = badgeColor)
        }
    }
}
