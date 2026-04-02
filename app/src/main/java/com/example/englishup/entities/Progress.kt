package com.example.englishup.entities

data class Progress(
    val id: String,
    val date: String,
    val score: Int,
    val completedLessons: Int
)
