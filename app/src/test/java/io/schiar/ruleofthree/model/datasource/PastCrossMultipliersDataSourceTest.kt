package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.CrossMultiplier
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class PastCrossMultipliersDataSourceTest {
    private val crossMultipliers = listOf(
        CrossMultiplier(a = "1", b = "2.3", c = "45.3"),
        CrossMultiplier(a = "3", b = "32.3", c = "4.6"),
        CrossMultiplier(a = "94.5", b = "28.4", c = "57"),
        CrossMultiplier(a = "98", b = "23", c = "4"),
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
        Assert.assertEquals(expectedCrossMultipliers, actualCrossMultipliers)
    }

    @Test
    fun `Retrieve Past Cross Multipliers`() {
        // Given
        val expectedCrossMultipliers = crossMultipliers.reversed()

        // When
        val actualCrossMultipliers = runBlocking {
            pastCrossMultipliersDataSource.retrievePastCrossMultipliers()
        }

        // Then
        Assert.assertEquals(expectedCrossMultipliers, actualCrossMultipliers)
    }

    @Test
    fun `Retrieve Cross Multiplier at Index 2`() {
        // Given
        val indexToRetrieve = 2
        val expectedCrossMultipliers = crossMultipliers.reversed()[indexToRetrieve]

        // When
        val actualCrossMultipliers = runBlocking {
            pastCrossMultipliersDataSource.retrievePastCrossMultiplierAt(index = indexToRetrieve)
        }

        // Then
        Assert.assertEquals(expectedCrossMultipliers, actualCrossMultipliers)
    }

    @Test
    fun `Update Cross Multiplier at Index 2`() {
        // Given
        val indexToUpdate = 2
        val expectedCrossMultiplier = crossMultipliers.reversed()[indexToUpdate]

        // When
        runBlocking {
            pastCrossMultipliersDataSource.updatePastCrossMultiplier(expectedCrossMultiplier)
        }
        val actualCrossMultiplier = runBlocking {
            pastCrossMultipliersDataSource.retrievePastCrossMultiplierAt(index = indexToUpdate)
        }

        // Then
        Assert.assertEquals(expectedCrossMultiplier, actualCrossMultiplier)
    }

    @Test
    fun `Delete Cross Multiplier at Index 2`() {
        // Given
        val indexToDelete = 2
        val expectedCrossMultipliers = crossMultipliers.reversed().toMutableList()
        expectedCrossMultipliers.removeAt(index = indexToDelete)

        // When
        runBlocking {
            pastCrossMultipliersDataSource.deletePastCrossMultiplierAt(index = indexToDelete)
        }
        val actualCrossMultipliers = runBlocking {
            pastCrossMultipliersDataSource.retrievePastCrossMultipliers()
        }

        // Then
        Assert.assertEquals(expectedCrossMultipliers, actualCrossMultipliers)
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
        Assert.assertEquals(expectedCrossMultipliers, actualCrossMultipliers)
    }
}