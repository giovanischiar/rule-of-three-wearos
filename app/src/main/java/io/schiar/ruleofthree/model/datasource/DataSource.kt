package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.CrossMultiplier

interface DataSource {
    suspend fun requestCurrentCrossMultiplier(): CrossMultiplier
    suspend fun updateCurrentCrossMultiplier(crossMultiplier: CrossMultiplier)
    suspend fun requestAllPastCrossMultipliers(): List<CrossMultiplier>
    suspend fun addToAllPastCrossMultipliers(crossMultiplier: CrossMultiplier)
    suspend fun replaceCurrentCrossMultiplier(index: Int)
    suspend fun deleteHistoryItem(index: Int)
    suspend fun deleteHistory()
    suspend fun requestCrossMultiplier(index: Int): CrossMultiplier
    suspend fun updateCrossMultiplier(crossMultiplier: CrossMultiplier, index: Int)
}