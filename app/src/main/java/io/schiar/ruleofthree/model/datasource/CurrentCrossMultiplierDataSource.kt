package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.CrossMultiplier

interface CurrentCrossMultiplierDataSource {
    suspend fun create(crossMultiplier: CrossMultiplier)
    suspend fun retrieve(): CrossMultiplier?
    suspend fun update(crossMultiplier: CrossMultiplier)
}