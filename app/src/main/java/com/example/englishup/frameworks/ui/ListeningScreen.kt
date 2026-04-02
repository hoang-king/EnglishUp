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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.englishup.core.utils.AudioPlayer
import com.example.englishup.entities.Listening
import com.example.englishup.ui.theme.*
import com.example.englishup.view_model.ListeningViewModel
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun ListeningScreen(
    onBack: () -> Unit = {},
    viewModel: ListeningViewModel = hiltViewModel()
) {
    val lessons by viewModel.state.collectAsStateWithLifecycle()
    var selectedLesson by remember { mutableStateOf<Listening?>(null) }

    if (selectedLesson == null) {
        // Lesson List View
        ListeningLessonList(lessons, onBack) { selectedLesson = it }
    } else {
        // Detailed Player View
        ListeningPlayerView(selectedLesson!!, onBack = { selectedLesson = null })
    }
}

@Composable
fun ListeningLessonList(
    lessons: List<Listening>,
    onBack: () -> Unit,
    onSelect: (Listening) -> Unit
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
                Text("Bài nghe tiếng Anh", style = MaterialTheme.typography.titleLarge)
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
                Box(Modifier.size(40.dp).background(BlueDim, CircleShape), contentAlignment = Alignment.Center) {
                    Text("🎧", fontSize = 18.sp)
                }
                Spacer(Modifier.width(14.dp))
                Column(Modifier.weight(1f)) {
                    Text(lesson.title, style = MaterialTheme.typography.titleMedium)
                    Text(lesson.description, style = MaterialTheme.typography.bodySmall, color = TextSecondary, maxLines = 1)
                }
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "", modifier = Modifier.size(16.dp).graphicsLayer { rotationZ = 180f }, tint = TextTertiary)
            }
        }
    }
}

@Composable
fun ListeningPlayerView(lesson: Listening, onBack: () -> Unit) {
    val waveHeights = remember { List(30) { Random.nextInt(15, 65) } }
    var isPlaying by remember { mutableStateOf(false) }

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
            Text("Đang nghe", style = MaterialTheme.typography.titleLarge)
        }

        // ── Audio Player ──
        Column(
            Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 20.dp)
                .background(Surface, RoundedCornerShape(20.dp))
                .border(1.dp, Border, RoundedCornerShape(20.dp))
                .padding(20.dp)
        ) {
            Text("LESSON", style = MaterialTheme.typography.labelSmall, color = TextTertiary)
            Spacer(Modifier.height(12.dp))
            Text(lesson.title, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(4.dp))
            Text(lesson.description, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
            Spacer(Modifier.height(20.dp))

            // Waveform (Simulated)
            Row(
                Modifier.fillMaxWidth().height(48.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                waveHeights.forEach { h ->
                    Box(
                        Modifier.weight(1f).height(h.dp).clip(RoundedCornerShape(2.dp))
                            .background(if (isPlaying) Green else Border2)
                    )
                }
            }
            Spacer(Modifier.height(32.dp))

            // Controls
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(64.dp).clip(CircleShape).background(Green).clickable { 
                    if (!isPlaying) {
                        AudioPlayer.play(lesson.audioUrl)
                        isPlaying = true
                    } else {
                        AudioPlayer.stop()
                        isPlaying = false
                    }
                }, contentAlignment = Alignment.Center) {
                    Text(if (isPlaying) "⏸" else "▶", fontSize = 24.sp, color = Bg)
                }
            }
        }
    }
}

@Composable
private fun ListeningQuestionCard(num: String, question: String, options: List<String>, selected: Int, onSelect: (Int) -> Unit) {
    Column(
        Modifier.fillMaxWidth()
            .background(Surface, RoundedCornerShape(10.dp))
            .border(1.dp, Border, RoundedCornerShape(10.dp))
            .padding(14.dp)
    ) {
        Text(num.uppercase(), style = MaterialTheme.typography.labelSmall, color = TextTertiary)
        Spacer(Modifier.height(6.dp))
        Text(question, style = MaterialTheme.typography.titleSmall)
        Spacer(Modifier.height(10.dp))
        options.forEachIndexed { i, opt ->
            val isSelected = i == selected
            Box(
                Modifier.fillMaxWidth().padding(bottom = 6.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (isSelected) GreenDim else Bg3)
                    .border(1.dp, if (isSelected) Green else androidx.compose.ui.graphics.Color.Transparent, RoundedCornerShape(6.dp))
                    .clickable { onSelect(i) }
                    .padding(8.dp, 8.dp)
            ) {
                Text(opt, style = MaterialTheme.typography.bodySmall, color = if (isSelected) Green else TextPrimary)
            }
        }
    }
}
