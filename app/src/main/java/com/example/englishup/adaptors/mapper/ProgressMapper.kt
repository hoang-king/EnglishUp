package com.example.englishup.adaptors.mapper

import com.example.englishup.core.Mapper
import com.example.englishup.adaptors.datasources.local.Dto.ProgressDto
import com.example.englishup.entities.Progress

class ProgressMapper(private val dto: ProgressDto) : Mapper<Progress>() {
    override fun toEntity(): Progress {
        return Progress(
            id = dto.id,
            date = dto.date,
            score = dto.score,
            completedLessons = dto.completedLessons
        )
    }
}
