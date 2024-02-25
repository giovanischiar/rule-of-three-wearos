package io.schiar.ruleofthree.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.schiar.ruleofthree.model.repository.CrossMultipliersCreatorRepository
import io.schiar.ruleofthree.viewmodel.util.toViewData
import io.schiar.ruleofthree.viewmodel.viewdata.CrossMultiplierViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CrossMultipliersCreatorViewModel @Inject constructor(
    private val crossMultipliersCreatorRepository: CrossMultipliersCreatorRepository
): ViewModel() {
    constructor(): this(crossMultipliersCreatorRepository = CrossMultipliersCreatorRepository())
    val crossMultiplier: StateFlow<CrossMultiplierViewData> = crossMultipliersCreatorRepository
        .currentCrossMultiplier
        .filterNotNull()
        .map { it.toViewData() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = CrossMultiplierViewData()
        )
    private val _areTherePastCrossMultipliers = MutableStateFlow(value = false)
    val areTherePastCrossMultipliers: StateFlow<Boolean> = _areTherePastCrossMultipliers

    init {
        crossMultipliersCreatorRepository.subscribeForAreTherePastCrossMultipliers(
            ::onAreTherePastCrossMultiplier
        )
    }

    private fun onAreTherePastCrossMultiplier(value: Boolean) {
        _areTherePastCrossMultipliers.update { value }
    }

    fun pushCharacterToInputAt(
        position: Pair<Int, Int>, character: String
    ) = viewModelScope.launch {
        crossMultipliersCreatorRepository.pushCharacterToInputAt(
            position = position, character = character
        )
    }

    fun popCharacterOfInputAt(position: Pair<Int, Int>) = viewModelScope.launch {
        crossMultipliersCreatorRepository.popCharacterOfInputAt(position = position)
    }

    fun changeTheUnknownPositionTo(position: Pair<Int, Int>) = viewModelScope.launch {
        crossMultipliersCreatorRepository.changeTheUnknownPositionTo(position = position)
    }

    fun clearInputOn(position: Pair<Int, Int>) = viewModelScope.launch {
        crossMultipliersCreatorRepository.clearInputOn(position = position)
    }

    fun clearAllInputs() = viewModelScope.launch {
        crossMultipliersCreatorRepository.clearAllInputs()
    }
}