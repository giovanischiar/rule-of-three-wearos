package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.CrossMultiplier
import kotlinx.coroutines.flow.Flow

interface CurrentCrossMultiplierDataSource {
    suspend fun create(crossMultiplier: CrossMultiplier)
    fun retrieve(): Flow<CrossMultiplier?>
    suspend fun update(crossMultiplier: CrossMultiplier)
}