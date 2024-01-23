package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSource
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSourceable

class CrossMultipliersCreatorRepository(
    private val currentCrossMultiplierDataSourceable: CurrentCrossMultiplierDataSourceable
        = CurrentCrossMultiplierDataSource()
) {
    private var currentCrossMultipliersCallback = { _: CrossMultiplier -> }

    suspend fun loadCurrentCrossMultiplier() {
        val currentCrossMultiplier = currentCrossMultiplierDataSourceable
            .retrieveCurrentCrossMultiplier()
        currentCrossMultipliersCallback(currentCrossMultiplier)
    }

    fun subscribeForCurrentCrossMultipliers(callback: (crossMultiplier: CrossMultiplier) -> Unit) {
        currentCrossMultipliersCallback = callback
    }

    private suspend fun currentCrossMultiplier(): CrossMultiplier {
        return currentCrossMultiplierDataSourceable.retrieveCurrentCrossMultiplier()
    }

    private suspend fun updateCurrentCrossMultiplier(crossMultiplierUpdated: CrossMultiplier) {
        val crossMultiplierUpdatedResultCalculated = crossMultiplierUpdated.resultCalculated()
        currentCrossMultiplierDataSourceable.updateCurrentCrossMultiplier(
            crossMultiplierUpdated = crossMultiplierUpdatedResultCalculated
        )
        currentCrossMultipliersCallback(crossMultiplierUpdatedResultCalculated)
    }

    suspend fun pushCharacterToInputAt(position: Pair<Int, Int>, character: String) {
        val crossMultiplierUpdated = currentCrossMultiplier()
            .characterPushedAt(position = position, character = character)
        updateCurrentCrossMultiplier(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    suspend fun popCharacterOfInputAt(position: Pair<Int, Int>) {
        val crossMultiplierUpdated = currentCrossMultiplier().characterPoppedAt(position = position)
        updateCurrentCrossMultiplier(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    suspend fun changeTheUnknownPositionTo(position: Pair<Int, Int>) {
        val crossMultiplierUpdated = currentCrossMultiplier()
            .unknownPositionChangedTo(position = position)
        updateCurrentCrossMultiplier(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    suspend fun clearInputOn(position: Pair<Int, Int>) {
        val crossMultiplierUpdated = currentCrossMultiplier().inputClearedAt(position = position)
        updateCurrentCrossMultiplier(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    suspend fun clearAllInputs() {
        val crossMultiplierUpdated = currentCrossMultiplier().allInputsCleared()
        updateCurrentCrossMultiplier(crossMultiplierUpdated = crossMultiplierUpdated)
    }
}