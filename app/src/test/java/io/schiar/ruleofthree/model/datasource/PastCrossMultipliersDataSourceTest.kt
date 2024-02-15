package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.pastcrossmultipliers.PastCrossMultipliersDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class PastCrossMultipliersDataSourceTest {
    private val crossMultipliers = listOf(
        CrossMultiplier(valueAt00 = 1, valueAt01 = 2.3, valueAt10 = 45.3),
        CrossMultiplier(valueAt00 = 3, valueAt01 = 32.3, valueAt10 = 4.6),
        CrossMultiplier(valueAt00 = 94.5, valueAt01 = 28.4, valueAt10 = 57),
        CrossMultiplier(valueAt00 = 98, valueAt01 = 23, valueAt10 = 4),
    )

    private val pastCrossMultipliersDataSource = PastCrossMultipliersDataSource(
        crossMultipliers = crossMultipliers
    )

    @Test
    fun `Create Cross Multipliers and Check its Reversed Order`() {
        // Given
        val expectedCrossMultipliers = crossMultipliers.reversed()
        val dataSource = PastCrossMultipliersDataSource()

        // When
        for (crossMultiplier in crossMultipliers) {
            runBlocking {
                dataSource.createPastCrossMultiplier(
                    crossMultiplierToBeCreated = crossMultiplier
                )
            }
        }
        val actualCrossMultipliers = runBlocking { dataSource.retrievePastCrossMultipliers()  }

        // Then
        assertEquals(expectedCrossMultipliers, actualCrossMultipliers)
    }

    @Test
    fun `Retrieve Past Cross Multipliers`() {
        // Given
        val expectedCrossMultipliers = crossMultipliers

        // When
        val actualCrossMultipliers = runBlocking {
            pastCrossMultipliersDataSource.retrievePastCrossMultipliers()
        }

        // Then
        assertEquals(expectedCrossMultipliers, actualCrossMultipliers)
    }

    @Test
    fun `Retrieve Cross Multiplier at Index 2`() {
        // Given
        val indexToRetrieve = 2
        val expectedCrossMultipliers = crossMultipliers[indexToRetrieve]

        // When
        val actualCrossMultipliers = runBlocking {
            pastCrossMultipliersDataSource.retrievePastCrossMultiplierAt(index = indexToRetrieve)
        }

        // Then
        assertEquals(expectedCrossMultipliers, actualCrossMultipliers)
    }

    @Test
    fun `Update Cross Multiplier at Index 2`() {
        // Given
        val indexToUpdate = 2
        val expectedCrossMultiplier = crossMultipliers[indexToUpdate]

        // When
        runBlocking {
            pastCrossMultipliersDataSource.updatePastCrossMultiplier(expectedCrossMultiplier)
        }
        val actualCrossMultiplier = runBlocking {
            pastCrossMultipliersDataSource.retrievePastCrossMultiplierAt(index = indexToUpdate)
        }

        // Then
        assertEquals(expectedCrossMultiplier, actualCrossMultiplier)
    }

    @Test
    fun `Delete Cross Multiplier at Index 2`() {
        // Given
        val indexToDelete = 2
        val expectedCrossMultipliers = crossMultipliers.toMutableList()
        expectedCrossMultipliers.removeAt(index = indexToDelete)

        // When
        runBlocking {
            pastCrossMultipliersDataSource.deletePastCrossMultiplierAt(index = indexToDelete)
        }
        val actualCrossMultipliers = runBlocking {
            pastCrossMultipliersDataSource.retrievePastCrossMultipliers()
        }

        // Then
        assertEquals(expectedCrossMultipliers, actualCrossMultipliers)
    }

    @Test
    fun `Delete Past Cross Multipliers`() {
        // Given
        val expectedCrossMultipliers = emptyList<CrossMultiplier>()
        val pastCrossMultipliersDataSource = PastCrossMultipliersDataSource(
            crossMultipliers = crossMultipliers
        )

        // When
        runBlocking { pastCrossMultipliersDataSource.deletePastCrossMultipliers() }
        val actualCrossMultipliers = runBlocking {
            pastCrossMultipliersDataSource.retrievePastCrossMultipliers()
        }

        // Then
        assertEquals(expectedCrossMultipliers, actualCrossMultipliers)
    }
}