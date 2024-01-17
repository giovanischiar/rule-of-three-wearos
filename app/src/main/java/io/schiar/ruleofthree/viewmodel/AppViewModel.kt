package io.schiar.ruleofthree.viewmodel

import androidx.lifecycle.ViewModel
import io.schiar.ruleofthree.model.repository.AppRepository

class AppViewModel(private val repository: AppRepository): ViewModel() {
    suspend fun loadDatabase() {
        repository.loadDatabase()
    }
}