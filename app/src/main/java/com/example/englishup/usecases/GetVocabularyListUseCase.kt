package com.example.englishup.usecases

import com.example.englishup.entities.Vocabulary
import com.example.englishup.repositories.VocabularyRepository
import javax.inject.Inject

class GetVocabularyListUseCase @Inject constructor(
    private val repository: VocabularyRepository
) {
    suspend fun execute(): List<Vocabulary> {
        return repository.getAll()
    }
}
