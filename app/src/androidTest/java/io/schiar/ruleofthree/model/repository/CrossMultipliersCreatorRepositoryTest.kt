package io.schiar.ruleofthree.model.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.schiar.ruleofthree.library.room.CurrentCrossMultiplierRoomDataSource
import io.schiar.ruleofthree.library.room.RuleOfThreeRoomDatabase
import io.schiar.ruleofthree.model.CrossMultiplier
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

class CrossMultipliersCreatorRepositoryTest {
    private lateinit var database: RuleOfThreeRoomDatabase
    private lateinit var crossMultipliersCreatorRepository: CrossMultipliersCreatorRepository
    private val crossMultiplier = CrossMultiplier(valueAt00 = 3, valueAt01 = 4.3)

    @Before
    fun createCrossMultipliersCreatorRepository(): Unit = runBlocking {
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
        crossMultipliersCreatorRepository.currentCrossMultiplier.first()
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

        // When
        val actualCurrentCrossMultiplier = crossMultipliersCreatorRepository
            .currentCrossMultiplier
            .first()

        // Then
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

        // When
        crossMultipliersCreatorRepository.clearAllInputs()

        // Then
        val actualCurrentCrossMultiplier = crossMultipliersCreatorRepository
            .currentCrossMultiplier
            .first()
        assertEquals(expectedCurrentCrossMultiplier, actualCurrentCrossMultiplier)
    }
}