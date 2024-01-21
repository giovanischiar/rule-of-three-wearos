package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.CrossMultiplier
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class CrossMultiplierDataSourceTest {
    @Test
    fun `Request Current Cross Multiplier`() {
        val expectedCrossMultiplier = CrossMultiplier(a = "1", b = "2.3", c = "45.3")
        val actualCrossMultiplier = runBlocking {
            CrossMultiplierDataSource(currentCrossMultiplier = expectedCrossMultiplier).requestCurrentCrossMultiplier()
        }

        Assert.assertEquals(expectedCrossMultiplier, actualCrossMultiplier)
    }

    @Test
    fun `Update Current Cross Multiplier`() {
        val crossMultiplier = CrossMultiplier(a = "1", b = "2.3", c = "45.3")
        val dataSource = CrossMultiplierDataSource()
        runBlocking { dataSource.updateCurrentCrossMultiplier(crossMultiplier = crossMultiplier) }

        Assert.assertEquals(crossMultiplier, runBlocking { dataSource.requestCurrentCrossMultiplier() })
    }

    @Test
    fun `Request All Past Cross Multipliers`() {
        Assert.assertEquals(
            listOf(CrossMultiplier(a = "1", b = "2.3", c = "45.3")),
            runBlocking { CrossMultiplierDataSource(
                allPastCrossMultipliers = listOf(CrossMultiplier(a = "1", b = "2.3", c = "45.3"))
            ).requestAllPastCrossMultipliers() }
        )
    }

    @Test
    fun `Verify Reversed Order of All Past Cross Multipliers`() {
        val crossMultipliers = listOf(
            CrossMultiplier(a = "1", b = "2.3", c = "45.3"),
            CrossMultiplier(a = "45", b = "45.33", c = "45.3"),
            CrossMultiplier(a = "207", b = "97.33", c = "454.567"),
            CrossMultiplier(a = "34.4", b = "82.13", c = "905.57")
        )
        val dataSource = CrossMultiplierDataSource()
        for (crossMultiplier in crossMultipliers) {
            runBlocking { dataSource.addToAllPastCrossMultipliers(crossMultiplier) }
        }

        Assert.assertEquals(crossMultipliers.reversed(), runBlocking {
            dataSource.requestAllPastCrossMultipliers()
        })
    }

    @Test
    fun `Request Cross Multiplier`() {
        val expectedCrossMultiplier = CrossMultiplier(
            a = "207", b = "97.33", c = "454.567"
        ).resultCalculated()
        val actualCrossMultiplier = runBlocking {
            CrossMultiplierDataSource(
                allPastCrossMultipliers = listOf(
                    CrossMultiplier(a = "1", b = "2.3", c = "45.3").resultCalculated(),
                    CrossMultiplier(a = "45", b = "45.33", c = "45.3").resultCalculated(),
                    expectedCrossMultiplier
                )
            ).requestCrossMultiplier(index = 2)
        }

        Assert.assertEquals(expectedCrossMultiplier, actualCrossMultiplier)
    }
}