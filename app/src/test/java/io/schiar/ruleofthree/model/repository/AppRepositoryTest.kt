package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierLocalDataSource
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersLocalDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class AppRepositoryTest {
    private fun createAppRepository(
        currentCrossMultiplier: CrossMultiplier = CrossMultiplier(),
        pastCrossMultipliersDataSource: PastCrossMultipliersLocalDataSource
    ): AppRepository {
        val currentCrossMultiplierDataSource = CurrentCrossMultiplierLocalDataSource(
            currentCrossMultiplierToInsert = currentCrossMultiplier
        )
        return AppRepository(
            pastCrossMultipliersDataSource = pastCrossMultipliersDataSource,
            currentCrossMultiplierDataSource = currentCrossMultiplierDataSource
        )
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
        val mainRepository = createAppRepository(
            currentCrossMultiplier = expectedPastCrossMultipliers[0],
            pastCrossMultipliersDataSource = pastCrossMultipliersLocalDataSource
        )
        // When
        mainRepository.addCurrentCrossMultiplierToPastCrossMultipliers()

        // Then
        val actualPastCrossMultipliers = historyRepository.pastCrossMultipliers.first()
        Assert.assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }
}