package io.schiar.ruleofthree.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.schiar.ruleofthree.model.repository.AppRepository
import kotlinx.coroutines.launch

class AppViewModel(private val appRepository: AppRepository = AppRepository()): ViewModel() {
    fun addCurrentCrossMultiplierToPastCrossMultipliers() = viewModelScope.launch {
        appRepository.addCurrentCrossMultiplierToPastCrossMultipliers()
    }
}