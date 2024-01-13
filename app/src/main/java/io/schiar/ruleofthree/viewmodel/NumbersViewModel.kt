package io.schiar.ruleofthree.viewmodel

import androidx.lifecycle.ViewModel
import io.schiar.ruleofthree.model.Numbers
import io.schiar.ruleofthree.model.repository.MainRepository
import io.schiar.ruleofthree.model.repository.NumbersRepository
import io.schiar.ruleofthree.view.viewdata.NumbersViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class NumbersViewModel(
    private val repository: NumbersRepository = MainRepository()
): ViewModel() {
    private val _numbers = MutableStateFlow(repository.numbers.toViewData())
    val numbers: StateFlow<NumbersViewData> = _numbers
    private val _result = MutableStateFlow("?")
    val result: StateFlow<String> = _result

    fun init() {
        repository.subscribeForNumbers(::onInputChanged)
        repository.subscribeForResult(::onResultChanged)
    }

    fun addInput(value: String, position: Int) {
        repository.addToInput(value = value, position = position)
    }

    fun removeInput(position: Int) {
        repository.removeFromInput(position = position)
    }

    fun clearInput(position: Int) {
        repository.clearInput(position = position)
    }

    private fun onInputChanged(numbers: Numbers) {
        _numbers.update { numbers.toViewData() }
    }

    private fun onResultChanged(result: Double?) {
        _result.update { result.toFormattedString() }
    }
}