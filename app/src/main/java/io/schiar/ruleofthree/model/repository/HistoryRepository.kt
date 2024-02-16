package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSource
import io.schiar.ruleofthree.model.repository.listener.AreTherePastCrossMultipliersListener
import io.schiar.ruleofthree.model.repository.listener.PastCrossMultipliersListener

class HistoryRepository(
    private val pastCrossMultipliersDataSource: PastCrossMultipliersDataSource
        = PastCrossMultipliersDataSource(),
    private val areTherePastCrossMultipliersListener: AreTherePastCrossMultipliersListener? = null
): PastCrossMultipliersListener {
    private var pastCrossMultipliersCallback: ((List<CrossMultiplier>) -> Unit)? = null

    private suspend fun retrievePastCrossMultiplierFromDataSourceAt(index: Int): CrossMultiplier? {
        return pastCrossMultipliersDataSource.retrievePastCrossMultiplierAt(index = index)
    }

    private suspend fun retrievePastCrossMultipliersFromDataSource(): List<CrossMultiplier> {
        return pastCrossMultipliersDataSource.retrievePastCrossMultipliers()
    }

    private suspend fun updatePastCrossMultiplierAt(crossMultiplierUpdated: CrossMultiplier) {
        pastCrossMultipliersDataSource.updatePastCrossMultiplier(
            crossMultiplierUpdated = crossMultiplierUpdated.resultCalculated()
        )
        pastCrossMultipliersCallback?.let {
            it(pastCrossMultipliersDataSource.retrievePastCrossMultipliers())
        }
    }

    suspend fun loadPastCrossMultipliers() {
        val pastCrossMultipliers = pastCrossMultipliersDataSource.retrievePastCrossMultipliers()
        onPastCrossMultipliersChangedTo(newPastCrossMultipliers = pastCrossMultipliers)
        areTherePastCrossMultipliersListener?.areTherePastCrossMultipliersChangedTo(
            newAreTherePastCrossMultipliers = pastCrossMultipliers.isNotEmpty()
        )
    }

    fun subscribeForPastCrossMultipliers(
        callback: (allPastCrossMultipliers: List<CrossMultiplier>) -> Unit
    ) {
        pastCrossMultipliersCallback = callback
    }

    suspend fun pushCharacterToInputOnPositionOfTheCrossMultiplierAt(
        index: Int, position: Pair<Int, Int>, character: String
    ) {
        val crossMultiplierUpdated =
            (retrievePastCrossMultiplierFromDataSourceAt(index = index) ?: return)
                .characterPushedAt(position = position, character = character)
        updatePastCrossMultiplierAt(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    suspend fun popCharacterOfInputOnPositionOfTheCrossMultiplierAt(
        index: Int, position: Pair<Int, Int>
    ) {
        val crossMultiplierUpdated =
            (retrievePastCrossMultiplierFromDataSourceAt(index = index) ?: return)
                .characterPoppedAt(position = position)
        updatePastCrossMultiplierAt(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    suspend fun changeTheUnknownPositionToPositionOfTheCrossMultiplierAt(
        index: Int, position: Pair<Int, Int>
    ) {
        val crossMultiplierUpdated =
            (retrievePastCrossMultiplierFromDataSourceAt(index = index) ?: return)
                .unknownPositionChangedTo(position = position)
        updatePastCrossMultiplierAt(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    suspend fun clearInputOnPositionOfTheCrossMultiplierAt(
        index: Int, position: Pair<Int, Int>
    ) {
        val crossMultiplierUpdated =
            (retrievePastCrossMultiplierFromDataSourceAt(index = index) ?: return)
                .inputClearedAt(position = position)
        updatePastCrossMultiplierAt(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    suspend fun deleteCrossMultiplier(index: Int) {
        pastCrossMultipliersDataSource.deletePastCrossMultiplierAt(index = index)
        val pastCrossMultipliers = retrievePastCrossMultipliersFromDataSource()
        pastCrossMultipliersCallback?.let { it(pastCrossMultipliers) }
        areTherePastCrossMultipliersListener?.areTherePastCrossMultipliersChangedTo(
            newAreTherePastCrossMultipliers = pastCrossMultipliers.isNotEmpty()
        )
    }

    suspend fun deleteHistory() {
        pastCrossMultipliersDataSource.deletePastCrossMultipliers()
        val pastCurrentMultipliers = emptyList<CrossMultiplier>()
        pastCrossMultipliersCallback?.let { it(pastCurrentMultipliers) }
        areTherePastCrossMultipliersListener?.areTherePastCrossMultipliersChangedTo(
            newAreTherePastCrossMultipliers = false
        )
    }

    override fun onPastCrossMultipliersChangedTo(newPastCrossMultipliers: List<CrossMultiplier>) {
        pastCrossMultipliersCallback?.let { it(newPastCrossMultipliers) }
    }
}