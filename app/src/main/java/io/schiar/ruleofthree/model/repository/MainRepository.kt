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
): AppRepository, PastCrossMultipliersRepository, HistoryRepository {
    // AppRepository

    private var isThereHistoryCallback = { _: Boolean -> }

    override suspend fun loadPastCrossMultipliers() {
        val pastCrossMultipliers = pastCrossMultipliersDataSourceable.retrievePastCrossMultipliers()
        pastCrossMultipliersCallback(pastCrossMultipliers)
        isThereHistoryCallback(pastCrossMultipliers.isNotEmpty())
    }

    override fun subscribeForIsTherePastCrossMultipliers(callback: (value: Boolean) -> Unit) {
        isThereHistoryCallback = callback
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

    // PastCrossMultipliersRepository

    private suspend fun crossMultiplierAt(index: Int): CrossMultiplier? {
        return pastCrossMultipliersDataSourceable.retrievePastCrossMultiplierAt(index = index)
    }

    private suspend fun updatePastCrossMultiplierAt(crossMultiplierUpdated: CrossMultiplier) {
        pastCrossMultipliersDataSourceable.updatePastCrossMultiplier(
            crossMultiplierUpdated = crossMultiplierUpdated.resultCalculated()
        )
        pastCrossMultipliersCallback(
            pastCrossMultipliersDataSourceable.retrievePastCrossMultipliers()
        )
    }

    override suspend fun pushCharacterToInputOnPositionOfTheCrossMultiplierAt(
        index: Int, character: String, position: Pair<Int, Int>
    ) {
        val crossMultiplierUpdated = (crossMultiplierAt(index = index) ?: return)
            .characterPushedAt(position = position, character = character)
        updatePastCrossMultiplierAt(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    override suspend fun popCharacterOfInputOnPositionOfTheCrossMultiplierAt(
        index: Int, position: Pair<Int, Int>
    ) {
        val crossMultiplierUpdated = (crossMultiplierAt(index = index) ?: return)
            .characterPoppedAt(position = position)
        updatePastCrossMultiplierAt(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    override suspend fun changeTheUnknownPositionOfTheCrossMultiplierAt(
        index: Int, position: Pair<Int, Int>
    ) {
        val crossMultiplierUpdated = (crossMultiplierAt(index = index) ?: return)
            .unknownPositionChangedTo(position = position)
        updatePastCrossMultiplierAt(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    override suspend fun clearInputOnPositionOfTheCrossMultiplierAt(
        index: Int, position: Pair<Int, Int>
    ) {
        val crossMultiplierUpdated = (crossMultiplierAt(index = index) ?: return)
            .inputClearedAt(position = position)
        updatePastCrossMultiplierAt(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    // HistoryRepository
    private var pastCrossMultipliersCallback = { _: List<CrossMultiplier> -> }

    private suspend fun pastCrossMultipliers(): List<CrossMultiplier> {
        return pastCrossMultipliersDataSourceable.retrievePastCrossMultipliers()
    }

    override fun subscribeForPastCrossMultipliers(
        callback: (allPastCrossMultipliers: List<CrossMultiplier>) -> Unit
    ) {
        pastCrossMultipliersCallback = callback
    }

    override suspend fun deleteCrossMultiplier(index: Int) {
        pastCrossMultipliersDataSourceable.deletePastCrossMultiplierAt(index = index)
        val pastCrossMultipliers = pastCrossMultipliers()
        pastCrossMultipliersCallback(pastCrossMultipliers)
        isThereHistoryCallback(pastCrossMultipliers.isNotEmpty())
    }

    override suspend fun deleteHistory() {
        pastCrossMultipliersDataSourceable.deletePastCrossMultipliers()
        val pastCurrentMultipliers = emptyList<CrossMultiplier>()
        pastCrossMultipliersCallback(pastCurrentMultipliers)
        isThereHistoryCallback(false)
    }
}