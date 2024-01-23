package io.schiar.ruleofthree.viewmodel

import androidx.lifecycle.ViewModel
import io.schiar.ruleofthree.model.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class AppViewModel(private val appRepository: AppRepository): ViewModel() {
    private val _isThereHistory = MutableStateFlow(false)
    val isThereHistory: StateFlow<Boolean> = _isThereHistory

    init {
        appRepository.subscribeForIsTherePastCrossMultipliers(::onIsTherePastCrossMultiplier)
    }

    suspend fun loadPastCrossMultipliers() {
        appRepository.loadPastCrossMultipliers()
    }

    suspend fun addCurrentCrossMultiplierToPastCrossMultipliers() {
        appRepository.addCurrentCrossMultiplierToPastCrossMultipliers()
    }

    private fun onIsTherePastCrossMultiplier(value: Boolean) {
        _isThereHistory.update { value }
    }
}