package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSource
import io.schiar.ruleofthree.model.repository.listener.AreTherePastCrossMultipliersListener

class CrossMultipliersCreatorRepository(
    private val currentCrossMultiplierDataSource: CurrentCrossMultiplierDataSource
        = CurrentCrossMultiplierDataSource()
): AreTherePastCrossMultipliersListener {
    private var currentCrossMultipliersCallback: ((CrossMultiplier) -> Unit)? = null
    private var areTherePastCrossMultipliersCallback: ((Boolean) -> Unit)? = null

    suspend fun loadCurrentCrossMultiplier() {
        val currentCrossMultiplier = currentCrossMultiplierDataSource
            .retrieveCurrentCrossMultiplier()
        currentCrossMultipliersCallback?.let { it(currentCrossMultiplier) }
    }

    fun subscribeForCurrentCrossMultipliers(callback: (crossMultiplier: CrossMultiplier) -> Unit) {
        currentCrossMultipliersCallback = callback
    }

    fun subscribeForAreTherePastCrossMultipliers(callback: (value: Boolean) -> Unit) {
        areTherePastCrossMultipliersCallback = callback
    }

    private suspend fun retrieveCurrentCrossMultiplierFromDataSource(): CrossMultiplier {
        return currentCrossMultiplierDataSource.retrieveCurrentCrossMultiplier()
    }

    private suspend fun updateCurrentCrossMultiplier(crossMultiplierUpdated: CrossMultiplier) {
        val crossMultiplierUpdatedResultCalculated = crossMultiplierUpdated.resultCalculated()
        currentCrossMultiplierDataSource.updateCurrentCrossMultiplier(
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

    override fun onAreTherePastCrossMultipliersChangedTo(newAreTherePastCrossMultipliers: Boolean) {
        areTherePastCrossMultipliersCallback?.let { it(newAreTherePastCrossMultipliers) }
    }
}