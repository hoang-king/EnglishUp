package com.example.englishup.adaptors.datasources.local

import androidx.room.TypeConverter
import com.example.englishup.entities.WordStatus
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromWordStatus(status: WordStatus): String {
        return status.name
    }

    @TypeConverter
    fun toWordStatus(status: String): WordStatus {
        return WordStatus.valueOf(status)
    }

    @TypeConverter
    fun fromReviewGrade(grade: com.example.englishup.entities.ReviewGrade): String {
        return grade.name
    }

    @TypeConverter
    fun toReviewGrade(grade: String): com.example.englishup.entities.ReviewGrade {
        return com.example.englishup.entities.ReviewGrade.valueOf(grade)
    }

    @TypeConverter
    fun fromReadingQuestionList(value: List<com.example.englishup.entities.ReadingQuestion>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toReadingQuestionList(value: String): List<com.example.englishup.entities.ReadingQuestion> {
        val listType = object : TypeToken<List<com.example.englishup.entities.ReadingQuestion>>() {}.type
        return Gson().fromJson(value, listType)
    }
}
