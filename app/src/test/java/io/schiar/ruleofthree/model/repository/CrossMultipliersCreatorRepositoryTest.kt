package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierLocalDataSource
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersLocalDataSource
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

    private var crossMultipliersCreatorRepository = CrossMultipliersCreatorRepository(
        currentCrossMultiplierDataSource = CurrentCrossMultiplierLocalDataSource(),
        pastCrossMultipliersDataSource = PastCrossMultipliersLocalDataSource()
    )

    private fun createCrossMultipliersCreatorRepository(
        crossMultiplier: CrossMultiplier? = this.crossMultiplier,
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
    fun `Create Current Cross Multiplier Repository and load Current CrossMultiplier`() = runBlocking {
        // Given
        val expectedCurrentCrossMultiplier = CrossMultiplier()
        val crossMultipliersCreatorRepository = CrossMultipliersCreatorRepository()
        crossMultipliersCreatorRepository.currentCrossMultiplier.first()

        // When
        val actualCurrentCrossMultiplier = crossMultipliersCreatorRepository
            .currentCrossMultiplier
            .first()

        // Then
        assertEquals(expectedCurrentCrossMultiplier, actualCurrentCrossMultiplier)
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

    @Test
    fun `Add Current Cross Multiplier to Past Cross Multipliers and Check if the Current Cross Multiplier was Created`() = runBlocking {
        // Given
        val expectedPastCrossMultipliers = listOf(
            CrossMultiplier(
                valueAt00 = 1,    valueAt01 = 2.3,
                valueAt10 = 45.3, valueAt11 = (45.3*2.3)/1,
                unknownPosition = Pair(1, 1)
            ),
            CrossMultiplier(
                valueAt00 = 98, valueAt01 = 23,
                valueAt10 = 4,  valueAt11 = (4*23)/98.0
            ),
            CrossMultiplier(
                valueAt00 = 94.5, valueAt01 = 28.4,
                valueAt10 = 57,   valueAt11 = (57*28.4)/94.5
            ),
            CrossMultiplier(
                valueAt00 = 3,   valueAt01 = 32.3,
                valueAt10 = 4.6, valueAt11 = (32.3 * 4.6)/3
            )
        )
        val pastCrossMultipliersLocalDataSource = PastCrossMultipliersLocalDataSource(
            crossMultipliersToInsert = expectedPastCrossMultipliers
                .toMutableList().apply { removeFirst() }
        )
        val historyRepository = HistoryRepository(
            pastCrossMultipliersDataSource = pastCrossMultipliersLocalDataSource
        )

        val crossMultipliersCreatorRepository = CrossMultipliersCreatorRepository(
            currentCrossMultiplierDataSource = CurrentCrossMultiplierLocalDataSource(
                currentCrossMultiplierToInsert = expectedPastCrossMultipliers[0]
            ),
            pastCrossMultipliersDataSource = pastCrossMultipliersLocalDataSource
        )
        crossMultipliersCreatorRepository.currentCrossMultiplier.first()

        // When
        crossMultipliersCreatorRepository.addToPastCrossMultipliers()

        // Then
        val actualPastCrossMultipliers = historyRepository.pastCrossMultipliers.first()
        assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }

    @Test
    fun `Add Current Cross Multiplier to Past Cross Multipliers and Check if There are Past Cross Multipliers`() = runBlocking {
        // Given
        val expectedAreTherePastCrossMultipliers = true
        val crossMultipliersCreatorRepository = CrossMultipliersCreatorRepository(
            currentCrossMultiplierDataSource = CurrentCrossMultiplierLocalDataSource(
                currentCrossMultiplierToInsert = CrossMultiplier(
                    valueAt00 = 1,    valueAt01 = 2.3,
                    valueAt10 = 45.3, valueAt11 = (45.3*2.3)/1,
                    unknownPosition = Pair(1, 1)
                )
            ),
            pastCrossMultipliersDataSource = PastCrossMultipliersLocalDataSource()
        )
        crossMultipliersCreatorRepository.currentCrossMultiplier.first()

        // When
        crossMultipliersCreatorRepository.addToPastCrossMultipliers()

        // Then
        val actualAreTherePastCrossMultipliers = crossMultipliersCreatorRepository
            .areTherePastCrossMultipliers
            .first()
        assertEquals(expectedAreTherePastCrossMultipliers, actualAreTherePastCrossMultipliers)
    }
}