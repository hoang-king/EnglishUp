package com.example.englishup.usecases

import com.example.englishup.entities.Reading
import com.example.englishup.repositories.ReadingRepository
import javax.inject.Inject

class GetReadingListUseCase @Inject constructor(
    private val repository: ReadingRepository
) {
    suspend fun execute(): List<Reading> {
        return repository.getAll()
    }
}
