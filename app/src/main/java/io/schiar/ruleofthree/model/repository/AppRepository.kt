package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSource
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierLocalDataSource
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSource
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersLocalDataSource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val pastCrossMultipliersDataSource: PastCrossMultipliersDataSource
        = PastCrossMultipliersLocalDataSource(),
    private val currentCrossMultiplierDataSource: CurrentCrossMultiplierDataSource
        = CurrentCrossMultiplierLocalDataSource()
) {
    suspend fun addCurrentCrossMultiplierToPastCrossMultipliers() {
        val currentCrossMultiplier = currentCrossMultiplierDataSource.retrieve().first() ?: return
        val crossMultiplierToBeCreated = currentCrossMultiplier.resultCalculated()
        if (crossMultiplierToBeCreated.isTheResultValid()) {
            pastCrossMultipliersDataSource.create(crossMultiplier = crossMultiplierToBeCreated)
        }
    }
}