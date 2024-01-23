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

class HistoryViewModel(
    private val historyRepository: HistoryRepository = MainRepository()
): ViewModel() {
    private var _pastCrossMultipliers = MutableStateFlow(emptyList<CrossMultiplierViewData>())
    val pastCrossMultipliers: StateFlow<List<CrossMultiplierViewData>> = _pastCrossMultipliers

    init { historyRepository.subscribeForPastCrossMultipliers(::onPastCrossMultipliersChanged) }

    suspend fun deleteCrossMultiplier(index: Int) {
        historyRepository.deleteCrossMultiplier(index = index)
    }

    suspend fun deleteHistory() {
        historyRepository.deleteHistory()
    }

    private fun onPastCrossMultipliersChanged(allPastCrossMultipliers: List<CrossMultiplier>) {
        _pastCrossMultipliers.update { allPastCrossMultipliers.map { it.toViewData() } }
    }
}