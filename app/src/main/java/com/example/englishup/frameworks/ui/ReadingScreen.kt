package com.example.englishup.frameworks.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.englishup.entities.Reading
import com.example.englishup.ui.theme.*
import com.example.englishup.view_model.ReadingViewModel

@Composable
fun ReadingScreen(
    onBack: () -> Unit = {},
    viewModel: ReadingViewModel = hiltViewModel()
) {
    val lessons by viewModel.state.collectAsStateWithLifecycle()
    var selectedLesson by remember { mutableStateOf<Reading?>(null) }

    if (selectedLesson == null) {
        ReadingLessonList(lessons, onBack) { selectedLesson = it }
    } else {
        ReadingDetailView(selectedLesson!!, onBack = { selectedLesson = null })
    }
}

@Composable
fun ReadingLessonList(
    lessons: List<Reading>,
    onBack: () -> Unit,
    onSelect: (Reading) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Bg2),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp, 16.dp, 16.dp, 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(Modifier.size(36.dp).background(Surface, RoundedCornerShape(10.dp))
                    .border(1.dp, Border, RoundedCornerShape(10.dp)).clickable { onBack() },
                    contentAlignment = Alignment.Center) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = TextSecondary, modifier = Modifier.size(18.dp))
                }
                Spacer(Modifier.width(12.dp))
                Text("Bài đọc hiểu", style = MaterialTheme.typography.titleLarge)
            }
        }

        items(lessons) { lesson ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 6.dp)
                    .background(Surface, RoundedCornerShape(12.dp))
                    .border(1.dp, Border, RoundedCornerShape(12.dp))
                    .clickable { onSelect(lesson) }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(Modifier.size(40.dp).background(GreenDim, CircleShape), contentAlignment = Alignment.Center) {
                    Text("📖", fontSize = 18.sp)
                }
                Spacer(Modifier.width(14.dp))
                Column(Modifier.weight(1f)) {
                    Text(lesson.title, style = MaterialTheme.typography.titleMedium)
                    ReadingChipBadge(lesson.level, Amber, AmberDim)
                }
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "", modifier = Modifier.size(16.dp).graphicsLayer { rotationZ = 180f }, tint = TextTertiary)
            }
        }
    }
}

@Composable
fun ReadingDetailView(lesson: Reading, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(Bg2).verticalScroll(rememberScrollState())
    ) {
        // ── Header ──
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp, 16.dp, 16.dp, 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(Modifier.size(36.dp).background(Surface, RoundedCornerShape(10.dp))
                .border(1.dp, Border, RoundedCornerShape(10.dp)).clickable { onBack() },
                contentAlignment = Alignment.Center) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = TextSecondary, modifier = Modifier.size(18.dp))
            }
            Spacer(Modifier.width(12.dp))
            Text("Nội dung bài đọc", style = MaterialTheme.typography.titleLarge)
        }

        // ── Passage ──
        Column(
            Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 16.dp)
                .clip(RoundedCornerShape(16.dp)).background(Surface)
                .border(1.dp, Border, RoundedCornerShape(16.dp))
        ) {
            Row(
                Modifier.fillMaxWidth().padding(14.dp, 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(lesson.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Text("Level: ${lesson.level}", style = MaterialTheme.typography.labelMedium, color = TextTertiary)
                }
            }
            HorizontalDivider(color = Border)
            Column(Modifier.padding(14.dp)) {
                Text(lesson.content, style = MaterialTheme.typography.bodyMedium, color = TextSecondary, lineHeight = 22.sp)
            }
        }
    }
}

@Composable
private fun ReadingQuestionCard(num: String, question: String, options: List<String>, selected: Int, onSelect: (Int) -> Unit) {
    val letters = listOf("A", "B", "C", "D")
    Column(
        Modifier.fillMaxWidth().background(Surface, RoundedCornerShape(10.dp))
            .border(1.dp, Border, RoundedCornerShape(10.dp)).padding(14.dp)
    ) {
        Text(num.uppercase(), style = MaterialTheme.typography.labelSmall, color = TextTertiary)
        Spacer(Modifier.height(8.dp))
        Text(question, style = MaterialTheme.typography.titleSmall, lineHeight = 18.sp)
        Spacer(Modifier.height(10.dp))
        options.forEachIndexed { i, opt ->
            val isSelected = i == selected
            Row(
                Modifier.fillMaxWidth().padding(bottom = 6.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (isSelected) BlueDim else Bg3)
                    .border(1.dp, if (isSelected) Blue else androidx.compose.ui.graphics.Color.Transparent, RoundedCornerShape(6.dp))
                    .clickable { onSelect(i) }.padding(9.dp, 9.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier.size(18.dp).clip(RoundedCornerShape(4.dp))
                        .background(if (isSelected) Blue else Border),
                    contentAlignment = Alignment.Center
                ) { Text(letters[i], fontSize = 10.sp, fontWeight = FontWeight.Bold, color = if (isSelected) TextPrimary else TextPrimary) }
                Spacer(Modifier.width(8.dp))
                Text(opt, style = MaterialTheme.typography.bodySmall, color = if (isSelected) Blue else TextPrimary)
            }
        }
    }
}

@Composable
private fun ReadingChipBadge(text: String, color: Color, bgColor: Color) {
    Box(
        modifier = Modifier
            .background(bgColor, RoundedCornerShape(20.dp))
            .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(text, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold, color = color)
    }
}
