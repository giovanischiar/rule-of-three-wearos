package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSource
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSourceable

class CrossMultipliersCreatorRepository(
    private val currentCrossMultiplierDataSourceable: CurrentCrossMultiplierDataSourceable
        = CurrentCrossMultiplierDataSource()
) {
    private var currentCrossMultipliersCallback: ((CrossMultiplier) -> Unit)? = null

    suspend fun loadCurrentCrossMultiplier() {
        val currentCrossMultiplier = currentCrossMultiplierDataSourceable
            .retrieveCurrentCrossMultiplier()
        currentCrossMultipliersCallback?.let { it(currentCrossMultiplier) }
    }

    fun subscribeForCurrentCrossMultipliers(callback: (crossMultiplier: CrossMultiplier) -> Unit) {
        currentCrossMultipliersCallback = callback
    }

    private suspend fun retrieveCurrentCrossMultiplierFromDataSource(): CrossMultiplier {
        return currentCrossMultiplierDataSourceable.retrieveCurrentCrossMultiplier()
    }

    private suspend fun updateCurrentCrossMultiplier(crossMultiplierUpdated: CrossMultiplier) {
        val crossMultiplierUpdatedResultCalculated = crossMultiplierUpdated.resultCalculated()
        currentCrossMultiplierDataSourceable.updateCurrentCrossMultiplier(
            crossMultiplierUpdated = crossMultiplierUpdatedResultCalculated
        )
        currentCrossMultipliersCallback?.let { it(crossMultiplierUpdatedResultCalculated) }
    }

    suspend fun pushCharacterToInputAt(position: Pair<Int, Int>, character: String) {
        val crossMultiplierUpdated = retrieveCurrentCrossMultiplierFromDataSource()
            .characterPushedAt(position = position, character = character)
        updateCurrentCrossMultiplier(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    suspend fun popCharacterOfInputAt(position: Pair<Int, Int>) {
        val crossMultiplierUpdated = retrieveCurrentCrossMultiplierFromDataSource()
            .characterPoppedAt(position = position)
        updateCurrentCrossMultiplier(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    suspend fun changeTheUnknownPositionTo(position: Pair<Int, Int>) {
        val crossMultiplierUpdated = retrieveCurrentCrossMultiplierFromDataSource()
            .unknownPositionChangedTo(position = position)
        updateCurrentCrossMultiplier(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    suspend fun clearInputOn(position: Pair<Int, Int>) {
        val crossMultiplierUpdated = retrieveCurrentCrossMultiplierFromDataSource()
            .inputClearedAt(position = position)
        updateCurrentCrossMultiplier(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    suspend fun clearAllInputs() {
        val crossMultiplierUpdated = retrieveCurrentCrossMultiplierFromDataSource()
            .allInputsCleared()
        updateCurrentCrossMultiplier(crossMultiplierUpdated = crossMultiplierUpdated)
    }
}