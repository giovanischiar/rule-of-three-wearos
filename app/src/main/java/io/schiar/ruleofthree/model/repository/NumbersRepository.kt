package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.Numbers

interface NumbersRepository {
    fun subscribeForNumbers(callback: (numbers: Numbers) -> Unit)
    fun subscribeForIsThereHistory(callback: (value: Boolean) -> Unit)
    suspend fun addToInput(value: String, position: Int)
    suspend fun submitToHistory()
    suspend fun removeFromInput(position: Int)
    suspend fun clearInput(position: Int)
    suspend fun clearAllInputs()
}