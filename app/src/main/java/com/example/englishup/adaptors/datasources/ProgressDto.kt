package com.example.englishup.adaptors.datasources

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "progress")
data class ProgressDto(
    @PrimaryKey val id: String,
    val userId: String,
    val score: Int,
    val completedLessons: Int
)
