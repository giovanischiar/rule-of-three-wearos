package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.CrossMultiplier

interface DataSource {
    suspend fun requestCurrentCrossMultipliers(): CrossMultiplier
    suspend fun updateCurrentCrossMultiplier(crossMultiplier: CrossMultiplier)
    suspend fun requestAllPastCrossMultipliers(): List<CrossMultiplier>
    suspend fun addToAllPastCrossMultipliers(crossMultiplier: CrossMultiplier)
    suspend fun deleteHistoryItem(index: Int)
}