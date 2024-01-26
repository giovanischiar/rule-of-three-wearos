package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier

interface HistoryRepository {
    suspend fun loadPastCrossMultipliers()
    fun subscribeForPastCrossMultipliers(
        callback: (allPastCrossMultipliers: List<CrossMultiplier>) -> Unit
    )
    suspend fun pushCharacterToInputOnPositionOfTheCrossMultiplierAt(
        index: Int, character: String, position: Pair<Int, Int>
    )
    suspend fun popCharacterOfInputOnPositionOfTheCrossMultiplierAt(
        index: Int,
        position: Pair<Int, Int>
    )
    suspend fun changeTheUnknownPositionOfTheCrossMultiplierAt(index: Int, position: Pair<Int, Int>)
    suspend fun clearInputOnPositionOfTheCrossMultiplierAt(index: Int, position: Pair<Int, Int>)
    suspend fun deleteCrossMultiplier(index: Int)
    suspend fun deleteHistory()
}