package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val pastCrossMultipliersDataSource: PastCrossMultipliersDataSource
) {
    private var _pastCrossMultipliers: List<CrossMultiplier>? = null
    val pastCrossMultipliers: Flow<List<CrossMultiplier>> =
        pastCrossMultipliersDataSource.retrieve().onEach {
            _pastCrossMultipliers = it
        }

    private suspend fun updatePastCrossMultiplierAtDataSource(
        crossMultiplierUpdated: CrossMultiplier
    ) {
        pastCrossMultipliersDataSource.update(
            crossMultiplier = crossMultiplierUpdated.resultCalculated()
        )
    }

    suspend fun pushCharacterToInputOnPositionOfTheCrossMultiplierAt(
        index: Int, position: Pair<Int, Int>, character: String
    ) {
        val pastCrossMultipliers = _pastCrossMultipliers ?: return
        val crossMultiplier = pastCrossMultipliers.getOrNull(index = index) ?: return
        val crossMultiplierUpdated = crossMultiplier.characterPushedAt(
            position = position, character = character
        )
        updatePastCrossMultiplierAtDataSource(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    suspend fun popCharacterOfInputOnPositionOfTheCrossMultiplierAt(
        index: Int, position: Pair<Int, Int>
    ) {
        val pastCrossMultipliers = _pastCrossMultipliers ?: return
        val crossMultiplier = pastCrossMultipliers.getOrNull(index = index) ?: return
        val crossMultiplierUpdated = crossMultiplier.characterPoppedAt(position = position)
        updatePastCrossMultiplierAtDataSource(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    suspend fun changeTheUnknownPositionToPositionOfTheCrossMultiplierAt(
        index: Int, position: Pair<Int, Int>
    ) {
        val pastCrossMultipliers = _pastCrossMultipliers ?: return
        val crossMultiplier = pastCrossMultipliers.getOrNull(index = index) ?: return
        val crossMultiplierUpdated = crossMultiplier.unknownPositionChangedTo(position = position)
        updatePastCrossMultiplierAtDataSource(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    suspend fun clearInputOnPositionOfTheCrossMultiplierAt(
        index: Int, position: Pair<Int, Int>
    ) {
        val pastCrossMultipliers = _pastCrossMultipliers ?: return
        val crossMultiplier = pastCrossMultipliers.getOrNull(index = index) ?: return
        val crossMultiplierUpdated = crossMultiplier.inputClearedAt(position = position)
        updatePastCrossMultiplierAtDataSource(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    suspend fun deleteCrossMultiplierAt(index: Int) {
        val pastCrossMultipliers = _pastCrossMultipliers ?: return
        val crossMultiplierDeleted = pastCrossMultipliers.getOrNull(index = index) ?: return
        pastCrossMultipliersDataSource.delete(crossMultiplier = crossMultiplierDeleted)
    }

    suspend fun deleteHistory() {
        pastCrossMultipliersDataSource.deleteAll()
    }
}