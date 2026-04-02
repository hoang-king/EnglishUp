package com.example.englishup.entities

data class Vocabulary(
    val id: String,
    val word: String,
    val phonetic: String,
    val partOfSpeech: String,
    val definitionEn: String,
    val definitionVi: String,
    val example: String,
    val audioUrl: String,
    val category: String,           // "TOEIC" | "IELTS" | "Business"
    val easeFactor: Double = 2.5,   // SRS ease factor
    val intervalDays: Int = 1,      // Số ngày đến lần ôn tiếp
    val repetitions: Int = 0,
    val nextReviewDate: String = "",
    val status: WordStatus = WordStatus.NEW
)

enum class WordStatus { NEW, LEARNING, REVIEW, KNOWN }
enum class ReviewGrade { AGAIN, HARD, GOOD, EASY }
