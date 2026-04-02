package com.example.englishup.usecases

import com.example.englishup.entities.Vocabulary
import com.example.englishup.repositories.VocabularyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWordsUseCase @Inject constructor(
    private val repository: VocabularyRepository
) {
    fun execute(): Flow<List<Vocabulary>> {
        return repository.getAllFlow()
    }
}
