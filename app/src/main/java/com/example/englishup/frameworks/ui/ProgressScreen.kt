package com.example.englishup.frameworks.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.englishup.ui.theme.*
import kotlin.math.roundToInt

private data class WeekDay(val day: String, val correct: Int, val wrong: Int)
private data class DayLog(val date: String, val correct: Int, val wrong: Int)

@Composable
fun ProgressScreen() {
    val weekData = listOf(
        WeekDay("T2", 22, 18), WeekDay("T3", 30, 15), WeekDay("T4", 18, 22),
        WeekDay("T5", 35, 10), WeekDay("T6", 28, 17), WeekDay("T7", 40, 8), WeekDay("CN", 38, 12)
    )
    val maxBar = weekData.maxOf { it.correct + it.wrong }

    val logs = listOf(
        DayLog("30/3", 38, 12), DayLog("29/3", 40, 8), DayLog("28/3", 28, 17),
        DayLog("27/3", 35, 10), DayLog("26/3", 18, 22), DayLog("25/3", 30, 15), DayLog("24/3", 22, 18)
    )
    val maxLog = logs.maxOf { it.correct + it.wrong }

    Column(
        modifier = Modifier.fillMaxSize().background(Bg2).verticalScroll(rememberScrollState())
    ) {
        // ── Header ──
        Column(Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp)) {
            Text("Tiến độ học", style = MaterialTheme.typography.headlineLarge)
            Text("Tháng 3, 2024", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
        }

        // ── Stat Grid 2×2 ──
        Column(Modifier.padding(horizontal = 20.dp).padding(bottom = 20.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                StatCard(Modifier.weight(1f), "Đúng hôm nay", "38", Green, "▲ 6 so hôm qua", true)
                StatCard(Modifier.weight(1f), "Sai hôm nay", "12", Red, "▼ 3 so hôm qua", true)
            }
            Spacer(Modifier.height(10.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                StatCard(Modifier.weight(1f), "Độ chính xác", "76%", TextPrimary, "▲ 4% tuần này", true)
                StatCard(Modifier.weight(1f), "Từ đã học", "284", Purple, "/ 500 mục tiêu", false)
            }
        }

        // ── Weekly Bar Chart ──
        Column(
            Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 20.dp)
                .background(Surface, RoundedCornerShape(16.dp))
                .border(1.dp, Border, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Text("Đúng / Sai — 7 ngày qua", style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(14.dp))
            Row(
                Modifier.fillMaxWidth().height(100.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                weekData.forEach { d ->
                    Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(
                            Modifier.weight(1f).fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            val cH = (d.correct.toFloat() / maxBar * 88).dp
                            val wH = (d.wrong.toFloat() / maxBar * 88).dp
                            Box(Modifier.weight(1f).height(cH).clip(RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp)).background(Green))
                            Box(Modifier.weight(1f).height(wH).clip(RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp)).background(Red.copy(0.7f)))
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(d.day, fontSize = 9.sp, color = TextTertiary, fontFamily = FontFamily.Monospace)
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                LegendItem("Đúng", Green)
                LegendItem("Sai", Red)
            }
        }

        // ── Skill Breakdown ──
        Column(Modifier.padding(horizontal = 20.dp).padding(bottom = 20.dp)) {
            Text("Độ chính xác theo kỹ năng", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(14.dp))
            SkillProgressRow("📚", "Từ vựng", 0.76f, Purple, PurpleDim)
            SkillProgressRow("✏️", "Ngữ pháp", 0.72f, Blue, BlueDim)
            SkillProgressRow("📖", "Đọc hiểu", 0.82f, Green, GreenDim)
            SkillProgressRow("🎧", "Nghe", 0.65f, Amber, AmberDim)
        }

        // ── Daily Log ──
        Column(Modifier.padding(horizontal = 20.dp).padding(bottom = 24.dp)) {
            Text("Lịch sử theo ngày", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(14.dp))
            logs.forEach { d ->
                val total = d.correct + d.wrong
                val acc = (d.correct * 100f / total).roundToInt()
                val accColor = when { acc >= 75 -> Green; acc >= 55 -> Amber; else -> Red }
                Row(
                    Modifier.fillMaxWidth().padding(bottom = 8.dp)
                        .background(Surface, RoundedCornerShape(10.dp))
                        .border(1.dp, Border, RoundedCornerShape(10.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(d.date, style = MaterialTheme.typography.labelMedium, color = TextTertiary, fontFamily = FontFamily.Monospace, modifier = Modifier.width(42.dp))
                    Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        val cW = (d.correct.toFloat() / maxLog * 120).dp
                        val wW = (d.wrong.toFloat() / maxLog * 120).dp
                        Box(Modifier.height(8.dp).width(cW).clip(RoundedCornerShape(4.dp)).background(Green))
                        Box(Modifier.height(8.dp).width(wW).clip(RoundedCornerShape(4.dp)).background(Red.copy(0.8f)))
                    }
                    Spacer(Modifier.width(8.dp))
                    Text("${d.correct}đ/${d.wrong}s", style = MaterialTheme.typography.labelMedium, color = TextSecondary, fontFamily = FontFamily.Monospace)
                    Spacer(Modifier.width(8.dp))
                    Text("$acc%", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = accColor, modifier = Modifier.width(36.dp))
                }
            }
        }
    }
}

@Composable
private fun StatCard(modifier: Modifier, label: String, value: String, valueColor: androidx.compose.ui.graphics.Color, trend: String, isUp: Boolean) {
    Column(
        modifier.background(Surface, RoundedCornerShape(16.dp))
            .border(1.dp, Border, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(label, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
        Spacer(Modifier.height(6.dp))
        Text(value, fontSize = 26.sp, fontWeight = FontWeight.Bold, color = valueColor, lineHeight = 26.sp)
        Spacer(Modifier.height(3.dp))
        Text(trend, style = MaterialTheme.typography.labelMedium, color = if (isUp) Green else TextTertiary)
    }
}

@Composable
private fun LegendItem(label: String, color: androidx.compose.ui.graphics.Color) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        Box(Modifier.size(8.dp).clip(RoundedCornerShape(2.dp)).background(color))
        Text(label, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
    }
}

@Composable
private fun SkillProgressRow(icon: String, name: String, progress: Float, color: androidx.compose.ui.graphics.Color, dimColor: androidx.compose.ui.graphics.Color) {
    Row(
        Modifier.fillMaxWidth().padding(bottom = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.size(34.dp).background(dimColor, RoundedCornerShape(9.dp)), contentAlignment = Alignment.Center) {
            Text(icon, fontSize = 15.sp)
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(name, style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(5.dp))
            Box(Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)).background(Bg3)) {
                Box(Modifier.fillMaxWidth(progress).fillMaxHeight().clip(RoundedCornerShape(3.dp)).background(color))
            }
        }
        Spacer(Modifier.width(12.dp))
        Text("${(progress * 100).roundToInt()}%", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = color, modifier = Modifier.width(36.dp))
    }
}
