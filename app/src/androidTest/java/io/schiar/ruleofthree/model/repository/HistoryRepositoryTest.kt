package io.schiar.ruleofthree.model.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.schiar.ruleofthree.library.room.PastCrossMultipliersRoomDataSource
import io.schiar.ruleofthree.library.room.RuleOfThreeRoomDatabase
import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.repository.listener.AreTherePastCrossMultipliersListener
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Test
import java.io.IOException

class HistoryRepositoryTest {
    private lateinit var database: RuleOfThreeRoomDatabase
    private lateinit var historyRepository: HistoryRepository

    private suspend fun createHistoryRepository(
        pastCrossMultipliers: List<CrossMultiplier> = emptyList(),
        onNewAreTherePastCrossMultipliers: (Boolean) -> Unit = {}
    ) {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context = context,
            klass = RuleOfThreeRoomDatabase::class.java
        ).build()
        historyRepository = HistoryRepository(
            pastCrossMultipliersDataSource = PastCrossMultipliersRoomDataSource(
                pastCrossMultipliersRoomDAO = database.pastCrossMultipliersDAO()
            ).apply {
                for (crossMultiplier in pastCrossMultipliers.reversed()) {
                    create(crossMultiplier)
                }
            },
            areTherePastCrossMultipliersListener = object : AreTherePastCrossMultipliersListener {
                override fun areTherePastCrossMultipliersChangedTo(
                    newAreTherePastCrossMultipliers: Boolean
                ) {
                    onNewAreTherePastCrossMultipliers(newAreTherePastCrossMultipliers)
                }
            }
        )
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun `Load a Empty List of Past Cross Multipliers and Check if Past Cross Multiplier is Empty`() = runBlocking {
        // Given
        val expectedPastCrossMultipliers = emptyList<CrossMultiplier>()
        var actualPastCrossMultipliers: List<CrossMultiplier>? = null
        createHistoryRepository(pastCrossMultipliers = emptyList())
        historyRepository.subscribeForPastCrossMultipliers { actualPastCrossMultipliers = it }
        historyRepository.loadPastCrossMultipliers()

        // When
        historyRepository.loadPastCrossMultipliers()

        // Then
        Assert.assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }

    @Test
    fun `Load a Empty List of Past Cross Multipliers and Check if There are not Past Cross Multipliers`() = runBlocking {
        // Given
        val expectedAreTherePastCrossMultipliers = false
        var actualAreTherePastCrossMultipliers: Boolean? = null
        val callback: (Boolean) -> Unit = { actualAreTherePastCrossMultipliers = it }
        createHistoryRepository(
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
        createHistoryRepository(
            pastCrossMultipliers = expectedPastCrossMultipliers
        )
        historyRepository.subscribeForPastCrossMultipliers { actualPastCrossMultipliers = it }
        historyRepository.loadPastCrossMultipliers()

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
        createHistoryRepository(
            pastCrossMultipliers = listOf(
                CrossMultiplier(
                    valueAt00 = 3,   valueAt01 = 32.3,
                    valueAt10 = 4.6, valueAt11 = (32.3*4.6)/3
                )
            ),
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
    fun `Push Character 5 to Input on Position 1 0 of the Cross Multiplier at index 2`() = runBlocking {
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
        createHistoryRepository(
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
        historyRepository.loadPastCrossMultipliers()

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
    fun `Pop Character From Input on Position 0 1 of The Cross Multiplier at index 2`() = runBlocking {
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
        createHistoryRepository(
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
        historyRepository.loadPastCrossMultipliers()

        // When
        historyRepository.popCharacterOfInputOnPositionOfTheCrossMultiplierAt(
            index = 2,
            position = Pair(0, 1)
        )

        // Then
        Assert.assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }

    @Test
    fun `Change the Unknown Position to 0 1 of the Cross Multiplier at Index 1`() = runBlocking {
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
        createHistoryRepository(
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
        historyRepository.loadPastCrossMultipliers()

        // When
        historyRepository.changeTheUnknownPositionToPositionOfTheCrossMultiplierAt(
            index = 1,
            position = Pair(0, 1)
        )

        // Then
        Assert.assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }

    @Test
    fun `Clear Input on Position 0 1 of the Cross Multiplier at Index 0`() = runBlocking {
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
        createHistoryRepository(
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
        historyRepository.loadPastCrossMultipliers()

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
        createHistoryRepository(
            pastCrossMultipliers = listOf(
                CrossMultiplier(
                    valueAt00 = 3,   valueAt01 = 32.3,
                    valueAt10 = 4.6, valueAt11 = (32.3*4.6)/3
                )
            )
        )
        val callback: ((List<CrossMultiplier>) -> Unit) = { actualPastCrossMultipliers = it }
        historyRepository.subscribeForPastCrossMultipliers(callback)
        historyRepository.loadPastCrossMultipliers()

        // When
        historyRepository.deleteCrossMultiplierAt(index = 0)

        // Then
        Assert.assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }

    @Test
    fun `Delete the Only Cross Multiplier and Check if There are not Past Cross Multipliers`() = runBlocking {
        // Given
        val expectedAreTherePastCrossMultipliers = false
        var actualAreTherePastCrossMultipliers: Boolean? = null
        val callback: ((Boolean) -> Unit) = { actualAreTherePastCrossMultipliers = it }
        createHistoryRepository(
            pastCrossMultipliers = listOf(
                CrossMultiplier(
                    valueAt00 = 3,   valueAt01 = 32.3,
                    valueAt10 = 4.6, valueAt11 = (32.3*4.6)/3
                )
            ),
            onNewAreTherePastCrossMultipliers = callback
        )
        historyRepository.loadPastCrossMultipliers()

        // When
        historyRepository.deleteCrossMultiplierAt(index = 0)

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
        createHistoryRepository(
            pastCrossMultipliers = listOf(
                CrossMultiplier(
                    valueAt00 = 3,   valueAt01 = 32.3,
                    valueAt10 = 4.6, valueAt11 = (32.3*4.6)/3
                )
            )
        )
        val callback: ((List<CrossMultiplier>) -> Unit) = { actualPastCrossMultipliers = it }
        historyRepository.subscribeForPastCrossMultipliers(callback)
        historyRepository.loadPastCrossMultipliers()

        // When
        historyRepository.deleteHistory()

        // Then
        Assert.assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }

    @Test
    fun `Delete History and Check if There are not Past Cross Multipliers`() = runBlocking {
        // Given
        val expectedAreTherePastCrossMultipliers = false
        var actualAreTherePastCrossMultipliers: Boolean? = null
        val callback: ((Boolean) -> Unit) = { actualAreTherePastCrossMultipliers = it }
        createHistoryRepository(
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