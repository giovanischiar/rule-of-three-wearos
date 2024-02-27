package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSource
import io.schiar.ruleofthree.model.datasource.local.CurrentCrossMultiplierLocalDataSource
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSource
import io.schiar.ruleofthree.model.datasource.local.PastCrossMultipliersLocalDataSource
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CrossMultipliersCreatorRepository @Inject constructor(
    private val currentCrossMultiplierDataSource: CurrentCrossMultiplierDataSource,
    private val pastCrossMultipliersDataSource: PastCrossMultipliersDataSource
) {
    private var _currentCrossMultiplier: CrossMultiplier = CrossMultiplier()
    var currentCrossMultiplier = currentCrossMultiplierDataSource.retrieve()
        .onEach {
            if (it == null) {
                currentCrossMultiplierDataSource.create(crossMultiplier = CrossMultiplier())
            } else {
                _currentCrossMultiplier = it
            }
        }
    var areTherePastCrossMultipliers = pastCrossMultipliersDataSource.retrieve()
        .map { it.isNotEmpty() }

    constructor(
        currentCrossMultiplier: CrossMultiplier? = null,
        pastCrossMultipliers: List<CrossMultiplier> = emptyList()
    ) : this(
        currentCrossMultiplierDataSource = CurrentCrossMultiplierLocalDataSource(
            currentCrossMultiplierToInsert = currentCrossMultiplier
        ),
        pastCrossMultipliersDataSource = PastCrossMultipliersLocalDataSource(
            crossMultipliersToInsert = pastCrossMultipliers
        )
    )

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

    suspend fun addToPastCrossMultipliers() {
        val crossMultiplierToBeCreated = _currentCrossMultiplier.resultCalculated()
        if (crossMultiplierToBeCreated.isTheResultValid()) {
            pastCrossMultipliersDataSource.create(crossMultiplier = crossMultiplierToBeCreated)
        }
    }
}