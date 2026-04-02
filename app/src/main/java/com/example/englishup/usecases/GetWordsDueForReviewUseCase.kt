package com.example.englishup.usecases

import com.example.englishup.entities.Vocabulary
import com.example.englishup.repositories.VocabularyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWordsDueForReviewUseCase @Inject constructor(
    private val repository: VocabularyRepository
) {
    fun execute(today: String): Flow<List<Vocabulary>> {
        return repository.getDueForReview(today)
    }
}
