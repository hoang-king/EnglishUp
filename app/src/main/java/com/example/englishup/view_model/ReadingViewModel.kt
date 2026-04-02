package com.example.englishup.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishup.entities.Reading
import com.example.englishup.usecases.GetReadingListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadingViewModel @Inject constructor(
    private val getReadingListUseCase: GetReadingListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<List<Reading>>(emptyList())
    val state: StateFlow<List<Reading>> = _state

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _state.value = getReadingListUseCase.execute()
        }
    }
}
