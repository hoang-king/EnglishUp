package com.example.englishup.usecases

import com.example.englishup.entities.Progress
import com.example.englishup.repositories.ProgressRepository
import javax.inject.Inject

class GetProgressListUseCase @Inject constructor(
    private val repository: ProgressRepository
) {
    suspend fun execute(): List<Progress> {
        return repository.getAll()
    }
}
