package com.example.englishup.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishup.entities.Quiz
import com.example.englishup.usecases.GetQuizListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getQuizListUseCase: GetQuizListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<List<Quiz>>(emptyList())
    val state: StateFlow<List<Quiz>> = _state

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _state.value = getQuizListUseCase.execute()
        }
    }
}
