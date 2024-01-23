package io.schiar.ruleofthree.model.repository

interface PastCrossMultipliersRepository {
    suspend fun pushCharacterToInputOnPositionOfTheCrossMultiplierAt(
        index: Int, character: String, position: Pair<Int, Int>
    )
    suspend fun popCharacterOfInputOnPositionOfTheCrossMultiplierAt(
        index: Int,
        position: Pair<Int, Int>
    )
    suspend fun changeTheUnknownPositionOfTheCrossMultiplierAt(index: Int, position: Pair<Int, Int>)
    suspend fun clearInputOnPositionOfTheCrossMultiplierAt(index: Int, position: Pair<Int, Int>)
}