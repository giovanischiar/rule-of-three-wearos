package io.schiar.ruleofthree.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.schiar.ruleofthree.model.repository.MainRepository
import io.schiar.ruleofthree.model.repository.PastCrossMultipliersRepository
import kotlinx.coroutines.launch

class PastCrossMultipliersViewModel(
    private val pastCrossMultipliersRepository: PastCrossMultipliersRepository = MainRepository()
): ViewModel() {
    fun pushCharacterToInputOnPositionOfTheCrossMultiplierAt(
        index: Int, character: String, position: Pair<Int, Int>
    ) {
        viewModelScope.launch {
            pastCrossMultipliersRepository.pushCharacterToInputOnPositionOfTheCrossMultiplierAt(
                index = index, character = character, position = position
            )
        }
    }

    fun popCharacterOfInputOnPositionOfTheCrossMultiplierAt(index: Int, position: Pair<Int, Int>) {
        viewModelScope.launch {
            pastCrossMultipliersRepository.popCharacterOfInputOnPositionOfTheCrossMultiplierAt(
                index = index, position = position
            )
        }
    }

    fun clearInputOnPositionOfTheCrossMultiplierAt(index: Int, position: Pair<Int, Int>) {
        viewModelScope.launch {
            pastCrossMultipliersRepository.clearInputOnPositionOfTheCrossMultiplierAt(
                index = index, position = position
            )
        }
    }

    fun changeTheUnknownPositionOfTheCrossMultiplierAt(index: Int, position: Pair<Int, Int>) {
        viewModelScope.launch {
            pastCrossMultipliersRepository.changeTheUnknownPositionOfTheCrossMultiplierAt(
                index = index, position = position
            )
        }
    }
}