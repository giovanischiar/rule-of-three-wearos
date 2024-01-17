package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier

interface CrossMultiplierRepository {
    fun subscribeForCrossMultipliers(callback: (crossMultiplier: CrossMultiplier) -> Unit)
    fun subscribeForIsThereHistories(callback: (value: Boolean) -> Unit)
    suspend fun addToInput(value: String, position: Int)
    suspend fun submitToHistory()
    suspend fun removeFromInput(position: Int)
    suspend fun clearInput(position: Int)
    suspend fun clearAllInputs()
}