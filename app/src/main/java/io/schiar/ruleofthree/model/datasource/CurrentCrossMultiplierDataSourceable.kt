package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.CrossMultiplier

interface CurrentCrossMultiplierDataSourceable {
    suspend fun retrieveCurrentCrossMultiplier(): CrossMultiplier
    suspend fun updateCurrentCrossMultiplier(crossMultiplierUpdated: CrossMultiplier)
}