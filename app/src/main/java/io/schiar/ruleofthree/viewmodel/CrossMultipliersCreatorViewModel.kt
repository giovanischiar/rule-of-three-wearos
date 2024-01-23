package io.schiar.ruleofthree.viewmodel

import androidx.lifecycle.ViewModel
import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.repository.CrossMultipliersCreatorRepository
import io.schiar.ruleofthree.view.viewdata.CrossMultiplierViewData
import io.schiar.ruleofthree.viewmodel.util.toViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CrossMultipliersCreatorViewModel(
    private val crossMultipliersCreatorRepository: CrossMultipliersCreatorRepository
        = CrossMultipliersCreatorRepository()
): ViewModel() {
    private val _crossMultiplier = MutableStateFlow(CrossMultiplierViewData())
    val crossMultiplier: StateFlow<CrossMultiplierViewData> = _crossMultiplier

    private fun onCurrentCrossMultiplierChanged(crossMultiplier: CrossMultiplier) {
        _crossMultiplier.update { crossMultiplier.toViewData() }
    }

    init {
        crossMultipliersCreatorRepository
            .subscribeForCurrentCrossMultipliers(::onCurrentCrossMultiplierChanged)
    }

    suspend fun loadCurrentCrossMultipliers() {
        crossMultipliersCreatorRepository.loadCurrentCrossMultiplier()
    }

    suspend fun pushCharacterToInputAt(position: Pair<Int, Int>, character: String) {
        crossMultipliersCreatorRepository.pushCharacterToInputAt(
            position = position, character = character
        )
    }

    suspend fun popCharacterOfInputAt(position: Pair<Int, Int>) {
        crossMultipliersCreatorRepository.popCharacterOfInputAt(position = position)
    }

    suspend fun changeTheUnknownPositionTo(position: Pair<Int, Int>) {
        crossMultipliersCreatorRepository.changeTheUnknownPositionTo(position = position)
    }

    suspend fun clearInputOn(position: Pair<Int, Int>) {
       crossMultipliersCreatorRepository.clearInputOn(position = position)
    }

    suspend fun clearAllInputs() {
        crossMultipliersCreatorRepository.clearAllInputs()
    }
}