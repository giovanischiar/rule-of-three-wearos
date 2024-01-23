package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.CrossMultiplier

interface PastCrossMultipliersDataSourceable {
    suspend fun createPastCrossMultiplier(crossMultiplierToBeCreated: CrossMultiplier)
    suspend fun retrievePastCrossMultiplierAt(index: Int): CrossMultiplier?
    suspend fun retrievePastCrossMultipliers(): List<CrossMultiplier>
    suspend fun updatePastCrossMultiplier(crossMultiplierUpdated: CrossMultiplier)
    suspend fun deletePastCrossMultiplierAt(index: Int)
    suspend fun deletePastCrossMultipliers()
}