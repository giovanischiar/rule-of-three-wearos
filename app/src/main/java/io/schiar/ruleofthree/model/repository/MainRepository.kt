package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSource
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSourceable
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSource
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSourceable

class MainRepository(
    private val pastCrossMultipliersDataSourceable: PastCrossMultipliersDataSourceable
        = PastCrossMultipliersDataSource(),
    private val currentCrossMultiplierDataSourceable: CurrentCrossMultiplierDataSourceable
        = CurrentCrossMultiplierDataSource()
): AppRepository, HistoryRepository {
    // AppRepository

    private var areTherePastCrossMultipliersCallback: ((Boolean) -> Unit)? = null

    override suspend fun loadPastCrossMultipliers() {
        val pastCrossMultipliers = pastCrossMultipliersDataSourceable.retrievePastCrossMultipliers()
        pastCrossMultipliersCallback?.let { it(pastCrossMultipliers) }
        areTherePastCrossMultipliersCallback?.let { it(pastCrossMultipliers.isNotEmpty()) }
    }

    override fun subscribeForAreTherePastCrossMultipliers(callback: (value: Boolean) -> Unit) {
        areTherePastCrossMultipliersCallback = callback
    }

    override suspend fun addCurrentCrossMultiplierToPastCrossMultipliers() {
        val crossMultiplierToBeCreated = currentCrossMultiplierDataSourceable
            .retrieveCurrentCrossMultiplier()
            .resultCalculated()
        if (crossMultiplierToBeCreated.isTheResultValid()) {
            pastCrossMultipliersDataSourceable.createPastCrossMultiplier(
                crossMultiplierToBeCreated = crossMultiplierToBeCreated
            )
            loadPastCrossMultipliers()
        }
    }

    // HistoryRepository

    private var pastCrossMultipliersCallback: ((List<CrossMultiplier>) -> Unit)? = null

    private suspend fun retrievePastCrossMultiplierFromDataSourceAt(
        index: Int
    ): CrossMultiplier? {
        return pastCrossMultipliersDataSourceable.retrievePastCrossMultiplierAt(index = index)
    }

    private suspend fun retrievePastCrossMultipliersFromDataSource(): List<CrossMultiplier> {
        return pastCrossMultipliersDataSourceable.retrievePastCrossMultipliers()
    }

    private suspend fun updatePastCrossMultiplierAt(crossMultiplierUpdated: CrossMultiplier) {
        pastCrossMultipliersDataSourceable.updatePastCrossMultiplier(
            crossMultiplierUpdated = crossMultiplierUpdated.resultCalculated()
        )
        pastCrossMultipliersCallback?.let {
            it(pastCrossMultipliersDataSourceable.retrievePastCrossMultipliers())
        }
    }

    override fun subscribeForPastCrossMultipliers(
        callback: (allPastCrossMultipliers: List<CrossMultiplier>) -> Unit
    ) {
        pastCrossMultipliersCallback = callback
    }

    override suspend fun pushCharacterToInputOnPositionOfTheCrossMultiplierAt(
        index: Int, position: Pair<Int, Int>, character: String
    ) {
        val crossMultiplierUpdated =
            (retrievePastCrossMultiplierFromDataSourceAt(index = index) ?: return)
            .characterPushedAt(position = position, character = character)
        updatePastCrossMultiplierAt(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    override suspend fun popCharacterOfInputOnPositionOfTheCrossMultiplierAt(
        index: Int, position: Pair<Int, Int>
    ) {
        val crossMultiplierUpdated =
            (retrievePastCrossMultiplierFromDataSourceAt(index = index) ?: return)
            .characterPoppedAt(position = position)
        updatePastCrossMultiplierAt(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    override suspend fun changeTheUnknownPositionToPositionOfTheCrossMultiplierAt(
        index: Int, position: Pair<Int, Int>
    ) {
        val crossMultiplierUpdated =
            (retrievePastCrossMultiplierFromDataSourceAt(index = index) ?: return)
            .unknownPositionChangedTo(position = position)
        updatePastCrossMultiplierAt(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    override suspend fun clearInputOnPositionOfTheCrossMultiplierAt(
        index: Int, position: Pair<Int, Int>
    ) {
        val crossMultiplierUpdated =
            (retrievePastCrossMultiplierFromDataSourceAt(index = index) ?: return)
            .inputClearedAt(position = position)
        updatePastCrossMultiplierAt(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    override suspend fun deleteCrossMultiplier(index: Int) {
        pastCrossMultipliersDataSourceable.deletePastCrossMultiplierAt(index = index)
        val pastCrossMultipliers = retrievePastCrossMultipliersFromDataSource()
        pastCrossMultipliersCallback?.let { it(pastCrossMultipliers) }
        areTherePastCrossMultipliersCallback?.let { it(pastCrossMultipliers.isNotEmpty())}
    }

    override suspend fun deleteHistory() {
        pastCrossMultipliersDataSourceable.deletePastCrossMultipliers()
        val pastCurrentMultipliers = emptyList<CrossMultiplier>()
        pastCrossMultipliersCallback?.let { it(pastCurrentMultipliers) }
        areTherePastCrossMultipliersCallback?.let { it(false) }
    }
}