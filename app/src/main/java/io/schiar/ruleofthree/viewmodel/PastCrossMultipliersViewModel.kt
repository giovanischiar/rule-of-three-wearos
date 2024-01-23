package io.schiar.ruleofthree.viewmodel

import androidx.lifecycle.ViewModel
import io.schiar.ruleofthree.model.repository.MainRepository
import io.schiar.ruleofthree.model.repository.PastCrossMultipliersRepository

class PastCrossMultipliersViewModel(
    private val pastCrossMultipliersRepository: PastCrossMultipliersRepository = MainRepository()
): ViewModel() {
    suspend fun pushCharacterToInputOnPositionOfTheCrossMultiplierAt(
        index: Int, character: String, position: Pair<Int, Int>
    ) {
        pastCrossMultipliersRepository.pushCharacterToInputOnPositionOfTheCrossMultiplierAt(
            index = index, character = character, position = position
        )
    }

    suspend fun popCharacterOfInputOnPositionOfTheCrossMultiplierAt(
        index: Int, position: Pair<Int, Int>
    ) {
        pastCrossMultipliersRepository.popCharacterOfInputOnPositionOfTheCrossMultiplierAt(
            index = index, position = position
        )
    }

    suspend fun clearInputOnPositionOfTheCrossMultiplierAt(index: Int, position: Pair<Int, Int>) {
        pastCrossMultipliersRepository.clearInputOnPositionOfTheCrossMultiplierAt(
            index = index, position = position
        )
    }

    suspend fun changeTheUnknownPositionOfTheCrossMultiplierAt(
        index: Int, position: Pair<Int, Int>
    ) {
        pastCrossMultipliersRepository.changeTheUnknownPositionOfTheCrossMultiplierAt(
            index = index, position = position
        )
    }
}