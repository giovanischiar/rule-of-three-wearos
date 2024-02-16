package io.schiar.ruleofthree.model.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.schiar.ruleofthree.library.room.CurrentCrossMultiplierRoomDataSource
import io.schiar.ruleofthree.library.room.RuleOfThreeRoomDatabase
import io.schiar.ruleofthree.model.CrossMultiplier
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

class CrossMultipliersCreatorRepositoryTest {
    private lateinit var database: RuleOfThreeRoomDatabase
    private lateinit var crossMultipliersCreatorRepository: CrossMultipliersCreatorRepository
    private val crossMultiplier = CrossMultiplier(valueAt00 = 3, valueAt01 = 4.3)

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context = context,
            klass = RuleOfThreeRoomDatabase::class.java
        ).build()
        crossMultipliersCreatorRepository = CrossMultipliersCreatorRepository(
            currentCrossMultiplierDataSource = CurrentCrossMultiplierRoomDataSource(
                currentCrossMultiplierRoomDAO = database.currentCrossMultiplierDAO()
            ).apply {
                runBlocking { create(crossMultiplier) }
            }
        )
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun `Load Current Cross Multiplier`() = runBlocking {
        // Given
        val expectedCurrentCrossMultiplier = crossMultiplier
        var actualCurrentCrossMultiplier: CrossMultiplier? = null

        // When
        crossMultipliersCreatorRepository.subscribeForCurrentCrossMultipliers {
            actualCurrentCrossMultiplier = it
        }
        crossMultipliersCreatorRepository.loadCurrentCrossMultiplier()

        // Then
        Assert.assertNotNull(actualCurrentCrossMultiplier)
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
        Assert.assertNotNull(actualCurrentCrossMultiplier)
        assertEquals(expectedCurrentCrossMultiplier, actualCurrentCrossMultiplier)
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
        var actualCurrentCrossMultiplier: CrossMultiplier? = null
        crossMultipliersCreatorRepository.subscribeForCurrentCrossMultipliers {
            actualCurrentCrossMultiplier = it
        }
        crossMultipliersCreatorRepository.loadCurrentCrossMultiplier()

        // When
        crossMultipliersCreatorRepository.pushCharacterToInputAt(
            position = positionToAppendValueToInput,
            character = characterToPushToInput
        )

        // Then
        Assert.assertNotNull(actualCurrentCrossMultiplier)
        assertEquals(actualCurrentCrossMultiplier, expectedCurrentCrossMultiplier)
    }

    @Test
    fun `Pop Character Of Input At 0 1`() = runBlocking {
        // Given
        val positionToPopCharacterFromInput = Pair(0, 1)
        val expectedCurrentCrossMultiplier = crossMultiplier.characterPoppedAt(
            position = positionToPopCharacterFromInput
        ).resultCalculated()
        var actualCurrentCrossMultiplier: CrossMultiplier? = null
        crossMultipliersCreatorRepository.subscribeForCurrentCrossMultipliers {
            actualCurrentCrossMultiplier = it
        }
        crossMultipliersCreatorRepository.loadCurrentCrossMultiplier()

        // When
        crossMultipliersCreatorRepository.popCharacterOfInputAt(
            position = positionToPopCharacterFromInput
        )

        // Then
        Assert.assertNotNull(actualCurrentCrossMultiplier)
        assertEquals(actualCurrentCrossMultiplier, expectedCurrentCrossMultiplier)
    }

    @Test
    fun `Change The Unknown Position To 0 1`() = runBlocking {
        // Given
        val newUnknownPosition = Pair(0, 1)
        val expectedCurrentCrossMultiplier = crossMultiplier.unknownPositionChangedTo(
            position = newUnknownPosition
        ).resultCalculated()
        var actualCurrentCrossMultiplier: CrossMultiplier? = null
        crossMultipliersCreatorRepository.subscribeForCurrentCrossMultipliers {
            actualCurrentCrossMultiplier = it
        }
        crossMultipliersCreatorRepository.loadCurrentCrossMultiplier()

        // When
        crossMultipliersCreatorRepository.changeTheUnknownPositionTo(
            position = newUnknownPosition
        )

        // Then
        Assert.assertNotNull(actualCurrentCrossMultiplier)
        assertEquals(actualCurrentCrossMultiplier, expectedCurrentCrossMultiplier)
    }

    @Test
    fun `Clear Input On Position 0 1`() = runBlocking {
        // Given
        val positionToClearInput = Pair(0, 1)
        val expectedCurrentCrossMultiplier = crossMultiplier.inputClearedAt(
            position = positionToClearInput
        ).resultCalculated()
        var actualCurrentCrossMultiplier: CrossMultiplier? = null
        crossMultipliersCreatorRepository.subscribeForCurrentCrossMultipliers {
            actualCurrentCrossMultiplier = it
        }
        crossMultipliersCreatorRepository.loadCurrentCrossMultiplier()

        // When
        crossMultipliersCreatorRepository.clearInputOn(
            position = positionToClearInput
        )

        // Then
        Assert.assertNotNull(actualCurrentCrossMultiplier)
        assertEquals(actualCurrentCrossMultiplier, expectedCurrentCrossMultiplier)
    }

    @Test
    fun `Clear All Inputs`() = runBlocking {
        // Given
        val expectedCurrentCrossMultiplier = crossMultiplier.allInputsCleared().resultCalculated()
        var actualCurrentCrossMultiplier: CrossMultiplier? = null
        crossMultipliersCreatorRepository.subscribeForCurrentCrossMultipliers {
            actualCurrentCrossMultiplier = it
        }
        crossMultipliersCreatorRepository.loadCurrentCrossMultiplier()

        // When
        crossMultipliersCreatorRepository.clearAllInputs()

        // Then
        Assert.assertNotNull(actualCurrentCrossMultiplier)
        assertEquals(actualCurrentCrossMultiplier, expectedCurrentCrossMultiplier)
    }
}