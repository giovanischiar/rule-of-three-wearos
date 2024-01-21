package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier

interface CrossMultiplierRepository {
    fun subscribeForCrossMultipliers(callback: (crossMultiplier: CrossMultiplier) -> Unit)
    fun subscribeForIsThereHistories(callback: (value: Boolean) -> Unit)
    suspend fun addToInput(value: String, position: Pair<Int, Int>)
    suspend fun submitToHistory()
    suspend fun removeFromInput(position: Pair<Int, Int>)
    suspend fun clearInput(position: Pair<Int, Int>)
    suspend fun changeUnknownPosition(position: Pair<Int, Int>)
    suspend fun clearAllInputs()
}