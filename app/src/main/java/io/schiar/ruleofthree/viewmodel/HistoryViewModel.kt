package io.schiar.ruleofthree.viewmodel

import androidx.lifecycle.ViewModel
import io.schiar.ruleofthree.model.Numbers
import io.schiar.ruleofthree.model.repository.HistoryRepository
import io.schiar.ruleofthree.model.repository.MainRepository
import io.schiar.ruleofthree.view.viewdata.NumbersViewData
import io.schiar.ruleofthree.viewmodel.util.toViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class HistoryViewModel(private val repository: HistoryRepository = MainRepository()): ViewModel() {
    private var _allPastNumbers = MutableStateFlow(repository.requestAllPastNumbers().map {
        it.toViewData()
    })

    fun init() {
        repository.subscribeForAllPastNumbers(::onAllPastNumbersChanged)
    }

    val allPastNumbers: StateFlow<List<NumbersViewData>> = _allPastNumbers

    private fun onAllPastNumbersChanged(allPastNumbers: List<Numbers>) {
        _allPastNumbers.update { allPastNumbers.map { it.toViewData() } }
    }
}