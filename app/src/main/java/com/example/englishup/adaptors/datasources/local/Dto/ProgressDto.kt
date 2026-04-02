package com.example.englishup.adaptors.datasources.local.Dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "progress")
data class ProgressDto(
    @PrimaryKey val id: String, // Can be the date itself or a unique ID
    val date: String,
    val score: Int,
    val completedLessons: Int
)