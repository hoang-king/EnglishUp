package com.example.englishup.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishup.entities.Listening
import com.example.englishup.usecases.GetListeningListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListeningViewModel @Inject constructor(
    private val getListeningListUseCase: GetListeningListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<List<Listening>>(emptyList())
    val state: StateFlow<List<Listening>> = _state

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _state.value = getListeningListUseCase.execute()
        }
    }
}
