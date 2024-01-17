package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier

interface HistoryRepository {
    fun subscribeForAllPastCrossMultipliers(
        callback: (allPastCrossMultipliers: List<CrossMultiplier>) -> Unit
    )
    suspend fun deleteHistoryItem(index: Int)
}