package com.example.englishup.adaptors.datasources

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reading")
data class ReadingDto(
    @PrimaryKey val id: String,
    val title: String,
    val content: String,
    val level: String
)
