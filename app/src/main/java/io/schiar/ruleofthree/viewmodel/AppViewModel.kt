package io.schiar.ruleofthree.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.schiar.ruleofthree.model.repository.AppRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val appRepository: AppRepository): ViewModel() {
    constructor(): this(appRepository = AppRepository())
    fun addCurrentCrossMultiplierToPastCrossMultipliers() = viewModelScope.launch {
        appRepository.addCurrentCrossMultiplierToPastCrossMultipliers()
    }
}