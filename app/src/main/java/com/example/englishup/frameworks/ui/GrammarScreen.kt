package com.example.englishup.frameworks.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.englishup.entities.Grammar
import com.example.englishup.view_model.GrammarViewModel
import com.example.englishup.ui.theme.*

private data class GrammarTopicItem(
    val icon: String, val name: String, val nameEn: String,
    val lessons: Int, val completed: Int,
    val iconBg: androidx.compose.ui.graphics.Color
)

@Composable
fun GrammarScreen(
    onBack: () -> Unit = {},
    viewModel: GrammarViewModel = hiltViewModel()
) {
    val grammarList by viewModel.state.collectAsStateWithLifecycle()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("TOEIC", "IELTS", "Cần ôn")

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Bg2),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp, 16.dp, 16.dp, 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier.size(36.dp).background(Surface, RoundedCornerShape(10.dp))
                        .border(1.dp, Border, RoundedCornerShape(10.dp)).clickable { onBack() },
                    contentAlignment = Alignment.Center
                ) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = TextSecondary, modifier = Modifier.size(18.dp)) }
                Spacer(Modifier.width(12.dp))
                Text("Ngữ pháp", style = MaterialTheme.typography.titleLarge)
            }
        }

        item {
            Row(Modifier.padding(horizontal = 20.dp).padding(bottom = 12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                tabs.forEachIndexed { i, tab ->
                    Box(
                        Modifier.clip(RoundedCornerShape(20.dp))
                            .background(if (i == selectedTab) GreenDim else androidx.compose.ui.graphics.Color.Transparent)
                            .border(1.dp, if (i == selectedTab) Green.copy(0.3f) else Border, RoundedCornerShape(20.dp))
                            .clickable { selectedTab = i }
                            .padding(horizontal = 16.dp, vertical = 7.dp)
                    ) { Text(tab, style = MaterialTheme.typography.labelLarge, color = if (i == selectedTab) Green else TextSecondary) }
                }
            }
        }

        if (grammarList.isEmpty()) {
            item {
                Box(Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                    Text("Đang tải dữ liệu...", color = TextSecondary)
                }
            }
        }

        items(grammarList) { grammar ->
            // Map Grammar entity to UI item
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 10.dp)
                    .background(Surface, RoundedCornerShape(16.dp))
                    .border(1.dp, Border, RoundedCornerShape(16.dp))
                    .clickable { }.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(Modifier.size(44.dp).background(BlueDim, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center) { Text("✏️", fontSize = 20.sp) }
                Spacer(Modifier.width(14.dp))
                Column(Modifier.weight(1f)) {
                    Text(grammar.word, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(3.dp))
                    Text(grammar.translation, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
                }
                Text(grammar.phonetic, style = MaterialTheme.typography.labelSmall, color = TextTertiary)
            }
        }
    }
}
