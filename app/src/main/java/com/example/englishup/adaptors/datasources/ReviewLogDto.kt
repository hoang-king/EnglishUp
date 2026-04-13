package com.example.englishup.adaptors.datasources.local.Dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.englishup.entities.ReviewGrade

@Entity(tableName = "review_logs")
data class ReviewLogDto(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val wordId: String,
    val word: String,
    val grade: ReviewGrade,
    val timestamp: String,
    val isCorrect: Boolean
)
