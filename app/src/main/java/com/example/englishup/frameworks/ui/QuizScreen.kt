package com.example.englishup.frameworks.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.englishup.entities.Quiz
import com.example.englishup.ui.theme.*
import com.example.englishup.view_model.QuizViewModel

@Composable
fun QuizScreen(
    onBack: () -> Unit = {},
    viewModel: QuizViewModel = hiltViewModel()
) {
    val quizList by viewModel.state.collectAsStateWithLifecycle()
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var selectedAnswerIndex by remember { mutableIntStateOf(-1) }
    var isAnswered by remember { mutableStateOf(false) }
    var score by remember { mutableIntStateOf(0) }
    var wrongCount by remember { mutableIntStateOf(0) }

    val currentQuiz = quizList.getOrNull(currentQuestionIndex)
    val letters = listOf("A", "B", "C", "D")

    if (quizList.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Green)
        }
        return
    }

    if (currentQuiz == null) {
        // Quiz completed
        QuizResultScreen(score, wrongCount, onBack)
        return
    }

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
            Text("Quiz — General English", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.weight(1f))
            ChipBadge("Live", Green, GreenDim)
        }

        // ── Progress ──
        Column(Modifier.padding(horizontal = 20.dp).padding(bottom = 16.dp)) {
            Box(Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)).background(Bg3)) {
                Box(Modifier.fillMaxWidth((currentQuestionIndex + 1).toFloat() / quizList.size).fillMaxHeight().clip(RoundedCornerShape(3.dp)).background(Green))
            }
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("${currentQuestionIndex + 1} / ${quizList.size} câu", style = MaterialTheme.typography.labelMedium, color = TextSecondary, fontFamily = FontFamily.Monospace)
                Text("✓ $score đúng", style = MaterialTheme.typography.labelMedium, color = Green, fontFamily = FontFamily.Monospace)
                Text("✗ $wrongCount sai", style = MaterialTheme.typography.labelMedium, color = Red, fontFamily = FontFamily.Monospace)
            }
        }

        // ── Question ──
        Column(Modifier.padding(horizontal = 20.dp).padding(bottom = 20.dp)) {
            Text("QUESTION ${currentQuestionIndex + 1}", style = MaterialTheme.typography.labelSmall, color = TextTertiary)
            Spacer(Modifier.height(12.dp))
            Text(currentQuiz.question,
                style = MaterialTheme.typography.headlineMedium, lineHeight = 26.sp)
        }

        // ── Options ──
        Column(Modifier.padding(horizontal = 20.dp).padding(bottom = 24.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            currentQuiz.options.forEachIndexed { i, option ->
                val isCorrect = isAnswered && option == currentQuiz.correctAnswer
                val isWrong = isAnswered && i == selectedAnswerIndex && option != currentQuiz.correctAnswer
                val isSelected = i == selectedAnswerIndex
                
                val borderColor = when {
                    isCorrect -> Green
                    isWrong -> Red
                    isSelected && !isAnswered -> Blue
                    else -> Border
                }
                val bgColor = when {
                    isCorrect -> GreenDim
                    isWrong -> RedDim
                    isSelected && !isAnswered -> BlueDim
                    else -> Surface
                }
                val textColor = when {
                    isCorrect -> Green
                    isWrong -> Red
                    isSelected && !isAnswered -> Blue
                    else -> TextPrimary
                }
                val keyBg = when {
                    isCorrect -> Green
                    isWrong -> Red
                    isSelected && !isAnswered -> Blue
                    else -> Bg3
                }

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .background(bgColor, RoundedCornerShape(10.dp))
                        .border(1.5.dp, borderColor, RoundedCornerShape(10.dp))
                        .clickable(enabled = !isAnswered) { selectedAnswerIndex = i }
                        .padding(15.dp, 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.size(28.dp).background(keyBg, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center) {
                        Text(letters.getOrNull(i) ?: "?", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = if (isSelected || isCorrect || isWrong) Bg else TextPrimary, fontFamily = FontFamily.Monospace)
                    }
                    Spacer(Modifier.width(12.dp))
                    Text(option, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium, color = textColor)
                }
            }
        }

        // ── Action Button ──
        if (!isAnswered) {
            Box(
                Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 24.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (selectedAnswerIndex != -1) Green else Bg3)
                    .clickable(enabled = selectedAnswerIndex != -1) {
                        isAnswered = true
                        if (currentQuiz.options[selectedAnswerIndex] == currentQuiz.correctAnswer) {
                            score++
                        } else {
                            wrongCount++
                        }
                    }
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Kiểm tra", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = if (selectedAnswerIndex != -1) Bg else TextTertiary)
            }
        } else {
            Box(
                Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 24.dp)
                    .clip(RoundedCornerShape(16.dp)).background(Green).clickable {
                        isAnswered = false
                        selectedAnswerIndex = -1
                        currentQuestionIndex++
                    }.padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Câu tiếp theo →", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Bg)
            }
        }
    }
}

@Composable
fun QuizResultScreen(score: Int, wrong: Int, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(Bg2).padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("🎉", fontSize = 64.sp)
        Spacer(Modifier.height(16.dp))
        Text("Hoàn thành!", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(8.dp))
        Text("Bạn đã trả lời đúng $score / ${score + wrong} câu hỏi.", textAlign = TextAlign.Center, color = TextSecondary)
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(containerColor = Green),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Quay lại Trang chủ", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ChipBadge(text: String, color: Color, bgColor: Color) {
    Box(
        modifier = Modifier
            .background(bgColor, RoundedCornerShape(20.dp))
            .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(text, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold, color = color)
    }
}
