package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierLocalDataSource
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSource
import io.schiar.ruleofthree.model.repository.listener.AreTherePastCrossMultipliersListener
import io.schiar.ruleofthree.model.repository.listener.PastCrossMultipliersListener
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class AppRepositoryTest {
    private fun createAppRepository(
        currentCrossMultiplier: CrossMultiplier = CrossMultiplier(),
        pastCrossMultipliers: List<CrossMultiplier> = emptyList(),
        onNewPastCrossMultipliers: (List<CrossMultiplier>) -> Unit = {},
        onNewAreTherePastCrossMultipliers: (Boolean) -> Unit = {}
    ): AppRepository {
        val pastCrossMultipliersDataSource = PastCrossMultipliersDataSource(
            crossMultipliers = pastCrossMultipliers
        )
        val currentCrossMultiplierDataSource = CurrentCrossMultiplierLocalDataSource(
            currentCrossMultiplierToInsert = currentCrossMultiplier
        )
        return AppRepository(
            pastCrossMultipliersDataSource = pastCrossMultipliersDataSource,
            currentCrossMultiplierDataSource = currentCrossMultiplierDataSource,
            pastCrossMultipliersListener = object : PastCrossMultipliersListener {
                override fun onPastCrossMultipliersChangedTo(
                    newPastCrossMultipliers: List<CrossMultiplier>
                ) {
                    onNewPastCrossMultipliers(newPastCrossMultipliers)
                }
            },
            areTherePastCrossMultipliersListener = object : AreTherePastCrossMultipliersListener {
                override fun areTherePastCrossMultipliersChangedTo(newAreTherePastCrossMultipliers: Boolean) {
                    onNewAreTherePastCrossMultipliers(newAreTherePastCrossMultipliers)
                }
            }
        )
    }

    @Test
    fun `Add Current Cross Multiplier to Past Cross Multipliers and Check if the Past Cross Multipliers Contains the Current Cross Multiplier`() = runBlocking {
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
        val callback: (List<CrossMultiplier>) -> Unit = { actualPastCurrentCrossMultipliers = it }
        val mainRepository = createAppRepository(
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
            ),
            onNewPastCrossMultipliers = callback
        )

        // When
        mainRepository.addCurrentCrossMultiplierToPastCrossMultipliers()

        // Then
        Assert.assertEquals(expectedPastCurrentCrossMultipliers, actualPastCurrentCrossMultipliers)
    }

    @Test
    fun `Add Current Cross Multiplier to Past Cross Multipliers and Check if There Are Past Cross Multipliers`() = runBlocking {
        // Given
        val expectedAreTherePastCrossMultipliers = true
        var actualAreTherePastCrossMultipliers: Boolean? = null
        val callback: ((Boolean) -> Unit) = { actualAreTherePastCrossMultipliers = it }
        val mainRepository = createAppRepository(
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
                    valueAt10 = 4.6, valueAt11 = (32.3*4.6)/3
                ),
            ),
            onNewAreTherePastCrossMultipliers = callback
        )

        // When
        mainRepository.addCurrentCrossMultiplierToPastCrossMultipliers()

        // Then
        Assert.assertEquals(
            expectedAreTherePastCrossMultipliers,
            actualAreTherePastCrossMultipliers
        )
    }
}