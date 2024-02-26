package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class CrossMultipliersCreatorRepositoryTest {
    private val crossMultiplier = CrossMultiplier(
        valueAt00 = 6,
        valueAt01 = 45.3,
        valueAt10 = 2.3,
        unknownPosition = Pair(1, 1)
    ).resultCalculated()

    private var crossMultipliersCreatorRepository = CrossMultipliersCreatorRepository()

    private fun createCrossMultipliersCreatorRepository(
        crossMultiplier: CrossMultiplier = this.crossMultiplier,
        pastCrossMultipliers: List<CrossMultiplier> = emptyList()
    ): Unit = runBlocking {
        crossMultipliersCreatorRepository = CrossMultipliersCreatorRepository(
            currentCrossMultiplier = crossMultiplier,
            pastCrossMultipliers = pastCrossMultipliers
        )
        crossMultipliersCreatorRepository.currentCrossMultiplier.first()
        crossMultipliersCreatorRepository.areTherePastCrossMultipliers.first()
    }

    @Test
    fun `Load Current Cross Multiplier`() = runBlocking {
        // Given
        val expectedCurrentCrossMultiplier = crossMultiplier
        createCrossMultipliersCreatorRepository()

        // When
        val actualCurrentCrossMultiplier = crossMultipliersCreatorRepository
            .currentCrossMultiplier
            .first()

        // Then
        assertEquals(expectedCurrentCrossMultiplier, actualCurrentCrossMultiplier)
    }

    @Test
    fun `Load empty List of Past Cross Multipliers and Check if There are not Past Cross Multipliers`() = runBlocking {
        // Given
        val expectedAreTherePastCrossMultipliers = false
        createCrossMultipliersCreatorRepository()
        val actualAreTherePastCrossMultipliers: Boolean = crossMultipliersCreatorRepository
            .areTherePastCrossMultipliers
            .first()

        // Then
        assertEquals(expectedAreTherePastCrossMultipliers, actualAreTherePastCrossMultipliers)
    }

    @Test
    fun `Load a List of Past Cross Multipliers and Check if There are Past Cross Multipliers`() = runBlocking {
        // Given
        val expectedAreTherePastCrossMultipliers = true
        createCrossMultipliersCreatorRepository(pastCrossMultipliers = listOf(CrossMultiplier()))
        val actualAreTherePastCrossMultipliers: Boolean = crossMultipliersCreatorRepository
            .areTherePastCrossMultipliers
            .first()

        // Then
        assertEquals(expectedAreTherePastCrossMultipliers, actualAreTherePastCrossMultipliers)
    }

    @Test
    fun `Push Character 4 To Input At Position 0 1`() = runBlocking {
        // Given
        val characterToPushToInput = "4"
        val positionToAppendValueToInput = Pair(0, 1)
        val expectedCurrentCrossMultiplier = crossMultiplier.characterPushedAt(
            character = characterToPushToInput,
            position = positionToAppendValueToInput
        ).resultCalculated()
        createCrossMultipliersCreatorRepository()

        // When
        crossMultipliersCreatorRepository.pushCharacterToInputAt(
            position = positionToAppendValueToInput,
            character = characterToPushToInput
        )

        // Then
        val actualCurrentCrossMultiplier = crossMultipliersCreatorRepository
            .currentCrossMultiplier
            .first()
        assertEquals(expectedCurrentCrossMultiplier, actualCurrentCrossMultiplier)
    }

    @Test
    fun `Pop Character Of Input At 0 1`() = runBlocking {
        // Given
        val positionToPopCharacterFromInput = Pair(0, 1)
        val expectedCurrentCrossMultiplier = crossMultiplier.characterPoppedAt(
            position = positionToPopCharacterFromInput
        ).resultCalculated()
        createCrossMultipliersCreatorRepository()

        // When
        crossMultipliersCreatorRepository.popCharacterOfInputAt(
            position = positionToPopCharacterFromInput
        )

        // Then
        val actualCurrentCrossMultiplier = crossMultipliersCreatorRepository
            .currentCrossMultiplier
            .first()
        assertEquals(expectedCurrentCrossMultiplier, actualCurrentCrossMultiplier)
    }

    @Test
    fun `Change The Unknown Position To 0 1`() = runBlocking {
        // Given
        val newUnknownPosition = Pair(0, 1)
        val expectedCurrentCrossMultiplier = crossMultiplier.unknownPositionChangedTo(
            position = newUnknownPosition
        ).resultCalculated()
        createCrossMultipliersCreatorRepository()

        // When
        crossMultipliersCreatorRepository.changeTheUnknownPositionTo(
            position = newUnknownPosition
        )

        // Then
        val actualCurrentCrossMultiplier = crossMultipliersCreatorRepository
            .currentCrossMultiplier
            .first()
        assertEquals(expectedCurrentCrossMultiplier, actualCurrentCrossMultiplier)
    }

    @Test
    fun `Clear Input On Position 0 1`() = runBlocking {
        // Given
        val positionToClearInput = Pair(0, 1)
        val expectedCurrentCrossMultiplier = crossMultiplier.inputClearedAt(
            position = positionToClearInput
        ).resultCalculated()
        createCrossMultipliersCreatorRepository()

        // When
        crossMultipliersCreatorRepository.clearInputOn(
            position = positionToClearInput
        )

        // Then
        val actualCurrentCrossMultiplier = crossMultipliersCreatorRepository
            .currentCrossMultiplier
            .first()
        assertEquals(expectedCurrentCrossMultiplier, actualCurrentCrossMultiplier)
    }

    @Test
    fun `Clear All Inputs`() = runBlocking {
        // Given
        val expectedCurrentCrossMultiplier = crossMultiplier.allInputsCleared().resultCalculated()
        createCrossMultipliersCreatorRepository()

        // When
        crossMultipliersCreatorRepository.clearAllInputs()

        // Then
        val actualCurrentCrossMultiplier = crossMultipliersCreatorRepository
            .currentCrossMultiplier
            .first()
        assertEquals(expectedCurrentCrossMultiplier, actualCurrentCrossMultiplier)
    }
}