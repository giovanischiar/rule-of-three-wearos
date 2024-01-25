package io.schiar.ruleofthree.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.repository.HistoryRepository
import io.schiar.ruleofthree.model.repository.MainRepository
import io.schiar.ruleofthree.view.viewdata.CrossMultiplierViewData
import io.schiar.ruleofthree.viewmodel.util.toViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val historyRepository: HistoryRepository = MainRepository()
): ViewModel() {
    private val _pastCrossMultipliers = MutableStateFlow(emptyList<CrossMultiplierViewData>())
    val pastCrossMultipliers: StateFlow<List<CrossMultiplierViewData>> = _pastCrossMultipliers

    constructor(pastCrossMultipliers: List<CrossMultiplierViewData>): this() {
        _pastCrossMultipliers.update { pastCrossMultipliers }
    }

    init {
        historyRepository.subscribeForPastCrossMultipliers(::onPastCrossMultipliersChanged)
    }

    private fun onPastCrossMultipliersChanged(allPastCrossMultipliers: List<CrossMultiplier>) {
        _pastCrossMultipliers.update { allPastCrossMultipliers.map { it.toViewData() } }
    }

    fun deleteCrossMultiplierAt(index: Int) {
        viewModelScope.launch {
            historyRepository.deleteCrossMultiplier(index = index)
        }
    }

    fun deleteHistory() {
        viewModelScope.launch {
            historyRepository.deleteHistory()
        }
    }
}