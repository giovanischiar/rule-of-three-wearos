package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSource
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierLocalDataSource
import io.schiar.ruleofthree.model.repository.listener.AreTherePastCrossMultipliersListener
import kotlin.properties.Delegates

class CrossMultipliersCreatorRepository(
    private val currentCrossMultiplierDataSource: CurrentCrossMultiplierDataSource
        = CurrentCrossMultiplierLocalDataSource()
): AreTherePastCrossMultipliersListener {
    private var currentCrossMultipliersCallback: ((CrossMultiplier) -> Unit)? = null
    private var areTherePastCrossMultipliersCallback: ((Boolean) -> Unit)? = null
    private var currentCrossMultiplier: CrossMultiplier? by Delegates.observable(
        initialValue = null
    ) { _, _, newCurrentCrossMultiplier ->
        if (newCurrentCrossMultiplier != null) {
            (currentCrossMultipliersCallback?: {})(newCurrentCrossMultiplier)
        }
    }

    constructor(currentCrossMultiplier: CrossMultiplier) : this(
        currentCrossMultiplierDataSource = CurrentCrossMultiplierLocalDataSource(
            currentCrossMultiplierToInsert = currentCrossMultiplier
        ),
    )

    suspend fun loadCurrentCrossMultiplier() {
        if (currentCrossMultiplier == null) {
            currentCrossMultiplier =  currentCrossMultiplierDataSource.retrieve()
            if (currentCrossMultiplier == null) {
                currentCrossMultiplier = CrossMultiplier()
                currentCrossMultiplierDataSource.create(
                    crossMultiplier = currentCrossMultiplier ?: return
                )
            }
        }
    }

    fun subscribeForCurrentCrossMultipliers(callback: (crossMultiplier: CrossMultiplier) -> Unit) {
        currentCrossMultipliersCallback = callback
    }

    fun subscribeForAreTherePastCrossMultipliers(callback: (value: Boolean) -> Unit) {
        areTherePastCrossMultipliersCallback = callback
    }

    private suspend fun updateCurrentCrossMultiplier(crossMultiplierUpdated: CrossMultiplier) {
        val crossMultiplierUpdatedResultCalculated = crossMultiplierUpdated.resultCalculated()
        currentCrossMultiplier = crossMultiplierUpdatedResultCalculated
        currentCrossMultiplierDataSource.update(crossMultiplier = crossMultiplierUpdated)
    }

    suspend fun pushCharacterToInputAt(position: Pair<Int, Int>, character: String) {
        val currentCrossMultiplier = currentCrossMultiplier ?: return
        val crossMultiplierUpdated = currentCrossMultiplier
            .characterPushedAt(position = position, character = character)
        updateCurrentCrossMultiplier(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    suspend fun popCharacterOfInputAt(position: Pair<Int, Int>) {
        val currentCrossMultiplier = currentCrossMultiplier ?: return
        val crossMultiplierUpdated = currentCrossMultiplier.characterPoppedAt(position = position)
        updateCurrentCrossMultiplier(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    suspend fun changeTheUnknownPositionTo(position: Pair<Int, Int>) {
        val currentCrossMultiplier = currentCrossMultiplier ?: return
        val crossMultiplierUpdated = currentCrossMultiplier
            .unknownPositionChangedTo(position = position)
        updateCurrentCrossMultiplier(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    suspend fun clearInputOn(position: Pair<Int, Int>) {
        val currentCrossMultiplier = currentCrossMultiplier ?: return
        val crossMultiplierUpdated = currentCrossMultiplier.inputClearedAt(position = position)
        updateCurrentCrossMultiplier(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    suspend fun clearAllInputs() {
        val currentCrossMultiplier = currentCrossMultiplier ?: return
        val crossMultiplierUpdated = currentCrossMultiplier.allInputsCleared()
        updateCurrentCrossMultiplier(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    override fun areTherePastCrossMultipliersChangedTo(newAreTherePastCrossMultipliers: Boolean) {
        areTherePastCrossMultipliersCallback?.let { it(newAreTherePastCrossMultipliers) }
    }
}