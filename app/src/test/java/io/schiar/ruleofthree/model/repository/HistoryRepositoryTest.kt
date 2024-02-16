package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSource
import io.schiar.ruleofthree.model.repository.listener.AreTherePastCrossMultipliersListener
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class HistoryRepositoryTest {
    private fun createHistoryRepository(
        pastCrossMultipliers: List<CrossMultiplier> = emptyList(),
        onNewAreTherePastCrossMultipliers: (Boolean) -> Unit = {}
    ): HistoryRepository {
        val pastCrossMultipliersDataSource = PastCrossMultipliersDataSource(
            crossMultipliers = pastCrossMultipliers
        )
        return HistoryRepository(
            pastCrossMultipliersDataSource = pastCrossMultipliersDataSource,
            areTherePastCrossMultipliersListener = object : AreTherePastCrossMultipliersListener {
                override fun areTherePastCrossMultipliersChangedTo(
                    newAreTherePastCrossMultipliers: Boolean
                ) {
                    onNewAreTherePastCrossMultipliers(newAreTherePastCrossMultipliers)
                }
            }
        )
    }

    @Test
    fun `Load a Empty List of Past Cross Multipliers and Check if Past Cross Multiplier is Empty`() = runBlocking {
        // Given
        val expectedPastCrossMultipliers = emptyList<CrossMultiplier>()
        var actualPastCrossMultipliers: List<CrossMultiplier>? = null
        val appRepository = createHistoryRepository(pastCrossMultipliers = emptyList())
        appRepository.subscribeForPastCrossMultipliers { actualPastCrossMultipliers = it }

        // When
        appRepository.loadPastCrossMultipliers()

        // Then
        Assert.assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }

    @Test
    fun `Load a Empty List of Past Cross Multipliers and Check if There aren't Past Cross Multipliers`() = runBlocking {
        // Given
        val expectedAreTherePastCrossMultipliers = false
        var actualAreTherePastCrossMultipliers: Boolean? = null
        val callback: (Boolean) -> Unit = { actualAreTherePastCrossMultipliers = it }
        val historyRepository = createHistoryRepository(
            pastCrossMultipliers = emptyList(),
            onNewAreTherePastCrossMultipliers = callback
        )

        // When
        historyRepository.loadPastCrossMultipliers()

        // Then
        Assert.assertEquals(
            expectedAreTherePastCrossMultipliers,
            actualAreTherePastCrossMultipliers
        )
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
        val historyRepository = createHistoryRepository(
            pastCrossMultipliers = expectedPastCrossMultipliers
        )
        historyRepository.subscribeForPastCrossMultipliers { actualPastCrossMultipliers = it }

        // When
        historyRepository.loadPastCrossMultipliers()

        // Then
        Assert.assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }

    @Test
    fun `Load a List of Past Cross Multipliers and Check if There are Past Cross Multipliers`() = runBlocking {
        // Given
        val expectedAreTherePastCrossMultipliers = true
        var actualAreTherePastCrossMultipliers: Boolean? = null
        val callback: ((Boolean) -> Unit) = { actualAreTherePastCrossMultipliers = it }
        val mainRepository = createHistoryRepository(
            pastCrossMultipliers = listOf(
                CrossMultiplier(
                    valueAt00 = 3,   valueAt01 = 32.3,
                    valueAt10 = 4.6, valueAt11 = (32.3*4.6)/3
                )
            ),
            onNewAreTherePastCrossMultipliers = callback
        )

        // When
        mainRepository.loadPastCrossMultipliers()

        // Then
        Assert.assertEquals(
            expectedAreTherePastCrossMultipliers,
            actualAreTherePastCrossMultipliers
        )
    }

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
        val historyRepository = createHistoryRepository(
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
        historyRepository.subscribeForPastCrossMultipliers { actualPastCrossMultipliers = it }

        // When
        historyRepository.pushCharacterToInputOnPositionOfTheCrossMultiplierAt(
            index = 2,
            position = Pair(1, 0),
            character = "5"
        )

        // Then
        Assert.assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
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
        val historyRepository = createHistoryRepository(
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
        historyRepository.subscribeForPastCrossMultipliers(callback)

        // When
        historyRepository.popCharacterOfInputOnPositionOfTheCrossMultiplierAt(
            index = 2,
            position = Pair(0, 1)
        )

        // Then
        Assert.assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
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
        val historyRepository = createHistoryRepository(
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
        historyRepository.subscribeForPastCrossMultipliers(callback)

        // When
        historyRepository.changeTheUnknownPositionToPositionOfTheCrossMultiplierAt(
            index = 1,
            position = Pair(0, 1)
        )

        // Then
        Assert.assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
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
        val historyRepository = createHistoryRepository(
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
        historyRepository.subscribeForPastCrossMultipliers(callback)

        // When
        historyRepository.clearInputOnPositionOfTheCrossMultiplierAt(
            index = 0,
            position = Pair(0, 1)
        )

        // Then
        Assert.assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }

    @Test
    fun `Delete a Cross Multiplier and Check if the Past Cross Multiplier Was Removed From List`() = runBlocking {
        // Given
        val expectedPastCrossMultipliers = emptyList<CrossMultiplier>()
        var actualPastCrossMultipliers: List<CrossMultiplier>? = null
        val historyRepository = createHistoryRepository(
            pastCrossMultipliers = listOf(
                CrossMultiplier(
                    valueAt00 = 3,   valueAt01 = 32.3,
                    valueAt10 = 4.6, valueAt11 = (32.3*4.6)/3
                )
            )
        )
        val callback: ((List<CrossMultiplier>) -> Unit) = { actualPastCrossMultipliers = it }
        historyRepository.subscribeForPastCrossMultipliers(callback)

        // When
        historyRepository.deleteCrossMultiplier(index = 0)

        // Then
        Assert.assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }

    @Test
    fun `Delete the Only Cross Multiplier and Check if There aren't Past Cross Multipliers`() = runBlocking {
        // Given
        val expectedAreTherePastCrossMultipliers = false
        var actualAreTherePastCrossMultipliers: Boolean? = null
        val callback: ((Boolean) -> Unit) = { actualAreTherePastCrossMultipliers = it }
        val historyRepository = createHistoryRepository(
            pastCrossMultipliers = listOf(
                CrossMultiplier(
                    valueAt00 = 3,   valueAt01 = 32.3,
                    valueAt10 = 4.6, valueAt11 = (32.3*4.6)/3
                )
            ),
            onNewAreTherePastCrossMultipliers = callback
        )

        // When
        historyRepository.deleteCrossMultiplier(index = 0)

        // Then
        Assert.assertEquals(
            expectedAreTherePastCrossMultipliers,
            actualAreTherePastCrossMultipliers
        )
    }

    @Test
    fun `Delete History and Check if the Past Cross Multipliers Are Empty`() = runBlocking {
        // Given
        val expectedPastCrossMultipliers = emptyList<CrossMultiplier>()
        var actualPastCrossMultipliers: List<CrossMultiplier>? = null
        val historyRepository = createHistoryRepository(
            pastCrossMultipliers = listOf(
                CrossMultiplier(
                    valueAt00 = 3,   valueAt01 = 32.3,
                    valueAt10 = 4.6, valueAt11 = (32.3*4.6)/3
                )
            )
        )
        val callback: ((List<CrossMultiplier>) -> Unit) = { actualPastCrossMultipliers = it }
        historyRepository.subscribeForPastCrossMultipliers(callback)

        // When
        historyRepository.deleteHistory()

        // Then
        Assert.assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }

    @Test
    fun `Delete History and Check if There Aren't Past Cross Multipliers`() = runBlocking {
        // Given
        val expectedAreTherePastCrossMultipliers = false
        var actualAreTherePastCrossMultipliers: Boolean? = null
        val callback: ((Boolean) -> Unit) = { actualAreTherePastCrossMultipliers = it }
        val historyRepository = createHistoryRepository(
            pastCrossMultipliers = listOf(
                CrossMultiplier(
                    valueAt00 = 3,   valueAt01 = 32.3,
                    valueAt10 = 4.6, valueAt11 = (32.3*4.6)/3
                )
            ),
            onNewAreTherePastCrossMultipliers = callback
        )

        // When
        historyRepository.deleteHistory()

        // Then
        Assert.assertEquals(
            expectedAreTherePastCrossMultipliers,
            actualAreTherePastCrossMultipliers
        )
    }
}