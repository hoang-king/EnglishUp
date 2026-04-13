package com.example.englishup.adaptors.mapper

import com.example.englishup.core.Mapper
import com.example.englishup.adaptors.datasources.local.Dto.GrammarDto
import com.example.englishup.entities.Grammar

class GrammarMapper(private val dto: GrammarDto) : Mapper<Grammar>() {
    override fun toEntity(): Grammar {
        return Grammar(
            id = dto.id,
            category = dto.category,
            question = dto.question,
            context = dto.context,
            optionA = dto.optionA,
            optionB = dto.optionB,
            optionC = dto.optionC,
            optionD = dto.optionD,
            correctAnswer = dto.correctAnswer,
            explanation = dto.explanation
        )
    }
}
