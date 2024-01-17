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

    private fun onAllPastCrossMultipliersChanged(allPastCrossMultipliers: List<CrossMultiplier>) {
        _allPastCrossMultipliers.update { allPastCrossMultipliers.map { it.toViewData() } }
    }
}