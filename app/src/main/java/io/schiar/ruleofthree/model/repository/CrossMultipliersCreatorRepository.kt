package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSource
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierLocalDataSource
import io.schiar.ruleofthree.model.repository.listener.AreTherePastCrossMultipliersListener
import kotlinx.coroutines.flow.onEach

class CrossMultipliersCreatorRepository(
    private val currentCrossMultiplierDataSource: CurrentCrossMultiplierDataSource
        = CurrentCrossMultiplierLocalDataSource()
): AreTherePastCrossMultipliersListener {
    private var areTherePastCrossMultipliersCallback: ((Boolean) -> Unit)? = null
    private var _currentCrossMultiplier: CrossMultiplier = CrossMultiplier()
    var currentCrossMultiplier = currentCrossMultiplierDataSource.retrieve()
        .onEach {
            if (it == null) {
                currentCrossMultiplierDataSource.create(crossMultiplier = CrossMultiplier())
            } else {
                _currentCrossMultiplier = it
            }
        }

    constructor(currentCrossMultiplier: CrossMultiplier) : this(
        currentCrossMultiplierDataSource = CurrentCrossMultiplierLocalDataSource(
            currentCrossMultiplierToInsert = currentCrossMultiplier
        )
    )

    fun subscribeForAreTherePastCrossMultipliers(callback: (value: Boolean) -> Unit) {
        areTherePastCrossMultipliersCallback = callback
    }

    suspend fun pushCharacterToInputAt(position: Pair<Int, Int>, character: String) {
        currentCrossMultiplierDataSource.update(
            crossMultiplier = _currentCrossMultiplier
                .characterPushedAt(position = position, character = character)
                .resultCalculated()
        )
    }

    suspend fun popCharacterOfInputAt(position: Pair<Int, Int>) {
        currentCrossMultiplierDataSource.update(
            crossMultiplier = _currentCrossMultiplier
                .characterPoppedAt(position = position)
                .resultCalculated()
        )
    }

    suspend fun changeTheUnknownPositionTo(position: Pair<Int, Int>) {
        currentCrossMultiplierDataSource.update(
            crossMultiplier = _currentCrossMultiplier
                .unknownPositionChangedTo(position = position)
                .resultCalculated()
        )
    }

    suspend fun clearInputOn(position: Pair<Int, Int>) {
        currentCrossMultiplierDataSource.update(
            _currentCrossMultiplier.inputClearedAt(position = position).resultCalculated()
        )
    }

    suspend fun clearAllInputs() {
        currentCrossMultiplierDataSource.update(
            crossMultiplier = _currentCrossMultiplier.allInputsCleared().resultCalculated()
        )
    }

    override fun areTherePastCrossMultipliersChangedTo(newAreTherePastCrossMultipliers: Boolean) {
        areTherePastCrossMultipliersCallback?.let { it(newAreTherePastCrossMultipliers) }
    }
}