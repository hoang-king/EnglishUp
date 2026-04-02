package com.example.englishup.usecases

import com.example.englishup.entities.Vocabulary
import com.example.englishup.repositories.VocabularyRepository
import javax.inject.Inject

class SearchWordUseCase @Inject constructor(
    private val repository: VocabularyRepository
) {
    suspend fun execute(query: String): List<Vocabulary> {
        return repository.search(query)
    }
}
