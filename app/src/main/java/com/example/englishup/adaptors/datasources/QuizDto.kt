package com.example.englishup.adaptors.datasources

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz")
data class QuizDto(
    @PrimaryKey val id: String,
    val question: String,
    val options: List<String>,
    val correctAnswer: String
)
