package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class CrossMultipliersCreatorRepositoryTest {
    private val crossMultiplier = CrossMultiplier(
        valueAt00 = 6,
        valueAt01 = 45.3,
        valueAt10 = 2.3,
        unknownPosition = Pair(1, 1)
    ).resultCalculated()

    private var crossMultipliersCreatorRepository = CrossMultipliersCreatorRepository()

    @Before
    fun setUp() {
        crossMultipliersCreatorRepository = CrossMultipliersCreatorRepository(
            currentCrossMultiplierDataSourceable = CurrentCrossMultiplierDataSource(
                currentCrossMultiplier = crossMultiplier
            )
        )
    }

    @Test
    fun `Load Cross Multipliers`() = runBlocking {
        // Given
        val expectedCurrentCrossMultiplier = crossMultiplier
        var actualCurrentCrossMultiplier: CrossMultiplier? = null

        // When
        crossMultipliersCreatorRepository.subscribeForCurrentCrossMultipliers {
            actualCurrentCrossMultiplier = it
        }
        crossMultipliersCreatorRepository.loadCurrentCrossMultiplier()

        // Then
        assertNotNull(actualCurrentCrossMultiplier)
        assertEquals(expectedCurrentCrossMultiplier, actualCurrentCrossMultiplier)
    }

    @Test
    fun `Subscribe For Current Cross Multipliers`() = runBlocking {
        // Given
        val expectedCurrentCrossMultiplier = CrossMultiplier()
        val crossMultipliersCreatorRepository = CrossMultipliersCreatorRepository()
        var actualCurrentCrossMultiplier: CrossMultiplier? = null

        // When
        crossMultipliersCreatorRepository
            .subscribeForCurrentCrossMultipliers { actualCurrentCrossMultiplier = it }
        crossMultipliersCreatorRepository.loadCurrentCrossMultiplier()

        // Then
        assertNotNull(actualCurrentCrossMultiplier)
        assertEquals(expectedCurrentCrossMultiplier, actualCurrentCrossMultiplier)
    }

    @Test
    fun `Push Character 4 To Input At Position (0, 1)`() = runBlocking {
        // Given
        val characterToPushToInput = "4"
        val positionToAppendValueToInput = Pair(0, 1)
        val expectedCurrentCrossMultiplier = crossMultiplier.characterPushedAt(
            character = characterToPushToInput,
            position = positionToAppendValueToInput
        ).resultCalculated()
        var actualCurrentCrossMultiplier: CrossMultiplier? = null

        // When
        crossMultipliersCreatorRepository.subscribeForCurrentCrossMultipliers {
            actualCurrentCrossMultiplier = it
        }

        crossMultipliersCreatorRepository.pushCharacterToInputAt(
            position = positionToAppendValueToInput,
            character = characterToPushToInput
        )

        // Then
        assertNotNull(actualCurrentCrossMultiplier)
        assertEquals(actualCurrentCrossMultiplier, expectedCurrentCrossMultiplier)
    }

    @Test
    fun `Pop Character Of Input At (0, 1)`() = runBlocking {
        // Given
        val positionToPopCharacterFromInput = Pair(0, 1)
        val expectedCurrentCrossMultiplier = crossMultiplier.characterPoppedAt(
            position = positionToPopCharacterFromInput
        ).resultCalculated()
        var actualCurrentCrossMultiplier: CrossMultiplier? = null

        // When
        crossMultipliersCreatorRepository.subscribeForCurrentCrossMultipliers {
            actualCurrentCrossMultiplier = it
        }

        crossMultipliersCreatorRepository.popCharacterOfInputAt(
            position = positionToPopCharacterFromInput
        )

        // Then
        assertNotNull(actualCurrentCrossMultiplier)
        assertEquals(actualCurrentCrossMultiplier, expectedCurrentCrossMultiplier)
    }

    @Test
    fun `Change The Unknown Position To (0, 1)`() = runBlocking {
        // Given
        val newUnknownPosition = Pair(0, 1)
        val expectedCurrentCrossMultiplier = crossMultiplier.unknownPositionChangedTo(
            position = newUnknownPosition
        ).resultCalculated()
        var actualCurrentCrossMultiplier: CrossMultiplier? = null

        // When
        crossMultipliersCreatorRepository.subscribeForCurrentCrossMultipliers {
            actualCurrentCrossMultiplier = it
        }

        crossMultipliersCreatorRepository.changeTheUnknownPositionTo(
            position = newUnknownPosition
        )

        // Then
        assertNotNull(actualCurrentCrossMultiplier)
        assertEquals(actualCurrentCrossMultiplier, expectedCurrentCrossMultiplier)
    }

    @Test
    fun `clear Input On Position (0, 1)`() = runBlocking {
        // Given
        val positionToClearInput = Pair(0, 1)
        val expectedCurrentCrossMultiplier = crossMultiplier.inputClearedAt(
            position = positionToClearInput
        ).resultCalculated()
        var actualCurrentCrossMultiplier: CrossMultiplier? = null

        // When
        crossMultipliersCreatorRepository.subscribeForCurrentCrossMultipliers {
            actualCurrentCrossMultiplier = it
        }

        crossMultipliersCreatorRepository.clearInputOn(
            position = positionToClearInput
        )

        // Then
        assertNotNull(actualCurrentCrossMultiplier)
        assertEquals(actualCurrentCrossMultiplier, expectedCurrentCrossMultiplier)
    }

    @Test
    fun `Clear All Inputs`() = runBlocking {
        // Given
        val expectedCurrentCrossMultiplier = crossMultiplier.allInputsCleared().resultCalculated()
        var actualCurrentCrossMultiplier: CrossMultiplier? = null

        // When
        crossMultipliersCreatorRepository.subscribeForCurrentCrossMultipliers {
            actualCurrentCrossMultiplier = it
        }

        crossMultipliersCreatorRepository.clearAllInputs()

        // Then
        assertNotNull(actualCurrentCrossMultiplier)
        assertEquals(actualCurrentCrossMultiplier, expectedCurrentCrossMultiplier)
    }
}