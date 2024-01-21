package io.schiar.ruleofthree.viewmodel

import androidx.lifecycle.ViewModel
import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.repository.HistoryRepository
import io.schiar.ruleofthree.model.repository.MainRepository
import io.schiar.ruleofthree.view.viewdata.CrossMultiplierViewData
import io.schiar.ruleofthree.viewmodel.util.toViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class HistoryViewModel(private val repository: HistoryRepository = MainRepository()): ViewModel() {
    private var _allPastCrossMultipliers = MutableStateFlow(emptyList<CrossMultiplierViewData>())
    val allPastCrossMultipliers: StateFlow<List<CrossMultiplierViewData>> = _allPastCrossMultipliers

    init {
        repository.subscribeForAllPastCrossMultipliers(::onAllPastCrossMultipliersChanged)
    }

    suspend fun replaceCurrentCrossMultiplier(index: Int) {
        repository.replaceCurrentCrossMultiplier(index = index)
    }

    suspend fun deleteHistoryItem(index: Int) {
        repository.deleteHistoryItem(index = index)
    }

    suspend fun deleteHistory() {
        repository.deleteHistory()
    }

    suspend fun addInput(index: Int, value: String, position: Pair<Int, Int>) {
        repository.addToInput(index = index, value = value, position = position)
    }

    suspend fun removeInput(index: Int, position: Pair<Int, Int>) {
        repository.removeFromInput(index = index, position = position)
    }

    suspend fun clearInput(index: Int, position: Pair<Int, Int>) {
        repository.clearInput(index = index, position = position)
    }

    suspend fun changeUnknownPosition(index: Int, position: Pair<Int, Int>) {
        repository.changeUnknownPosition(index = index, position = position)
    }

    private fun onAllPastCrossMultipliersChanged(allPastCrossMultipliers: List<CrossMultiplier>) {
        _allPastCrossMultipliers.update { allPastCrossMultipliers.map { it.toViewData() } }
    }
}