package com.example.englishup.adaptors.mapper

import com.example.englishup.adaptors.datasources.local.Dto.VocabularyDto
import com.example.englishup.adaptors.datasources.local.Dto.ReviewLogDto
import com.example.englishup.entities.Vocabulary
import com.example.englishup.entities.ReviewLog
import javax.inject.Inject

class VocabularyMapper @Inject constructor() {
    fun toEntity(dto: VocabularyDto): Vocabulary {
        return Vocabulary(
            id = dto.id,
            word = dto.word,
            phonetic = dto.phonetic,
            partOfSpeech = dto.partOfSpeech,
            definitionEn = dto.definitionEn,
            definitionVi = dto.definitionVi,
            example = dto.example,
            audioUrl = dto.audioUrl,
            category = dto.category,
            easeFactor = dto.easeFactor,
            intervalDays = dto.intervalDays,
            repetitions = dto.repetitions,
            nextReviewDate = dto.nextReviewDate,
            status = dto.status
        )
    }

    fun toDto(entity: Vocabulary): VocabularyDto {
        return VocabularyDto(
            id = entity.id,
            word = entity.word,
            phonetic = entity.phonetic,
            partOfSpeech = entity.partOfSpeech,
            definitionEn = entity.definitionEn,
            definitionVi = entity.definitionVi,
            example = entity.example,
            audioUrl = entity.audioUrl,
            category = entity.category,
            easeFactor = entity.easeFactor,
            intervalDays = entity.intervalDays,
            repetitions = entity.repetitions,
            nextReviewDate = entity.nextReviewDate,
            status = entity.status
        )
    }

    fun toReviewLogEntity(dto: ReviewLogDto): ReviewLog {
        return ReviewLog(
            id = dto.id.toString(),
            wordId = dto.wordId,
            word = dto.word,
            grade = dto.grade,
            timestamp = dto.timestamp,
            isCorrect = dto.isCorrect
        )
    }

    fun toReviewLogDto(entity: ReviewLog): ReviewLogDto {
        return ReviewLogDto(
            wordId = entity.wordId,
            word = entity.word,
            grade = entity.grade,
            timestamp = entity.timestamp,
            isCorrect = entity.isCorrect
        )
    }
}
