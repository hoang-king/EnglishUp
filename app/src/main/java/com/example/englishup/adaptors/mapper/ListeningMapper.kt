package com.example.englishup.adaptors.mapper

import com.example.englishup.core.Mapper
import com.example.englishup.adaptors.datasources.local.Dto.ListeningDto
import com.example.englishup.entities.Listening

class ListeningMapper(private val dto: ListeningDto) : Mapper<Listening>() {
    override fun toEntity(): Listening {
        return Listening(
            id = dto.id,
            title = dto.title,
            description = dto.description,
            audioUrl = dto.audioUrl
        )
    }
}

