package io.schiar.ruleofthree.viewmodel

import androidx.lifecycle.ViewModel
import io.schiar.ruleofthree.model.Numbers
import io.schiar.ruleofthree.model.repository.MainRepository
import io.schiar.ruleofthree.model.repository.NumbersRepository
import io.schiar.ruleofthree.view.viewdata.NumbersViewData
import io.schiar.ruleofthree.viewmodel.util.toViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class NumbersViewModel(private val repository: NumbersRepository = MainRepository()): ViewModel() {
    private val _numbers = MutableStateFlow(NumbersViewData())
    val numbers: StateFlow<NumbersViewData> = _numbers

    init { repository.subscribeForNumbers(::onInputChanged) }

    suspend fun addInput(value: String, position: Int) {
        repository.addToInput(value = value, position = position)
    }

    suspend fun removeInput(position: Int) {
        repository.removeFromInput(position = position)
    }

    suspend fun clearInput(position: Int) {
        repository.clearInput(position = position)
    }

    suspend fun clearAllInputs() {
        repository.clearAllInputs()
    }

    suspend fun submitInput() {
        repository.submitToHistory()
    }

    private fun onInputChanged(numbers: Numbers) {
        _numbers.update { numbers.toViewData() }
    }
}