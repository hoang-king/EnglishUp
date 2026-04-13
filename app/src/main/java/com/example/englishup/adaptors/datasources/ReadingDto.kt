package com.example.englishup.adaptors.datasources.local.Dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.englishup.entities.ReadingQuestion

@Entity(tableName = "reading")
data class ReadingDto(
    @PrimaryKey val id: String,
    val title: String,
    val content: String,
    val level: String,
    val questions: List<ReadingQuestion> = emptyList()
)
