package com.example.englishup.adaptors.datasources.local.Dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.englishup.entities.WordStatus

@Entity(tableName = "vocabulary")
data class VocabularyDto(
    @PrimaryKey val id: String,
    val word: String,
    val phonetic: String,
    val partOfSpeech: String,
    val definitionEn: String,
    val definitionVi: String,
    val example: String,
    val audioUrl: String,
    val category: String,
    val easeFactor: Double = 2.5,
    val intervalDays: Int = 1,
    val repetitions: Int = 0,
    val nextReviewDate: String = "",
    val status: WordStatus = WordStatus.NEW
)
