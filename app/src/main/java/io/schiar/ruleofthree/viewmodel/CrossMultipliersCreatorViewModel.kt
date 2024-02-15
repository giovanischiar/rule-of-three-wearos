package io.schiar.ruleofthree.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.repository.CrossMultipliersCreatorRepository
import io.schiar.ruleofthree.viewmodel.util.toViewData
import io.schiar.ruleofthree.viewmodel.viewdata.CrossMultiplierViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CrossMultipliersCreatorViewModel(
    private val crossMultipliersCreatorRepository: CrossMultipliersCreatorRepository
        = CrossMultipliersCreatorRepository()
): ViewModel() {
    private val _crossMultiplier = MutableStateFlow(value = CrossMultiplierViewData())
    val crossMultiplier: StateFlow<CrossMultiplierViewData> = _crossMultiplier
    private val _areTherePastCrossMultipliers = MutableStateFlow(value = false)
    val areTherePastCrossMultipliers: StateFlow<Boolean> = _areTherePastCrossMultipliers

    constructor(
        crossMultiplier: CrossMultiplierViewData,
        areTherePastCrossMultipliers: Boolean = false
    ): this() {
        _crossMultiplier.update { crossMultiplier }
        _areTherePastCrossMultipliers.update { areTherePastCrossMultipliers }
    }

    init {
        crossMultipliersCreatorRepository.subscribeForAreTherePastCrossMultipliers(
            ::onIsTherePastCrossMultiplier
        )
        crossMultipliersCreatorRepository
            .subscribeForCurrentCrossMultipliers(::onCurrentCrossMultiplierChanged)
        viewModelScope.launch {
            crossMultipliersCreatorRepository.loadCurrentCrossMultiplier()
        }
    }

    private fun onIsTherePastCrossMultiplier(value: Boolean) {
        _areTherePastCrossMultipliers.update { value }
    }

    private fun onCurrentCrossMultiplierChanged(crossMultiplier: CrossMultiplier) {
        _crossMultiplier.update { crossMultiplier.toViewData() }
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