package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSource
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierLocalDataSource
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSource
import io.schiar.ruleofthree.model.repository.listener.AreTherePastCrossMultipliersListener
import io.schiar.ruleofthree.model.repository.listener.PastCrossMultipliersListener
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepository(
    private val pastCrossMultipliersDataSource: PastCrossMultipliersDataSource
        = PastCrossMultipliersDataSource(),
    private val currentCrossMultiplierDataSource: CurrentCrossMultiplierDataSource
        = CurrentCrossMultiplierLocalDataSource(),
    private val pastCrossMultipliersListener: PastCrossMultipliersListener? = null,
    private val areTherePastCrossMultipliersListener: AreTherePastCrossMultipliersListener? = null,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun addCurrentCrossMultiplierToPastCrossMultipliers() {
        val currentCrossMultiplier = withContext(coroutineDispatcher) {
            currentCrossMultiplierDataSource.retrieve()
        } ?: return
        val crossMultiplierToBeCreated = currentCrossMultiplier.resultCalculated()
        if (crossMultiplierToBeCreated.isTheResultValid()) {
            pastCrossMultipliersDataSource.createPastCrossMultiplier(
                crossMultiplierToBeCreated = crossMultiplierToBeCreated
            )
            val pastCrossMultipliersUpdated =
                pastCrossMultipliersDataSource.retrievePastCrossMultipliers()
            pastCrossMultipliersListener?.onPastCrossMultipliersChangedTo(
                newPastCrossMultipliers = pastCrossMultipliersUpdated
            )
            areTherePastCrossMultipliersListener?.areTherePastCrossMultipliersChangedTo(
                newAreTherePastCrossMultipliers = true
            )
        }
    }
}