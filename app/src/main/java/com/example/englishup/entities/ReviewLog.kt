package com.example.englishup.entities

import com.example.englishup.entities.ReviewGrade

data class ReviewLog(
    val id: String,
    val wordId: String,
    val word: String,
    val grade: ReviewGrade,
    val timestamp: String, // yyyy-MM-dd HH:mm:ss
    val isCorrect: Boolean
)
