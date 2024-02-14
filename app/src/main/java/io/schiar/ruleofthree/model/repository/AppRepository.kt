package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSource
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSourceable
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSource
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSourceable
import io.schiar.ruleofthree.model.repository.listener.AreTherePastCrossMultipliersListener
import io.schiar.ruleofthree.model.repository.listener.PastCrossMultipliersListener

class AppRepository(
    private val pastCrossMultipliersDataSourceable: PastCrossMultipliersDataSourceable
        = PastCrossMultipliersDataSource(),
    private val currentCrossMultiplierDataSourceable: CurrentCrossMultiplierDataSourceable
        = CurrentCrossMultiplierDataSource(),
    private val pastCrossMultipliersListener: PastCrossMultipliersListener? = null,
    private val areTherePastCrossMultipliersListener: AreTherePastCrossMultipliersListener? = null
) {
    suspend fun addCurrentCrossMultiplierToPastCrossMultipliers() {
        val crossMultiplierToBeCreated = currentCrossMultiplierDataSourceable
            .retrieveCurrentCrossMultiplier()
            .resultCalculated()
        if (crossMultiplierToBeCreated.isTheResultValid()) {
            pastCrossMultipliersDataSourceable.createPastCrossMultiplier(
                crossMultiplierToBeCreated = crossMultiplierToBeCreated
            )
            val pastCrossMultipliersUpdated =
                pastCrossMultipliersDataSourceable.retrievePastCrossMultipliers()
            pastCrossMultipliersListener?.onPastCrossMultipliersChangedTo(
                newPastCrossMultipliers = pastCrossMultipliersUpdated
            )
            areTherePastCrossMultipliersListener?.onAreTherePastCrossMultipliersChangedTo(
                newAreTherePastCrossMultipliers = true
            )
        }
    }
}