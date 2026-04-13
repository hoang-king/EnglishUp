package com.example.englishup.entities

data class Reading(
    val id: String,
    val title: String,
    val content: String,
    val level: String,
    val questions: List<ReadingQuestion> = emptyList()
)

data class ReadingQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswer: String
)
