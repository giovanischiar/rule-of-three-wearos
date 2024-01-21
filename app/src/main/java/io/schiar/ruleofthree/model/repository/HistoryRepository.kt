package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier

interface HistoryRepository {
    fun subscribeForAllPastCrossMultipliers(
        callback: (allPastCrossMultipliers: List<CrossMultiplier>) -> Unit
    )
    suspend fun replaceCurrentCrossMultiplier(index: Int)
    suspend fun deleteHistoryItem(index: Int)
    suspend fun deleteHistory()
    suspend fun addToInput(index: Int, value: String, position: Pair<Int, Int>)
    suspend fun submitToHistory()
    suspend fun removeFromInput(index: Int, position: Pair<Int, Int>)
    suspend fun clearInput(index: Int, position: Pair<Int, Int>)
    suspend fun changeUnknownPosition(index: Int, position: Pair<Int, Int>)

}