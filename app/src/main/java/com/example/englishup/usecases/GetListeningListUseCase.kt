package com.example.englishup.usecases

import com.example.englishup.entities.Listening
import com.example.englishup.repositories.ListeningRepository
import javax.inject.Inject

class GetListeningListUseCase @Inject constructor(
    private val repository: ListeningRepository
) {
    suspend fun execute(): List<Listening> {
        return repository.getAll()
    }
}
