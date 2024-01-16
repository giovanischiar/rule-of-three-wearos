package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.Numbers
import io.schiar.ruleofthree.model.datasource.NumbersDataSource
import org.junit.Assert
import org.junit.Test

class MainRepositoryTest {
    @Test
    fun `Request Data Source Current Numbers`() {
        val expectedNumbers = Numbers(a = "1", b = "2.3", c = "45.3")
        val actualNumbers = MainRepository(dataSource = NumbersDataSource(
            currentNumbers = expectedNumbers
        )).requestCurrentNumbers()

        Assert.assertEquals(expectedNumbers, actualNumbers)
    }

    @Test
    fun `Request Data Source All Past Current Numbers`() {
        val expectedPastNumbers = listOf(
            Numbers(a = "1", b = "2.3", c = "45.3"),
            Numbers(a = "45", b = "45.33", c = "45.3"),
            Numbers(a = "207", b = "97.33", c = "454.567")
        )
        val actualPastNumbers = MainRepository(dataSource = NumbersDataSource(
            allPastNumbers = expectedPastNumbers
        )).requestAllPastNumbers()
        Assert.assertEquals(expectedPastNumbers, actualPastNumbers)
    }

    @Test
    fun `Subscribe For Result and Add Input For Result`() {
        val a = 12.3
        val b = 45
        val c = 4.56
        val mainRepository = MainRepository()
        mainRepository.addToInput(value = a.toString(), position = 0)
        mainRepository.addToInput(value = b.toString(), position = 1)
        mainRepository.subscribeForResult { actualResult ->
            val expectedResult = c * b / a
            Assert.assertEquals(expectedResult, actualResult)
        }
        mainRepository.addToInput(value = c.toString(), position = 2)
    }

    @Test
    fun `Subscribe For Numbers and Add Input`() {
        val mainRepository = MainRepository()
        mainRepository.addToInput(value = "2", position = 0)
        mainRepository.subscribeForNumbers {
            Assert.assertEquals(Numbers(a = "22", b = "", c = ""), it)
        }
        mainRepository.addToInput(value = "2", position = 0)
    }

    private fun addInputToInput(numbersValue: String, position: Int, repository: MainRepository) {
        repository.clearInput(position = position)
        for (value in numbersValue) {
            repository.addToInput(value = value.toString(), position = position)
        }
    }

    @Test
    fun `Receive All Past Numbers`() {
        val numbersList = listOf(
            Numbers(a = "1", b = "2.3", c = "45.3"),
            Numbers(a = "45", b = "45.33", c = "45.3"),
            Numbers(a = "207", b = "97.33", c = "454.567")
        )
        val repository = MainRepository()
        for (numbers in numbersList) {
            addInputToInput(numbersValue = numbers.a.value, position = 0, repository = repository)
            addInputToInput(numbersValue = numbers.b.value, position = 1, repository = repository)
            addInputToInput(numbersValue = numbers.c.value, position = 2, repository = repository)
            repository.submitToHistory()
        }
        val lastNumbers = Numbers(a = "34.4", b = "82.13", c = "905.57")

        repository.subscribeForAllPastNumbers {
            val numberListReversed = listOf(lastNumbers) + numbersList.reversed()
            for (i in numberListReversed.indices) {
                val actualNumbers = numberListReversed[i]
                val expectedNumbers = it[i]
                Assert.assertEquals(actualNumbers.a, expectedNumbers.a)
                Assert.assertEquals(actualNumbers.b, expectedNumbers.b)
                Assert.assertEquals(actualNumbers.c, expectedNumbers.c)
            }
        }

        addInputToInput(numbersValue = lastNumbers.a.value, position = 0, repository = repository)
        addInputToInput(numbersValue = lastNumbers.b.value, position = 1, repository = repository)
        addInputToInput(numbersValue = lastNumbers.c.value, position = 2, repository = repository)
        repository.submitToHistory()
    }

    @Test
    fun `Remove Input Position of Current Numbers`() {
        val mainRepository = MainRepository(
            dataSource = NumbersDataSource(
                currentNumbers = Numbers(a = "2.45", b = "45", c = "3.5")
            )
        )
        mainRepository.subscribeForNumbers {
            Assert.assertEquals(Numbers(a = "2.4", b = "45", c = "3.5"), it)
        }
        mainRepository.removeFromInput(position = 0)
    }

    @Test
    fun `Clear Input Position of Current Numbers`() {
        val mainRepository = MainRepository(
            dataSource = NumbersDataSource(
                currentNumbers = Numbers(a = "2.45", b = "45", c = "3.5")
            )
        )
        mainRepository.subscribeForNumbers {
            Assert.assertEquals(Numbers(a = "2.45", b = "", c = "3.5"), it)
        }
        mainRepository.clearInput(position = 1)
    }

    @Test
    fun `Add Input Position of Current Numbers and then Remove it`() {
        val mainRepository = MainRepository()
        mainRepository.addToInput(value = "1", position = 0)
        mainRepository.subscribeForNumbers {
            Assert.assertEquals(Numbers(a = "", b = "", c = ""), it)
        }
        mainRepository.removeFromInput(position = 0)
    }

    @Test
    fun `Add Two Input to Position 0 in Current Numbers and then Remove it`() {
        val mainRepository = MainRepository()
        mainRepository.addToInput(value = "1", position = 0)
        mainRepository.addToInput(value = "4", position = 0)
        mainRepository.removeFromInput(position = 0)

        mainRepository.subscribeForNumbers {
            Assert.assertEquals(Numbers(a = "", b = "", c = ""), it)
        }
        mainRepository.removeFromInput(position = 0)
    }

    @Test
    fun `Add Two Input to Position 0, 1, and 2 in Current Numbers and then Remove Them All`() {
        val mainRepository = MainRepository()
        mainRepository.addToInput(value = "1", position = 0)
        mainRepository.addToInput(value = "4", position = 0)
        mainRepository.addToInput(value = "4", position = 1)
        mainRepository.addToInput(value = ".", position = 1)
        mainRepository.addToInput(value = "6", position = 1)
        mainRepository.addToInput(value = "0", position = 2)
        mainRepository.addToInput(value = ".", position = 2)
        mainRepository.addToInput(value = "5", position = 2)
        mainRepository.removeFromInput(position = 0)
        mainRepository.removeFromInput(position = 1)
        mainRepository.removeFromInput(position = 1)
        mainRepository.removeFromInput(position = 1)
        mainRepository.removeFromInput(position = 2)
        mainRepository.removeFromInput(position = 2)
        mainRepository.removeFromInput(position = 2)

        mainRepository.subscribeForNumbers {
            Assert.assertEquals(Numbers(a = "", b = "", c = ""), it)
        }
        mainRepository.removeFromInput(position = 0)
    }
}