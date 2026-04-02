package com.example.englishup.core.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object DateUtils {
    const val PATTERN_DB = "yyyy-MM-dd"
    const val PATTERN_UI = "dd/MM/yyyy"
    const val PATTERN_FULL = "yyyy-MM-dd HH:mm:ss"
    const val PATTERN_UI_TIME = "HH:mm dd/MM/yyyy"

    private val dbFormatter = DateTimeFormatter.ofPattern(PATTERN_DB)
    private val uiFormatter = DateTimeFormatter.ofPattern(PATTERN_UI)
    private val fullFormatter = DateTimeFormatter.ofPattern(PATTERN_FULL)
    private val uiTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_UI_TIME)

    fun today(): String = LocalDate.now().format(dbFormatter)
    
    fun nowFull(): String = LocalDateTime.now().format(fullFormatter)

    fun formatDate(date: LocalDate, pattern: String = PATTERN_UI): String {
        return date.format(DateTimeFormatter.ofPattern(pattern))
    }

    fun parseDate(dateStr: String, pattern: String = PATTERN_DB): LocalDate? {
        return try {
            LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern))
        } catch (e: Exception) {
            null
        }
    }

    fun toUIDate(dbDate: String): String {
        return parseDate(dbDate, PATTERN_DB)?.format(uiFormatter) ?: dbDate
    }

    fun daysAgo(days: Long): String = LocalDate.now().minusDays(days).format(dbFormatter)

    fun isToday(dateStr: String): Boolean {
        return dateStr == today()
    }

    fun isYesterday(dateStr: String): Boolean {
        return dateStr == daysAgo(1)
    }

    fun getDayOfWeek(dateStr: String): String {
        val date = parseDate(dateStr) ?: return ""
        return when (date.dayOfWeek.value) {
            1 -> "Thứ 2"
            2 -> "Thứ 3"
            3 -> "Thứ 4"
            4 -> "Thứ 5"
            5 -> "Thứ 6"
            6 -> "Thứ 7"
            7 -> "Chủ Nhật"
            else -> ""
        }
    }

    fun getDaysBetween(start: String, end: String): Long {
        val startDate = parseDate(start) ?: return 0
        val endDate = parseDate(end) ?: return 0
        return ChronoUnit.DAYS.between(startDate, endDate)
    }

    fun getFriendlyDate(dateStr: String): String {
        return when {
            isToday(dateStr) -> "Hôm nay"
            isYesterday(dateStr) -> "Hôm qua"
            else -> toUIDate(dateStr)
        }
    }
}