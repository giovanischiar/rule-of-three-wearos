package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.Numbers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class NumbersDataSourceTest {
    @Test
    fun `Request Current Numbers`() {
        val expectedNumbers = Numbers(a = "1", b = "2.3", c = "45.3")
        val actualNumbers = runBlocking {
            NumbersDataSource(
                currentNumbers = expectedNumbers
            ).requestCurrentNumbers()
        }

        Assert.assertEquals(expectedNumbers, actualNumbers)
    }

    @Test
    fun `Update Current Numbers`() {
        val numbers = Numbers(a = "1", b = "2.3", c = "45.3")
        val dataSource = NumbersDataSource()
        runBlocking { dataSource.updateCurrentNumbers(numbers = numbers) }

        Assert.assertEquals(numbers, runBlocking { dataSource.requestCurrentNumbers() })
    }

    @Test
    fun `Request All Past Numbers`() {
        Assert.assertEquals(
            listOf(Numbers(a = "1", b = "2.3", c = "45.3")),
            runBlocking { NumbersDataSource(
                allPastNumbers = listOf(Numbers(a = "1", b = "2.3", c = "45.3"))
            ).requestAllPastNumbers() }
        )
    }

    @Test
    fun `Verify Reversed Order of All Past Numbers`() {
        val numbersList = listOf(
            Numbers(a = "1", b = "2.3", c = "45.3"),
            Numbers(a = "45", b = "45.33", c = "45.3"),
            Numbers(a = "207", b = "97.33", c = "454.567"),
            Numbers(a = "34.4", b = "82.13", c = "905.57")
        )
        val dataSource = NumbersDataSource()
        for (numbers in numbersList) {
            runBlocking { dataSource.addToAllPastNumbers(numbers) }
        }

        Assert.assertEquals(numbersList.reversed(), runBlocking {
            dataSource.requestAllPastNumbers()
        })
    }
}