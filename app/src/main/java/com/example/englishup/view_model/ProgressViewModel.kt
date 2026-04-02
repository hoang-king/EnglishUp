package com.example.englishup.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishup.entities.Progress
import com.example.englishup.usecases.GetProgressListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val getProgressListUseCase: GetProgressListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<List<Progress>>(emptyList())
    val state: StateFlow<List<Progress>> = _state

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _state.value = getProgressListUseCase.execute()
        }
    }
}
