package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierLocalDataSource
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersLocalDataSource
import io.schiar.ruleofthree.model.repository.listener.CrossMultiplierCreatedListener
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class AppRepositoryTest {
    private fun createAppRepository(
        currentCrossMultiplier: CrossMultiplier = CrossMultiplier(),
        pastCrossMultipliers: List<CrossMultiplier> = emptyList(),
        onNewCrossMultiplierCreated: (CrossMultiplier) -> Unit = {},
    ): AppRepository {
        val pastCrossMultipliersDataSource = PastCrossMultipliersLocalDataSource(
            crossMultipliersToInsert = pastCrossMultipliers
        )
        val currentCrossMultiplierDataSource = CurrentCrossMultiplierLocalDataSource(
            currentCrossMultiplierToInsert = currentCrossMultiplier
        )
        return AppRepository(
            pastCrossMultipliersDataSource = pastCrossMultipliersDataSource,
            currentCrossMultiplierDataSource = currentCrossMultiplierDataSource,
            crossMultiplierCreatedListener = object : CrossMultiplierCreatedListener {
                override fun crossMultiplierCreated(crossMultiplier: CrossMultiplier) {
                    onNewCrossMultiplierCreated(crossMultiplier)
                }
            }
        )
    }

    @Test
    fun `Add Current Cross Multiplier to Past Cross Multipliers and Check if the Current Cross Multiplier was Created`() = runBlocking {
        // Given
        val expectedCrossMultiplierCreated = CrossMultiplier(
            valueAt00 = 1,    valueAt01 = 2.3,
            valueAt10 = 45.3, valueAt11 = (45.3*2.3)/1,
            unknownPosition = Pair(1, 1)
        )

        var actualCrossMultiplierCreated: CrossMultiplier? = null
        val callback: (CrossMultiplier) -> Unit = { actualCrossMultiplierCreated = it }
        val mainRepository = createAppRepository(
            currentCrossMultiplier = expectedCrossMultiplierCreated,
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
            ),
            onNewCrossMultiplierCreated = callback
        )

        // When
        mainRepository.addCurrentCrossMultiplierToPastCrossMultipliers()

        // Then
        Assert.assertEquals(expectedCrossMultiplierCreated, actualCrossMultiplierCreated)
    }
}