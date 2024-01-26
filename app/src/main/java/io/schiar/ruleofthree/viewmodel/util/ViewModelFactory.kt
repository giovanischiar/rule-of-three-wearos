package io.schiar.ruleofthree.viewmodel.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.schiar.ruleofthree.model.repository.CrossMultipliersCreatorRepository
import io.schiar.ruleofthree.model.repository.MainRepository
import io.schiar.ruleofthree.viewmodel.AppViewModel
import io.schiar.ruleofthree.viewmodel.CrossMultipliersCreatorViewModel
import io.schiar.ruleofthree.viewmodel.HistoryViewModel

class ViewModelFactory(
    private val mainRepository: MainRepository,
    private val crossMultipliersCreatorRepository: CrossMultipliersCreatorRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            AppViewModel::class.java -> AppViewModel(appRepository = mainRepository)
            CrossMultipliersCreatorViewModel::class.java -> CrossMultipliersCreatorViewModel(
                crossMultipliersCreatorRepository = crossMultipliersCreatorRepository
            )
            HistoryViewModel::class.java -> HistoryViewModel(historyRepository = mainRepository)
            else -> throw IllegalArgumentException("Unknown view model class: ${modelClass.name}")
        } as T
    }
}