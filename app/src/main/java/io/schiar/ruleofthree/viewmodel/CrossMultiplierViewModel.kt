package io.schiar.ruleofthree.viewmodel

import androidx.lifecycle.ViewModel
import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.repository.MainRepository
import io.schiar.ruleofthree.model.repository.CrossMultiplierRepository
import io.schiar.ruleofthree.view.viewdata.CrossMultiplierViewData
import io.schiar.ruleofthree.viewmodel.util.toViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CrossMultiplierViewModel(private val repository: CrossMultiplierRepository = MainRepository()): ViewModel() {
    private val _crossMultiplier = MutableStateFlow(CrossMultiplierViewData())
    val crossMultiplier: StateFlow<CrossMultiplierViewData> = _crossMultiplier
    private val _isThereHistory = MutableStateFlow(false)
    val isThereHistory: StateFlow<Boolean> = _isThereHistory

    init {
        repository.subscribeForCrossMultipliers(::onInputChanged)
        repository.subscribeForIsThereHistories(::onIsThereHistoryChanged)
    }

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

    private fun onInputChanged(crossMultiplier: CrossMultiplier) {
        _crossMultiplier.update { crossMultiplier.toViewData() }
    }

    private fun onIsThereHistoryChanged(value: Boolean) {
        _isThereHistory.update { value }
    }
}