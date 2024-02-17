package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.CrossMultiplier

interface PastCrossMultipliersDataSource {
    suspend fun create(crossMultiplier: CrossMultiplier): CrossMultiplier
    suspend fun retrieve(): List<CrossMultiplier>
    suspend fun update(crossMultiplier: CrossMultiplier)
    suspend fun delete(crossMultiplier: CrossMultiplier)
    suspend fun deleteAll()
}