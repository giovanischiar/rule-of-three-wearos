package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.CrossMultiplier
import kotlinx.coroutines.flow.Flow

interface PastCrossMultipliersDataSource {
    suspend fun create(crossMultiplier: CrossMultiplier): CrossMultiplier
    fun retrieve(): Flow<List<CrossMultiplier>>
    suspend fun update(crossMultiplier: CrossMultiplier)
    suspend fun delete(crossMultiplier: CrossMultiplier)
    suspend fun deleteAll()
}