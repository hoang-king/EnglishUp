package com.example.englishup.usecases

import com.example.englishup.entities.Grammar
import com.example.englishup.repositories.GrammarRepository
import javax.inject.Inject

class GetGrammarListUseCase @Inject constructor(
    private val repository: GrammarRepository
) {
    suspend fun execute(): List<Grammar> {
        return repository.getAll()
    }
}
