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
    private val _areTherePastCrossMultipliers = MutableStateFlow(value = false)
    val areTherePastCrossMultipliers: StateFlow<Boolean> = _areTherePastCrossMultipliers

    constructor(areTherePastCrossMultipliers: Boolean): this() { _areTherePastCrossMultipliers.update { areTherePastCrossMultipliers } }

    init {
        appRepository.subscribeForAreTherePastCrossMultipliers(::onIsTherePastCrossMultiplier)
        viewModelScope.launch {
            appRepository.loadPastCrossMultipliers()
        }
    }

    private fun onIsTherePastCrossMultiplier(value: Boolean) {
        _areTherePastCrossMultipliers.update { value }
    }

    fun addCurrentCrossMultiplierToPastCrossMultipliers() = viewModelScope.launch {
        appRepository.addCurrentCrossMultiplierToPastCrossMultipliers()
    }
}