package io.schiar.ruleofthree.viewmodel.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.schiar.ruleofthree.model.repository.MainRepository
import io.schiar.ruleofthree.viewmodel.AppViewModel
import io.schiar.ruleofthree.viewmodel.HistoryViewModel
import io.schiar.ruleofthree.viewmodel.NumbersViewModel

class ViewModelFactory(private val repository: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            NumbersViewModel::class.java -> NumbersViewModel(repository = repository)
            HistoryViewModel::class.java -> HistoryViewModel(repository = repository)
            AppViewModel::class.java -> AppViewModel(repository = repository)
            else -> throw IllegalArgumentException("Unknown view model class: ${modelClass.name}")
        } as T
    }
}