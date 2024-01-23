package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier

interface HistoryRepository {
    fun subscribeForPastCrossMultipliers(
        callback: (allPastCrossMultipliers: List<CrossMultiplier>) -> Unit
    )
    suspend fun deleteCrossMultiplier(index: Int)
    suspend fun deleteHistory()
}