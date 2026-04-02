package com.example.englishup.adaptors.mapper

import com.example.englishup.core.Mapper
import com.example.englishup.adaptors.datasources.local.Dto.GrammarDto
import com.example.englishup.entities.Grammar

class GrammarMapper(private val dto: GrammarDto) : Mapper<Grammar>() {
    override fun toEntity(): Grammar {
        return Grammar(
            id = dto.id,
            word = dto.word,
            translation = dto.translation,
            phonetic = dto.phonetic
        )
    }
}

