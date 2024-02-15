package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.currentcrossmultiplier.CurrentCrossMultiplierDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class CurrentCrossMultipliersDataSourceTest {
    private val crossMultiplier = CrossMultiplier(valueAt00 = 3, valueAt01 = 4.3)

    private val currentCrossMultiplierDataSource = CurrentCrossMultiplierDataSource(
        currentCrossMultiplier = crossMultiplier
    )

    @Test
    fun `Retrieve Current Cross Multiplier`() = runBlocking {
        // Given
        val expectedCurrentCrossMultiplier = crossMultiplier

        //When
        val actualCurrentCrossMultiplier = currentCrossMultiplierDataSource
            .retrieveCurrentCrossMultiplier()

        // Then
        assertEquals(expectedCurrentCrossMultiplier, actualCurrentCrossMultiplier)
    }

    @Test
    fun `Update Current Cross Multiplier`() = runBlocking {
        // Given
        val expectedCurrentCrossMultiplier = CrossMultiplier(
            valueAt00 = 3,
            valueAt01 = 4.3,
            valueAt10 = 3.4
        )

        //When
        currentCrossMultiplierDataSource
            .updateCurrentCrossMultiplier(crossMultiplierUpdated = expectedCurrentCrossMultiplier)

        // Then
        val actualCurrentCrossMultiplier = currentCrossMultiplierDataSource
            .retrieveCurrentCrossMultiplier()
        assertEquals(expectedCurrentCrossMultiplier, actualCurrentCrossMultiplier)
    }
}