package com.example.englishup.frameworks.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.englishup.entities.Grammar
import com.example.englishup.view_model.GrammarViewModel
import com.example.englishup.ui.theme.*

@Composable
fun GrammarScreen(
    onBack: () -> Unit = {},
    viewModel: GrammarViewModel = hiltViewModel()
) {
    val grammarList by viewModel.state.collectAsStateWithLifecycle()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Thì động từ", "Mệnh đề", "Câu điều kiện")
    
    // Lưu trạng thái đáp án cho từng câu hỏi sử dụng ID
    val selectedAnswers = remember { mutableStateMapOf<String, String>() }

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
                            .background(if (i == selectedTab) GreenDim else Color.Transparent)
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
                    CircularProgressIndicator(color = Green)
                }
            }
        }

        val filteredList = grammarList.filter { it.category == tabs[selectedTab] }

        itemsIndexed(filteredList) { index, grammar ->
            GrammarQuestionCard(
                index = index + 1,
                grammar = grammar,
                selectedOption = selectedAnswers[grammar.id],
                onOptionSelected = { selectedAnswers[grammar.id] = it }
            )
        }
    }
}

@Composable
fun GrammarQuestionCard(
    index: Int,
    grammar: Grammar,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit
) {
    val isAnswered = selectedOption != null
    val isCorrect = selectedOption == grammar.correctAnswer

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 16.dp)
            .background(Surface, RoundedCornerShape(16.dp))
            .border(1.dp, if (isAnswered) (if (isCorrect) Green.copy(0.5f) else Red.copy(0.5f)) else Border, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Câu hỏi $index · ${grammar.category}",
                style = MaterialTheme.typography.labelSmall,
                color = TextTertiary
            )
        }
        
        Spacer(Modifier.height(10.dp))
        
        Text(
            text = grammar.question,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            lineHeight = 24.sp
        )
        
        if (grammar.context.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = "\"${grammar.context}\"",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                fontStyle = FontStyle.Italic
            )
        }
        
        Spacer(Modifier.height(16.dp))
        
        val options = listOf(
            "A" to grammar.optionA,
            "B" to grammar.optionB,
            "C" to grammar.optionC,
            "D" to grammar.optionD
        )
        
        options.forEach { (key, value) ->
            val isThisSelected = selectedOption == key
            val isThisCorrect = grammar.correctAnswer == key
            
            val backgroundColor = when {
                !isAnswered -> Bg3
                isThisSelected && isThisCorrect -> GreenDim
                isThisSelected && !isThisCorrect -> RedDim
                isThisCorrect -> GreenDim.copy(alpha = 0.3f)
                else -> Bg3
            }
            
            val borderColor = when {
                !isAnswered -> Color.Transparent
                isThisSelected && isThisCorrect -> Green
                isThisSelected && !isThisCorrect -> Red
                isThisCorrect -> Green.copy(alpha = 0.5f)
                else -> Color.Transparent
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(backgroundColor)
                    .border(1.dp, borderColor, RoundedCornerShape(10.dp))
                    .clickable(enabled = !isAnswered) { onOptionSelected(key) }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isThisSelected) (if (isCorrect) Green else Red) else Border),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = key,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isThisSelected) Bg else TextPrimary
                    )
                }
                Spacer(Modifier.width(12.dp))
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isThisSelected) (if (isCorrect) Green else Red) else TextPrimary
                )
            }
        }
        
        if (isAnswered) {
            Spacer(Modifier.height(12.dp))
            HorizontalDivider(color = Border)
            Spacer(Modifier.height(12.dp))
            Text(
                text = "Giải thích",
                style = MaterialTheme.typography.labelLarge,
                color = Green,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = grammar.explanation,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                lineHeight = 18.sp
            )
        }
    }
}
