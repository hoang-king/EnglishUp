package com.example.englishup.adaptors.mapper

import com.example.englishup.core.Mapper
import com.example.englishup.adaptors.datasources.local.Dto.ReadingDto
import com.example.englishup.entities.Reading

class ReadingMapper(private val dto: ReadingDto) : Mapper<Reading>() {
    override fun toEntity(): Reading {
        return Reading(
            id = dto.id,
            title = dto.title,
            content = dto.content,
            level = dto.level
        )
    }
}

