package com.example.englishup.adaptors.mapper

import com.example.englishup.adaptors.datasources.local.Dto.ReadingDto
import com.example.englishup.entities.Reading
import javax.inject.Inject

class ReadingMapper @Inject constructor() {
    fun toEntity(dto: ReadingDto): Reading {
        return Reading(
            id = dto.id,
            title = dto.title,
            content = dto.content,
            level = dto.level,
            questions = dto.questions
        )
    }

    fun toDto(entity: Reading): ReadingDto {
        return ReadingDto(
            id = entity.id,
            title = entity.title,
            content = entity.content,
            level = entity.level,
            questions = entity.questions
        )
    }
}
