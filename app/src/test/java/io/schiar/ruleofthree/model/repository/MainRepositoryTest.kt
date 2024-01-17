package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.Numbers
import io.schiar.ruleofthree.model.datasource.NumbersDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class MainRepositoryTest {
    @Test
    fun `Subscribe For Result and Add Input For Result`() {
        val a = 12.3
        val b = 45
        val c = 4.56
        val mainRepository = MainRepository()
        runBlocking { mainRepository.addToInput(value = a.toString(), position = 0) }
        runBlocking { mainRepository.addToInput(value = b.toString(), position = 1) }
        mainRepository.subscribeForNumbers { actualNumbers ->
            val expectedNumbers = Numbers(
                a = a.toString(),
                b = b.toString(),
                c = c.toString()
            ).resultCalculated()
            Assert.assertEquals(expectedNumbers, actualNumbers)
        }
        runBlocking { mainRepository.addToInput(value = c.toString(), position = 2) }
    }

    @Test
    fun `Subscribe For Numbers and Add Input`() {
        val mainRepository = MainRepository()
        runBlocking { mainRepository.addToInput(value = "2", position = 0) }
        mainRepository.subscribeForNumbers {
            Assert.assertEquals(Numbers(a = "22", b = "", c = ""), it)
        }
        runBlocking { mainRepository.addToInput(value = "2", position = 0) }
    }

    @Test
    fun `Subscribe For Is There History and Add Numbers To History`() {
        val mainRepository = MainRepository()
        runBlocking { mainRepository.addToInput(value = "2", position = 0) }
        runBlocking { mainRepository.addToInput(value = "12", position = 1) }
        runBlocking { mainRepository.addToInput(value = "40", position = 2) }
        mainRepository.subscribeForIsThereHistory {
            Assert.assertTrue(it)
        }
        runBlocking { mainRepository.submitToHistory() }
    }

    private fun addInputToInput(numbersValue: String, position: Int, repository: MainRepository) {
        runBlocking { repository.clearInput(position = position) }
        for (value in numbersValue) {
            runBlocking { repository.addToInput(value = value.toString(), position = position) }
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
            runBlocking { repository.submitToHistory() }
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
        runBlocking { repository.submitToHistory() }
    }

    @Test
    fun `Remove Input Position of Current Numbers`() {
        val mainRepository = MainRepository(
            dataSource = NumbersDataSource(
                currentNumbers = Numbers(a = "2.45", b = "45", c = "3.5")
            )
        )
        mainRepository.subscribeForNumbers {
            Assert.assertEquals(Numbers(a = "2.4", b = "45", c = "3.5").resultCalculated(), it)
        }
        runBlocking { mainRepository.removeFromInput(position = 0) }
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
        runBlocking { mainRepository.clearInput(position = 1) }
    }

    @Test
    fun `Clear All Inputs of Current Numbers`() {
        val mainRepository = MainRepository(
            dataSource = NumbersDataSource(
                currentNumbers = Numbers(a = "2.45", b = "45", c = "3.5")
            )
        )

        mainRepository.subscribeForNumbers {
            Assert.assertEquals(Numbers(), it)
        }

        runBlocking { mainRepository.clearAllInputs() }
    }

    @Test
    fun `Add Input Position of Current Numbers and then Remove it`() {
        val mainRepository = MainRepository()
        runBlocking { mainRepository.addToInput(value = "1", position = 0) }
        mainRepository.subscribeForNumbers {
            Assert.assertEquals(Numbers(a = "", b = "", c = ""), it)
        }
        runBlocking { mainRepository.removeFromInput(position = 0) }
    }

    @Test
    fun `Add Two Input to Position 0 in Current Numbers and then Remove it`() {
        val mainRepository = MainRepository()
        runBlocking { mainRepository.addToInput(value = "1", position = 0) }
        runBlocking { mainRepository.addToInput(value = "4", position = 0) }
        runBlocking { mainRepository.removeFromInput(position = 0) }

        mainRepository.subscribeForNumbers {
            Assert.assertEquals(Numbers(a = "", b = "", c = ""), it)
        }
        runBlocking { mainRepository.removeFromInput(position = 0) }
    }

    @Test
    fun `Add Two Input to Position 0, 1, and 2 in Current Numbers and then Remove Them All`() {
        val mainRepository = MainRepository()
        runBlocking { mainRepository.addToInput(value = "1", position = 0) }
        runBlocking { mainRepository.addToInput(value = "4", position = 0) }
        runBlocking { mainRepository.addToInput(value = "4", position = 1) }
        runBlocking { mainRepository.addToInput(value = ".", position = 1) }
        runBlocking { mainRepository.addToInput(value = "6", position = 1) }
        runBlocking { mainRepository.addToInput(value = "0", position = 2) }
        runBlocking { mainRepository.addToInput(value = ".", position = 2) }
        runBlocking { mainRepository.addToInput(value = "5", position = 2) }
        runBlocking { mainRepository.removeFromInput(position = 0) }
        runBlocking { mainRepository.removeFromInput(position = 1) }
        runBlocking { mainRepository.removeFromInput(position = 1) }
        runBlocking { mainRepository.removeFromInput(position = 1) }
        runBlocking { mainRepository.removeFromInput(position = 2) }
        runBlocking { mainRepository.removeFromInput(position = 2) }
        runBlocking { mainRepository.removeFromInput(position = 2) }
        mainRepository.subscribeForNumbers {
            Assert.assertEquals(Numbers(a = "", b = "", c = ""), it)
        }
        runBlocking { mainRepository.removeFromInput(position = 0) }
    }
}