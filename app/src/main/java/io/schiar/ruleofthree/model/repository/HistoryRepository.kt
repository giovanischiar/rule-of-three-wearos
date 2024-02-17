package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSource
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersLocalDataSource
import io.schiar.ruleofthree.model.repository.listener.AreTherePastCrossMultipliersListener
import io.schiar.ruleofthree.model.repository.listener.CrossMultiplierCreatedListener
import kotlin.properties.Delegates

class HistoryRepository(
    private val pastCrossMultipliersDataSource: PastCrossMultipliersDataSource
        = PastCrossMultipliersLocalDataSource(),
    private val areTherePastCrossMultipliersListener: AreTherePastCrossMultipliersListener? = null,
): CrossMultiplierCreatedListener {
    private var pastCrossMultipliersCallback: ((List<CrossMultiplier>) -> Unit)? = null
    private var pastCrossMultipliers: List<CrossMultiplier>? by Delegates.observable(
        initialValue = null
    ) { _, _, newPastCrossMultipliers ->
        if (newPastCrossMultipliers != null) {
            (pastCrossMultipliersCallback ?: {})(newPastCrossMultipliers)
            areTherePastCrossMultipliersListener?.areTherePastCrossMultipliersChangedTo(
                newAreTherePastCrossMultipliers = newPastCrossMultipliers.isNotEmpty()
            )
        }
    }

    constructor(
        crossMultipliers: List<CrossMultiplier>,
    ) : this(
        pastCrossMultipliersDataSource = PastCrossMultipliersLocalDataSource(
            crossMultipliersToInsert = crossMultipliers
        )
    )

    private suspend fun updatePastCrossMultiplierAtDataSource(
        crossMultiplierUpdated: CrossMultiplier
    ) {
        pastCrossMultipliersDataSource.update(
            crossMultiplier = crossMultiplierUpdated.resultCalculated()
        )
    }

    suspend fun loadPastCrossMultipliers() {
        if (pastCrossMultipliers == null) {
            pastCrossMultipliers = pastCrossMultipliersDataSource.retrieve()
        }
    }

    fun subscribeForPastCrossMultipliers(
        callback: (allPastCrossMultipliers: List<CrossMultiplier>) -> Unit
    ) {
        pastCrossMultipliersCallback = callback
    }

    suspend fun pushCharacterToInputOnPositionOfTheCrossMultiplierAt(
        index: Int, position: Pair<Int, Int>, character: String
    ) {
        val pastCrossMultipliers = pastCrossMultipliers ?: return
        val crossMultiplier = pastCrossMultipliers.getOrNull(index = index) ?: return
        val crossMultiplierUpdated = crossMultiplier.characterPushedAt(
            position = position, character = character
        )
        this.pastCrossMultipliers = pastCrossMultipliers.toMutableList().apply {
            this[index] = crossMultiplierUpdated.resultCalculated()
        }
        updatePastCrossMultiplierAtDataSource(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    suspend fun popCharacterOfInputOnPositionOfTheCrossMultiplierAt(
        index: Int, position: Pair<Int, Int>
    ) {
        val pastCrossMultipliers = pastCrossMultipliers ?: return
        val crossMultiplier = pastCrossMultipliers.getOrNull(index = index) ?: return
        val crossMultiplierUpdated = crossMultiplier.characterPoppedAt(position = position)
        this.pastCrossMultipliers = pastCrossMultipliers.toMutableList().apply {
            this[index] = crossMultiplierUpdated.resultCalculated()
        }
        updatePastCrossMultiplierAtDataSource(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    suspend fun changeTheUnknownPositionToPositionOfTheCrossMultiplierAt(
        index: Int, position: Pair<Int, Int>
    ) {
        val pastCrossMultipliers = pastCrossMultipliers ?: return
        val crossMultiplier = pastCrossMultipliers.getOrNull(index = index) ?: return
        val crossMultiplierUpdated = crossMultiplier.unknownPositionChangedTo(position = position)
        this.pastCrossMultipliers = pastCrossMultipliers.toMutableList().apply {
            this[index] = crossMultiplierUpdated.resultCalculated()
        }
        updatePastCrossMultiplierAtDataSource(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    suspend fun clearInputOnPositionOfTheCrossMultiplierAt(
        index: Int, position: Pair<Int, Int>
    ) {
        val pastCrossMultipliers = pastCrossMultipliers ?: return
        val crossMultiplier = pastCrossMultipliers.getOrNull(index = index) ?: return
        val crossMultiplierUpdated = crossMultiplier.inputClearedAt(position = position)
        this.pastCrossMultipliers = pastCrossMultipliers.toMutableList().apply {
            this[index] = crossMultiplierUpdated.resultCalculated()
        }
        updatePastCrossMultiplierAtDataSource(crossMultiplierUpdated = crossMultiplierUpdated)
    }

    suspend fun deleteCrossMultiplierAt(index: Int) {
        val mutablePastCrossMultipliers = (pastCrossMultipliers ?: return).toMutableList()
        val crossMultiplierDeleted = mutablePastCrossMultipliers.removeAt(index = index)
        pastCrossMultipliers = mutablePastCrossMultipliers
        pastCrossMultipliersDataSource.delete(crossMultiplier = crossMultiplierDeleted)
    }

    suspend fun deleteHistory() {
        pastCrossMultipliers = emptyList()
        pastCrossMultipliersDataSource.deleteAll()
    }

    override fun crossMultiplierCreated(crossMultiplier: CrossMultiplier) {
        pastCrossMultipliers = (pastCrossMultipliers ?: return).toMutableList().apply {
            add(index = 0, crossMultiplier)
        }
    }
}