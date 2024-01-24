package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSource
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class MainRepositoryTest {
    private val currentCrossMultiplier = CrossMultiplier(
        valueAt00 = 1, valueAt01 = 2.3, valueAt10 = 45.3, unknownPosition = Pair(1, 1)
    ).resultCalculated()

    private val crossMultipliers = listOf(
        CrossMultiplier(valueAt00 = 3, valueAt01 = 32.3, valueAt10 = 4.6),
        CrossMultiplier(valueAt00 = 94.5, valueAt01 = 28.4, valueAt10 = 57),
        CrossMultiplier(valueAt00 = 98, valueAt01 = 23, valueAt10 = 4),
    )

    private val currentCrossMultiplierDataSource = CurrentCrossMultiplierDataSource(
        currentCrossMultiplier = currentCrossMultiplier
    )

    private val mainRepository = MainRepository(
        pastCrossMultipliersDataSourceable = PastCrossMultipliersDataSource(
            crossMultipliers = crossMultipliers
        ),
        currentCrossMultiplierDataSourceable = currentCrossMultiplierDataSource
    )

    // AppRepository

    @Test
    fun `Load Empty Past Cross Multipliers`() = runBlocking {
        // Given
        val expectedPastCrossMultipliers = emptyList<CrossMultiplier>()
        val expectedIsThereHistory = false
        val mainRepository = MainRepository()
        var actualPastCrossMultipliers: List<CrossMultiplier>? = null
        var actualIsThereHistory: Boolean? = null

        // When
        mainRepository.subscribeForPastCrossMultipliers { actualPastCrossMultipliers = it }
        mainRepository.subscribeForIsTherePastCrossMultipliers { actualIsThereHistory = it }
        mainRepository.loadPastCrossMultipliers()

        // Then
        Assert.assertNotNull(actualPastCrossMultipliers)
        Assert.assertNotNull(actualIsThereHistory)
        Assert.assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
        Assert.assertEquals(expectedIsThereHistory, actualIsThereHistory)
    }

    @Test
    fun `Load Full Past Cross Multipliers`() = runBlocking {
        // Given
        val expectedPastCrossMultipliers = crossMultipliers.reversed()
        val expectedIsThereHistory = expectedPastCrossMultipliers.isNotEmpty()
        var actualPastCrossMultipliers: List<CrossMultiplier>? = null
        var actualIsThereHistory: Boolean? = null

        // When
        mainRepository.subscribeForPastCrossMultipliers { actualPastCrossMultipliers = it }
        mainRepository.subscribeForIsTherePastCrossMultipliers { actualIsThereHistory = it }
        mainRepository.loadPastCrossMultipliers()

        // Then
        Assert.assertNotNull(actualPastCrossMultipliers)
        Assert.assertNotNull(actualIsThereHistory)
        Assert.assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
        Assert.assertEquals(expectedIsThereHistory, actualIsThereHistory)
    }

    @Test
    fun `Subscribe For Is There Histories`() = runBlocking {
        // Given
        val expectedIsThereHistory = true
        var actualIsThereHistory: Boolean? = null

        // When
        mainRepository.subscribeForIsTherePastCrossMultipliers { actualIsThereHistory = it }
        mainRepository.loadPastCrossMultipliers()

        // Then
        Assert.assertNotNull(actualIsThereHistory)
        Assert.assertEquals(expectedIsThereHistory, actualIsThereHistory)
    }

    @Test
    fun `Add Current Cross Multiplier to Past Cross Multipliers`() = runBlocking {
        // Given
        val expectedPastCurrentCrossMultipliers = crossMultipliers.reversed().toMutableList()
        expectedPastCurrentCrossMultipliers.add(index = 0, currentCrossMultiplier)
        var actualPastCurrentCrossMultipliers: List<CrossMultiplier>? = null

        // When
        mainRepository.subscribeForPastCrossMultipliers { actualPastCurrentCrossMultipliers = it }
        mainRepository.addCurrentCrossMultiplierToPastCrossMultipliers()

        // Then
        Assert.assertNotNull(actualPastCurrentCrossMultipliers)
        Assert.assertEquals(expectedPastCurrentCrossMultipliers, actualPastCurrentCrossMultipliers)
    }

    // CrossMultiplierRepository

    @Test
    fun `Append Value to Input`() = runBlocking {
        // Given
        val valueToAppendToInput = "4"
        val indexToAppendValueToInput = 2
        val positionToAppendValueToInput = Pair(0, 1)
        val mutableCrossMultipliers = crossMultipliers.toMutableList()
        val crossMultiplierToAddInput = mutableCrossMultipliers.removeAt(
            index = indexToAppendValueToInput
        )
        crossMultiplierToAddInput.characterPushedAt(
            character = valueToAppendToInput,
            position = positionToAppendValueToInput
        )
        mutableCrossMultipliers.add(index = indexToAppendValueToInput, crossMultiplierToAddInput)
        val expectedPastCrossMultipliers = mutableCrossMultipliers.reversed()
        val mainRepository = MainRepository(
            pastCrossMultipliersDataSourceable = PastCrossMultipliersDataSource(crossMultipliers)
        )
        var actualPastCrossMultipliers: List<CrossMultiplier>? = null

        // When
        mainRepository.subscribeForPastCrossMultipliers { actualPastCrossMultipliers = it }

        mainRepository.pushCharacterToInputOnPositionOfTheCrossMultiplierAt(
            index = indexToAppendValueToInput,
            character = valueToAppendToInput,
            position = positionToAppendValueToInput
        )

        // Then
        Assert.assertNotNull(actualPastCrossMultipliers)
        Assert.assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }

    @Test
    fun `Remove Value From Input`() = runBlocking {
        // Given
        val indexToRemoveValueFromInput = 2
        val positionToAppendValueToInput = Pair(0, 1)
        val mutableCrossMultipliers = crossMultipliers.toMutableList()
        val crossMultiplierToAddInput = mutableCrossMultipliers.removeAt(
            index = indexToRemoveValueFromInput
        )
        crossMultiplierToAddInput.characterPoppedAt(position = positionToAppendValueToInput)
        mutableCrossMultipliers.add(index = indexToRemoveValueFromInput, crossMultiplierToAddInput)
        val expectedPastCrossMultipliers = mutableCrossMultipliers.reversed()
        val mainRepository = MainRepository(
            pastCrossMultipliersDataSourceable = PastCrossMultipliersDataSource(crossMultipliers)
        )
        var actualPastCrossMultipliers: List<CrossMultiplier>? = null

        // When
        mainRepository.subscribeForPastCrossMultipliers { actualPastCrossMultipliers = it }

        mainRepository.popCharacterOfInputOnPositionOfTheCrossMultiplierAt(
            index = indexToRemoveValueFromInput,
            position = positionToAppendValueToInput
        )

        // Then
        Assert.assertNotNull(actualPastCrossMultipliers)
        Assert.assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }

    @Test
    fun `Change Unknown Position`() = runBlocking {
        // Given
        val indexToNewUnknownPosition = 2
        val newUnknownPosition = Pair(0, 1)
        val mutableCrossMultipliers = crossMultipliers.toMutableList()
        val crossMultiplierToAddInput = mutableCrossMultipliers.removeAt(
            index = indexToNewUnknownPosition
        )
        crossMultiplierToAddInput.unknownPositionChangedTo(position = newUnknownPosition)
        mutableCrossMultipliers.add(index = indexToNewUnknownPosition, crossMultiplierToAddInput)
        val expectedPastCrossMultipliers = mutableCrossMultipliers.reversed()
        var actualPastCrossMultipliers: List<CrossMultiplier>? = null

        // When
        mainRepository.subscribeForPastCrossMultipliers { actualPastCrossMultipliers = it }

        mainRepository.changeTheUnknownPositionOfTheCrossMultiplierAt(
            index = indexToNewUnknownPosition,
            position = newUnknownPosition
        )

        // Then
        Assert.assertNotNull(actualPastCrossMultipliers)
        Assert.assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }

    @Test
    fun `Clear Input`() = runBlocking {
        // Given
        val indexToClearInput = 2
        val positionToAppendValueToInput = Pair(0, 1)
        val mutableCrossMultipliers = crossMultipliers.toMutableList()
        val crossMultiplierToAddInput = mutableCrossMultipliers.removeAt(
            index = indexToClearInput
        )
        crossMultiplierToAddInput.inputClearedAt(position = positionToAppendValueToInput)
        mutableCrossMultipliers.add(index = indexToClearInput, crossMultiplierToAddInput)
        val expectedPastCrossMultipliers = mutableCrossMultipliers.reversed()
        var actualPastCrossMultipliers: List<CrossMultiplier>? = null

        // When
        mainRepository.subscribeForPastCrossMultipliers { actualPastCrossMultipliers = it }

        mainRepository.clearInputOnPositionOfTheCrossMultiplierAt(
            index = indexToClearInput,
            position = positionToAppendValueToInput
        )

        // Then
        Assert.assertNotNull(actualPastCrossMultipliers)
        Assert.assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }

    // HistoryRepository

    @Test
    fun `Subscribe For Past Cross Multipliers`() = runBlocking {
        // Given
        val expectedPastCrossMultipliers = crossMultipliers.reversed()
        var actualPastCrossMultiplier: List<CrossMultiplier>? = null

        // When
        mainRepository.subscribeForPastCrossMultipliers { actualPastCrossMultiplier = it }
        mainRepository.loadPastCrossMultipliers()

        // Then
        Assert.assertNotNull(actualPastCrossMultiplier)
        Assert.assertEquals(expectedPastCrossMultipliers, actualPastCrossMultiplier)
    }

    @Test
    fun `Delete Cross Multiplier`() = runBlocking {
        // Given
        val expectedPastCrossMultipliers = crossMultipliers.reversed().toMutableList()
        val indexToDeleteCrossMultiplier = 1
        expectedPastCrossMultipliers.removeAt(index = indexToDeleteCrossMultiplier)
        var actualPastCrossMultiplier: List<CrossMultiplier>? = null

        // When
        mainRepository.subscribeForPastCrossMultipliers { actualPastCrossMultiplier = it }
        mainRepository.deleteCrossMultiplier(index = indexToDeleteCrossMultiplier)

        // Then
        Assert.assertNotNull(actualPastCrossMultiplier)
        Assert.assertEquals(expectedPastCrossMultipliers, actualPastCrossMultiplier)
    }

    @Test
    fun `Delete History`() = runBlocking {
        // Given
        val expectedPastCrossMultipliers = emptyList<CrossMultiplier>()
        var actualPastCrossMultiplier: List<CrossMultiplier>? = null

        // When
        mainRepository.subscribeForPastCrossMultipliers { actualPastCrossMultiplier = it }
        mainRepository.deleteHistory()

        // Then
        Assert.assertNotNull(actualPastCrossMultiplier)
        Assert.assertEquals(expectedPastCrossMultipliers, actualPastCrossMultiplier)
    }
}