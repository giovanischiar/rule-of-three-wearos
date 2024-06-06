package io.schiar.ruleofthree.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.schiar.ruleofthree.model.repository.CrossMultipliersCreatorRepository
import io.schiar.ruleofthree.view.crossmultiplierscreator.uistate.AreTherePastCrossMultipliersUiState
import io.schiar.ruleofthree.view.crossmultiplierscreator.uistate.CurrentCrossMultiplierUiState
import io.schiar.ruleofthree.viewmodel.util.toViewData
import io.schiar.ruleofthree.viewmodel.viewdata.CrossMultiplierViewData
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CrossMultipliersCreatorViewModel @Inject constructor(
    private val crossMultipliersCreatorRepository: CrossMultipliersCreatorRepository
): ViewModel() {
    val currentCrossMultiplierUiStateFlow by lazy {
        crossMultipliersCreatorRepository.currentCrossMultiplier.map {
            CurrentCrossMultiplierUiState.CurrentCrossMultiplierLoaded(
                crossMultiplier = it?.toViewData() ?: CrossMultiplierViewData()
            )
        }
    }

    val areTherePastCrossMultipliersUiStateFlow by lazy {
        crossMultipliersCreatorRepository.areTherePastCrossMultipliers.map {
            AreTherePastCrossMultipliersUiState.AreThereCrossMultipliersLoaded(it)
        }
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

    fun addToPastCrossMultipliers() = viewModelScope.launch {
        crossMultipliersCreatorRepository.addToPastCrossMultipliers()
    }
}