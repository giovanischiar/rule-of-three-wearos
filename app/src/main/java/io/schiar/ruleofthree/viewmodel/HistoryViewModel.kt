package io.schiar.ruleofthree.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.schiar.ruleofthree.model.repository.HistoryRepository
import io.schiar.ruleofthree.view.history.PastCrossMultipliersUiState
import io.schiar.ruleofthree.viewmodel.util.toViewDataList
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepository: HistoryRepository
): ViewModel() {
    val pastCrossMultipliersUiStateFlow by lazy {
        historyRepository.pastCrossMultipliers.map {
            PastCrossMultipliersUiState.PastCrossMultipliersLoaded(it.toViewDataList())
        }
    }

    fun pushCharacterToInputOnPositionOfTheCrossMultiplierAt(
        index: Int, position: Pair<Int, Int>, character: String
    ) = viewModelScope.launch {
        historyRepository.pushCharacterToInputOnPositionOfTheCrossMultiplierAt(
            index = index, position = position, character = character
        )
    }

    fun popCharacterOfInputOnPositionOfTheCrossMultiplierAt(
        index: Int, position: Pair<Int, Int>
    ) = viewModelScope.launch {
        historyRepository.popCharacterOfInputOnPositionOfTheCrossMultiplierAt(
            index = index, position = position
        )
    }

    fun changeTheUnknownPositionToPositionOfTheCrossMultiplierAt(
        index: Int, position: Pair<Int, Int>
    ) = viewModelScope.launch {
        historyRepository.changeTheUnknownPositionToPositionOfTheCrossMultiplierAt(
            index = index, position = position
        )
    }

    fun clearInputOnPositionOfTheCrossMultiplierAt(
        index: Int, position: Pair<Int, Int>
    ) = viewModelScope.launch {
        historyRepository.clearInputOnPositionOfTheCrossMultiplierAt(
            index = index, position = position
        )
    }

    fun deleteCrossMultiplierAt(index: Int) = viewModelScope.launch {
        historyRepository.deleteCrossMultiplierAt(index = index)
    }

    fun deleteHistory() = viewModelScope.launch {
        historyRepository.deleteHistory()
    }
}