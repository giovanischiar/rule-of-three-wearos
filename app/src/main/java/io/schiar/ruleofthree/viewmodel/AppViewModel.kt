package io.schiar.ruleofthree.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.schiar.ruleofthree.model.repository.AppRepository
import io.schiar.ruleofthree.model.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel(private val appRepository: AppRepository = MainRepository()): ViewModel() {
    private val _isThereHistory = MutableStateFlow(value = false)
    val isThereHistory: StateFlow<Boolean> = _isThereHistory

    constructor(isThereHistory: Boolean): this() { _isThereHistory.update { isThereHistory } }

    init {
        appRepository.subscribeForIsTherePastCrossMultipliers(::onIsTherePastCrossMultiplier)
        viewModelScope.launch {
            appRepository.loadPastCrossMultipliers()
        }
    }

    private fun onIsTherePastCrossMultiplier(value: Boolean) {
        _isThereHistory.update { value }
    }

    fun addCurrentCrossMultiplierToPastCrossMultipliers() {
        viewModelScope.launch {
            appRepository.addCurrentCrossMultiplierToPastCrossMultipliers()
        }
    }
}