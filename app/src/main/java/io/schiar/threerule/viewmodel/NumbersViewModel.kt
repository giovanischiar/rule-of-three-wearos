package io.schiar.threerule.viewmodel

import androidx.lifecycle.ViewModel
import io.schiar.threerule.model.repository.MainRepository
import io.schiar.threerule.model.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class NumbersViewModel(private val repository: Repository = MainRepository()): ViewModel() {
    private val _result = MutableStateFlow("?")
    val result: StateFlow<String> = _result

    fun subscribe() {
        repository.subscribeForResult(::onResultChanged)
    }

    fun inputValue(value: String, position: Int) {
        repository.inputNumber(number = value.toDoubleOrNull(), position = position)
    }

    private fun onResultChanged(result: Double?) {
        _result.update { result.toFormattedString() }
    }
}