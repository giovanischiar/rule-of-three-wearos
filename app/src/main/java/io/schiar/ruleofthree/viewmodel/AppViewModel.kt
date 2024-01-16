package io.schiar.ruleofthree.viewmodel

import androidx.lifecycle.ViewModel
import io.schiar.ruleofthree.model.repository.AppRepository
import io.schiar.ruleofthree.model.repository.MainRepository

class AppViewModel(private val repository: AppRepository): ViewModel() {
    suspend fun loadDatabase() {
        repository.loadDatabase()
    }
}