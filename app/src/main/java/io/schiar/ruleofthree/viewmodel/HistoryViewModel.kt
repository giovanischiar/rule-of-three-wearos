package io.schiar.ruleofthree.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.repository.HistoryRepository
import io.schiar.ruleofthree.viewmodel.viewdata.CrossMultiplierViewData
import io.schiar.ruleofthree.viewmodel.util.toViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val historyRepository: HistoryRepository = HistoryRepository()
): ViewModel() {
    private val _pastCrossMultipliers = MutableStateFlow(emptyList<CrossMultiplierViewData>())
    val pastCrossMultipliers: StateFlow<List<CrossMultiplierViewData>> = _pastCrossMultipliers

    constructor(pastCrossMultipliers: List<CrossMultiplierViewData>): this() {
        _pastCrossMultipliers.update { pastCrossMultipliers }
    }

    init {
        historyRepository.subscribeForPastCrossMultipliers(::onPastCrossMultipliersChanged)
        viewModelScope.launch { historyRepository.loadPastCrossMultipliers() }
    }

    private fun onPastCrossMultipliersChanged(allPastCrossMultipliers: List<CrossMultiplier>) {
        _pastCrossMultipliers.update { allPastCrossMultipliers.map { it.toViewData() } }
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
        historyRepository.deleteCrossMultiplier(index = index)
    }

    fun deleteHistory() = viewModelScope.launch {
        historyRepository.deleteHistory()
    }
}