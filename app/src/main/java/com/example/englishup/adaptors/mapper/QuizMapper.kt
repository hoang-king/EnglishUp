package com.example.englishup.adaptors.mapper

import com.example.englishup.core.Mapper
import com.example.englishup.adaptors.datasources.local.Dto.QuizDto
import com.example.englishup.entities.Quiz

class QuizMapper(private val dto: QuizDto) : Mapper<Quiz>() {
    override fun toEntity(): Quiz {
        return Quiz(
            id = dto.id,
            question = dto.question,
            options = dto.options,
            correctAnswer = dto.correctAnswer
        )
    }
}

