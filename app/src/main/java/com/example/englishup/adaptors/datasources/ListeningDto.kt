package com.example.englishup.adaptors.datasources

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "listening")
data class ListeningDto(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val audioUrl: String
)
