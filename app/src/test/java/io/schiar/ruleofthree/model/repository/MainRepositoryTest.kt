package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSource
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class MainRepositoryTest {
    private fun createMainRepository(
        currentCrossMultiplier: CrossMultiplier = CrossMultiplier(),
        pastCrossMultipliers: List<CrossMultiplier> = emptyList()
    ): MainRepository {
        val pastCrossMultipliersDataSource = PastCrossMultipliersDataSource(
            crossMultipliers = pastCrossMultipliers
        )
        val currentCrossMultiplierDataSource = CurrentCrossMultiplierDataSource(
            currentCrossMultiplier = currentCrossMultiplier
        )
        return MainRepository(
            pastCrossMultipliersDataSourceable = pastCrossMultipliersDataSource,
            currentCrossMultiplierDataSourceable = currentCrossMultiplierDataSource
        )
    }

    // AppRepository

    @Test
    fun `Load a Empty List of Past Cross Multipliers and Check if Past Cross Multiplier is Empty`() = runBlocking {
        // Given
        val expectedPastCrossMultipliers = emptyList<CrossMultiplier>()
        var actualPastCrossMultipliers: List<CrossMultiplier>? = null
        val mainRepository = createMainRepository(pastCrossMultipliers = emptyList())
        mainRepository.subscribeForPastCrossMultipliers { actualPastCrossMultipliers = it }

        // When
        mainRepository.loadPastCrossMultipliers()

        // Then
        assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }

    @Test
    fun `Load a Empty List of Past Cross Multipliers and Check if There aren't Past Cross Multipliers`() = runBlocking {
        // Given
        val expectedIsTherePastCrossMultipliers = false
        var actualIsThereIsTherePastCrossMultipliers: Boolean? = null
        val mainRepository = createMainRepository(pastCrossMultipliers = emptyList())
        val callback: (Boolean) -> Unit = { actualIsThereIsTherePastCrossMultipliers = it }
        mainRepository.subscribeForIsTherePastCrossMultipliers(callback)

        // When
        mainRepository.loadPastCrossMultipliers()

        // Then
        assertEquals(expectedIsTherePastCrossMultipliers, actualIsThereIsTherePastCrossMultipliers)
    }

    @Test
    fun `Load a List of Past Cross Multipliers and Check if Past Cross Multipliers Returns The Same List`() = runBlocking {
        // Given
        val expectedPastCrossMultipliers = listOf(
            CrossMultiplier(
                valueAt00 = 3,   valueAt01 = 32.3,
                valueAt10 = 4.6, valueAt11 = (32.3*4.6)/3
            ),
            CrossMultiplier(
                valueAt00 = 94.5, valueAt01 = 28.4,
                valueAt10 = 57,   valueAt11 = (57*28.4)/94.5
            ),
            CrossMultiplier(
                valueAt00 = 98, valueAt01 = 23,
                valueAt10 = 4,  valueAt11 = (4*23)/98.0
            ),
        )
        var actualPastCrossMultipliers: List<CrossMultiplier>? = null
        val mainRepository = createMainRepository(pastCrossMultipliers = expectedPastCrossMultipliers)
        mainRepository.subscribeForPastCrossMultipliers { actualPastCrossMultipliers = it }

        // When
        mainRepository.loadPastCrossMultipliers()

        // Then
        assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }

    @Test
    fun `Load a List of Past Cross Multipliers and Check if There are Past Cross Multipliers`() = runBlocking {
        // Given
        val expectedIsTherePastCrossMultipliers = true
        var actualIsThereIsTherePastCrossMultipliers: Boolean? = null
        val mainRepository = createMainRepository(
            pastCrossMultipliers = listOf(
                CrossMultiplier(
                    valueAt00 = 3,   valueAt01 = 32.3,
                    valueAt10 = 4.6, valueAt11 = (32.3*4.6)/3
                )
            )
        )
        val callback: ((Boolean) -> Unit) = { actualIsThereIsTherePastCrossMultipliers = it }
        mainRepository.subscribeForIsTherePastCrossMultipliers(callback)

        // When
        mainRepository.loadPastCrossMultipliers()

        // Then
        assertEquals(expectedIsTherePastCrossMultipliers, actualIsThereIsTherePastCrossMultipliers)
    }

    @Test
    fun `Add Current Cross Multiplier to Past Cross Multipliers and Check if The Past Cross Multipliers Contains the Current Cross Multiplier`() = runBlocking {
        // Given
        val expectedPastCurrentCrossMultipliers = listOf(
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
                valueAt10 = 4.6, valueAt11 = (32.3*4.6)/3
            ),
        )
        var actualPastCurrentCrossMultipliers: List<CrossMultiplier>? = null
        val mainRepository = createMainRepository(
            currentCrossMultiplier = CrossMultiplier(
                valueAt00 = 1,    valueAt01 = 2.3,
                valueAt10 = 45.3, valueAt11 = (45.3*2.3)/1,
                unknownPosition = Pair(1, 1)
            ),
            pastCrossMultipliers = listOf(
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
                ),
            )
        )
        val callback: (List<CrossMultiplier>) -> Unit = { actualPastCurrentCrossMultipliers = it }
        mainRepository.subscribeForPastCrossMultipliers(callback)

        // When
        mainRepository.addCurrentCrossMultiplierToPastCrossMultipliers()

        // Then
        assertEquals(expectedPastCurrentCrossMultipliers, actualPastCurrentCrossMultipliers)
    }

    @Test
    fun `Add Current Cross Multiplier to Past Cross Multipliers and Check if There are Past Cross Multipliers`() = runBlocking {
        // Given
        val expectedIsTherePastCrossMultipliers = true
        var actualIsThereIsTherePastCrossMultipliers: Boolean? = null
        val mainRepository = createMainRepository(
            currentCrossMultiplier = CrossMultiplier(
                valueAt00 = 1,    valueAt01 = 2.3,
                valueAt10 = 45.3, valueAt11 = (45.3*2.3)/1,
                unknownPosition = Pair(1, 1)
            ),
            pastCrossMultipliers = listOf(
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
                ),
            )
        )
        val callback: ((Boolean) -> Unit) = { actualIsThereIsTherePastCrossMultipliers = it }
        mainRepository.subscribeForIsTherePastCrossMultipliers(callback)

        // When
        mainRepository.addCurrentCrossMultiplierToPastCrossMultipliers()

        // Then
        assertEquals(expectedIsTherePastCrossMultipliers, actualIsThereIsTherePastCrossMultipliers)
    }

    // HistoryRepository

    @Test
    fun `Push Character 5 to Input on Position (1, 0) of the Cross Multiplier at index 2`() = runBlocking {
        // Given
        val expectedPastCrossMultipliers = listOf(
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
                valueAt10 = 4.655, valueAt11 = (32.3*4.655)/3
            )
        )
        var actualPastCrossMultipliers: List<CrossMultiplier>? = null
        val mainRepository = createMainRepository(
            pastCrossMultipliers = listOf(
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
                    valueAt10 = 4.65, valueAt11 = (32.3*4.65)/3
                )
            )
        )
        mainRepository.subscribeForPastCrossMultipliers { actualPastCrossMultipliers = it }

        // When
        mainRepository.pushCharacterToInputOnPositionOfTheCrossMultiplierAt(
            index = 2,
            character = "5",
            position = Pair(1, 0)
        )

        // Then
        assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }

    @Test
    fun `Pop Character From Input on Position (0, 1) of The Cross Multiplier at index 2`() = runBlocking {
        // Given
        val expectedPastCrossMultipliers = listOf(
            CrossMultiplier(
                valueAt00 = 3,   valueAt01 = 32.3,
                valueAt10 = 4.6, valueAt11 = (32.3*4.6)/3
            ),
            CrossMultiplier(
                valueAt00 = 94.5, valueAt01 = 28.4,
                valueAt10 = 57,   valueAt11 = (57*28.4)/94.5
            ),
            CrossMultiplier(
                valueAt00 = 98, valueAt01 = 2.4,
                valueAt10 = 4,  valueAt11 = (4*2.4)/98.0
            ),
        )
        var actualPastCrossMultipliers: List<CrossMultiplier>? = null
        val mainRepository = createMainRepository(
            pastCrossMultipliers = listOf(
                CrossMultiplier(
                    valueAt00 = 3,   valueAt01 = 32.3,
                    valueAt10 = 4.6, valueAt11 = (32.3*4.6)/3
                ),
                CrossMultiplier(
                    valueAt00 = 94.5, valueAt01 = 28.4,
                    valueAt10 = 57,   valueAt11 = (57*28.4)/94.5
                ),
                CrossMultiplier(
                    valueAt00 = 98, valueAt01 = 2.45,
                    valueAt10 = 4,  valueAt11 = (4*2.45)/98.0
                ),
            )
        )
        val callback: ((List<CrossMultiplier>) -> Unit) = { actualPastCrossMultipliers = it }
        mainRepository.subscribeForPastCrossMultipliers(callback)

        // When
        mainRepository.popCharacterOfInputOnPositionOfTheCrossMultiplierAt(
            index = 2,
            position = Pair(0, 1)
        )

        // Then
        assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }

    @Test
    fun `Change the Unknown Position to (0, 1) of the Cross Multiplier at Index 1`() = runBlocking {
        // Given
        val expectedPastCrossMultipliers = listOf(
            CrossMultiplier(
                valueAt00 = 3,   valueAt01 = 32.3,
                valueAt10 = 4.6, valueAt11 = (32.3*4.6)/3
            ),
            CrossMultiplier(
                valueAt00 = "94.5", valueAt01 = "",
                valueAt10 = "57",   valueAt11 = "",
                unknownPosition = Pair(0, 1)
            ),
            CrossMultiplier(
                valueAt00 = 98, valueAt01 = 2,
                valueAt10 = 4,  valueAt11 = (4*2)/98.0
            ),
        )
        var actualPastCrossMultipliers: List<CrossMultiplier>? = null
        val mainRepository = createMainRepository(
            pastCrossMultipliers = listOf(
                CrossMultiplier(
                    valueAt00 = 3,   valueAt01 = 32.3,
                    valueAt10 = 4.6, valueAt11 = (32.3*4.6)/3
                ),
                CrossMultiplier(
                    valueAt00 = 94.5, valueAt01 = 28.4,
                    valueAt10 = 57,   valueAt11 = (57*28.4)/94.5
                ),
                CrossMultiplier(
                    valueAt00 = 98, valueAt01 = 2,
                    valueAt10 = 4,  valueAt11 = (4*2)/98.0
                ),
            )
        )

        val callback: ((List<CrossMultiplier>) -> Unit) = { actualPastCrossMultipliers = it }
        mainRepository.subscribeForPastCrossMultipliers(callback)

        // When
        mainRepository.changeTheUnknownPositionOfTheCrossMultiplierAt(
            index = 1,
            position = Pair(0, 1)
        )

        // Then
        assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }

    @Test
    fun `Clear Input on Position (0, 1) of the Cross Multiplier at Index 0`() = runBlocking {
        // Given
        val expectedPastCrossMultipliers = listOf(
            CrossMultiplier(
                valueAt00 = "3",   valueAt01 = "",
                valueAt10 = "4.6", valueAt11 = ""
            ),
            CrossMultiplier(
                valueAt00 = 94.5, valueAt01 = 28.4,
                valueAt10 = 57,   valueAt11 = (57*28.4)/94.5
            ),
            CrossMultiplier(
                valueAt00 = 98, valueAt01 = 2,
                valueAt10 = 4,  valueAt11 = (4*2)/98.0
            ),
        )
        var actualPastCrossMultipliers: List<CrossMultiplier>? = null
        val mainRepository = createMainRepository(
            pastCrossMultipliers = listOf(
                CrossMultiplier(
                    valueAt00 = 3,   valueAt01 = 32.3,
                    valueAt10 = 4.6, valueAt11 = (32.3*4.6)/3
                ),
                CrossMultiplier(
                    valueAt00 = 94.5, valueAt01 = 28.4,
                    valueAt10 = 57,   valueAt11 = (57*28.4)/94.5
                ),
                CrossMultiplier(
                    valueAt00 = 98, valueAt01 = 2,
                    valueAt10 = 4,  valueAt11 = (4*2)/98.0
                ),
            )
        )
        val callback: ((List<CrossMultiplier>) -> Unit) = { actualPastCrossMultipliers = it }
        mainRepository.subscribeForPastCrossMultipliers(callback)

        // When
        mainRepository.clearInputOnPositionOfTheCrossMultiplierAt(
            index = 0,
            position = Pair(0, 1)
        )

        // Then
        assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }

    @Test
    fun `Delete a Cross Multiplier and Check if The Past Cross Multiplier Was Removed From List`() = runBlocking {
        // Given
        val expectedPastCrossMultipliers = emptyList<CrossMultiplier>()
        var actualPastCrossMultipliers: List<CrossMultiplier>? = null
        val mainRepository = createMainRepository(
            pastCrossMultipliers = listOf(
                CrossMultiplier(
                    valueAt00 = 3,   valueAt01 = 32.3,
                    valueAt10 = 4.6, valueAt11 = (32.3*4.6)/3
                )
            )
        )
        val callback: ((List<CrossMultiplier>) -> Unit) = { actualPastCrossMultipliers = it }
        mainRepository.subscribeForPastCrossMultipliers(callback)

        // When
        mainRepository.deleteCrossMultiplier(index = 0)

        // Then
        assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }

    @Test
    fun `Delete the Only Cross Multiplier and Check if There aren't Past Cross Multipliers`() = runBlocking {
        // Given
        val expectedIsTherePastCrossMultipliers = false
        var actualIsThereIsTherePastCrossMultipliers: Boolean? = null
        val mainRepository = createMainRepository(
            pastCrossMultipliers = listOf(
                CrossMultiplier(
                    valueAt00 = 3,   valueAt01 = 32.3,
                    valueAt10 = 4.6, valueAt11 = (32.3*4.6)/3
                )
            )
        )
        val callback: ((Boolean) -> Unit) = { actualIsThereIsTherePastCrossMultipliers = it }
        mainRepository.subscribeForIsTherePastCrossMultipliers(callback)

        // When
        mainRepository.deleteCrossMultiplier(index = 0)

        // Then
        assertEquals(expectedIsTherePastCrossMultipliers, actualIsThereIsTherePastCrossMultipliers)
    }

    @Test
    fun `Delete History and Check if the Past Cross Multipliers are Empty`() = runBlocking {
        // Given
        val expectedPastCrossMultipliers = emptyList<CrossMultiplier>()
        var actualPastCrossMultipliers: List<CrossMultiplier>? = null
        val mainRepository = createMainRepository(
            pastCrossMultipliers = listOf(
                CrossMultiplier(
                    valueAt00 = 3,   valueAt01 = 32.3,
                    valueAt10 = 4.6, valueAt11 = (32.3*4.6)/3
                )
            )
        )
        val callback: ((List<CrossMultiplier>) -> Unit) = { actualPastCrossMultipliers = it }
        mainRepository.subscribeForPastCrossMultipliers(callback)

        // When
        mainRepository.deleteHistory()

        // Then
        assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }

    @Test
    fun `Delete History and Check if There aren't Past Cross Multipliers`() = runBlocking {
        // Given
        val expectedIsTherePastCrossMultipliers = false
        var actualIsThereIsTherePastCrossMultipliers: Boolean? = null
        val mainRepository = createMainRepository(
            pastCrossMultipliers = listOf(
                CrossMultiplier(
                    valueAt00 = 3,   valueAt01 = 32.3,
                    valueAt10 = 4.6, valueAt11 = (32.3*4.6)/3
                )
            )
        )
        val callback: ((Boolean) -> Unit) = { actualIsThereIsTherePastCrossMultipliers = it }
        mainRepository.subscribeForIsTherePastCrossMultipliers(callback)

        // When
        mainRepository.deleteHistory()

        // Then
        assertEquals(expectedIsTherePastCrossMultipliers, actualIsThereIsTherePastCrossMultipliers)
    }
}