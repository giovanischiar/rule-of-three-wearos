package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSource
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSource
import io.schiar.ruleofthree.model.repository.listener.AreTherePastCrossMultipliersListener
import io.schiar.ruleofthree.model.repository.listener.PastCrossMultipliersListener

class AppRepository(
    private val pastCrossMultipliersDataSource: PastCrossMultipliersDataSource
        = PastCrossMultipliersDataSource(),
    private val currentCrossMultiplierDataSource: CurrentCrossMultiplierDataSource
        = CurrentCrossMultiplierDataSource(),
    private val pastCrossMultipliersListener: PastCrossMultipliersListener? = null,
    private val areTherePastCrossMultipliersListener: AreTherePastCrossMultipliersListener? = null
) {
    suspend fun addCurrentCrossMultiplierToPastCrossMultipliers() {
        val crossMultiplierToBeCreated = currentCrossMultiplierDataSource
            .retrieveCurrentCrossMultiplier()
            .resultCalculated()
        if (crossMultiplierToBeCreated.isTheResultValid()) {
            pastCrossMultipliersDataSource.createPastCrossMultiplier(
                crossMultiplierToBeCreated = crossMultiplierToBeCreated
            )
            val pastCrossMultipliersUpdated =
                pastCrossMultipliersDataSource.retrievePastCrossMultipliers()
            pastCrossMultipliersListener?.onPastCrossMultipliersChangedTo(
                newPastCrossMultipliers = pastCrossMultipliersUpdated
            )
            areTherePastCrossMultipliersListener?.onAreTherePastCrossMultipliersChangedTo(
                newAreTherePastCrossMultipliers = true
            )
        }
    }
}