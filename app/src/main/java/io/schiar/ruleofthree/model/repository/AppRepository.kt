package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSource
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierLocalDataSource
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSource
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersLocalDataSource
import io.schiar.ruleofthree.model.repository.listener.CrossMultiplierCreatedListener

class AppRepository(
    private val pastCrossMultipliersDataSource: PastCrossMultipliersDataSource
        = PastCrossMultipliersLocalDataSource(),
    private val currentCrossMultiplierDataSource: CurrentCrossMultiplierDataSource
        = CurrentCrossMultiplierLocalDataSource(),
    private val crossMultiplierCreatedListener: CrossMultiplierCreatedListener? = null,
) {
    suspend fun addCurrentCrossMultiplierToPastCrossMultipliers() {
        val currentCrossMultiplier = currentCrossMultiplierDataSource.retrieve() ?: return
        val crossMultiplierToBeCreated = currentCrossMultiplier.resultCalculated()
        if (crossMultiplierToBeCreated.isTheResultValid()) {
            val crossMultiplierCreated = pastCrossMultipliersDataSource.create(
                crossMultiplier = crossMultiplierToBeCreated
            )
            crossMultiplierCreatedListener?.crossMultiplierCreated(
                crossMultiplier = crossMultiplierCreated
            )
        }
    }
}