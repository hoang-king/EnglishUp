package com.example.englishup.adaptors.datasources

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grammar")
data class GrammarDto(
    @PrimaryKey val id: String,
    val word: String,
    val translation: String,
    val phonetic: String
)
