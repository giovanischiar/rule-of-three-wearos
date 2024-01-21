package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CrossMultiplierDataSource
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
        runBlocking { mainRepository.addToInput(value = a.toString(), position = Pair(0, 0)) }
        runBlocking { mainRepository.addToInput(value = b.toString(), position = Pair(0, 1)) }
        mainRepository.subscribeForCrossMultipliers { actualCrossMultiplier ->
            val expectedCrossMultiplier = CrossMultiplier(
                a = a.toString(),
                b = b.toString(),
                c = c.toString()
            ).resultCalculated()
            Assert.assertEquals(expectedCrossMultiplier, actualCrossMultiplier)
        }
        runBlocking { mainRepository.addToInput(value = c.toString(), position = Pair(1, 0)) }
    }

    @Test
    fun `Subscribe For Cross Multiplier and Add Input`() {
        val mainRepository = MainRepository()
        runBlocking { mainRepository.addToInput(value = "2", position = Pair(0, 0)) }
        mainRepository.subscribeForCrossMultipliers {
            Assert.assertEquals(CrossMultiplier(a = "22", b = "", c = ""), it)
        }
        runBlocking { mainRepository.addToInput(value = "2", position = Pair(0, 0)) }
    }

    @Test
    fun `Subscribe For Is There History and Add Cross Multiplier To History`() {
        val mainRepository = MainRepository()
        runBlocking { mainRepository.addToInput(value = "2", position = Pair(0, 0)) }
        runBlocking { mainRepository.addToInput(value = "12", position = Pair(0, 1)) }
        runBlocking { mainRepository.addToInput(value = "40", position = Pair(1, 0)) }
        mainRepository.subscribeForIsThereHistories {
            Assert.assertTrue(it)
        }
        runBlocking { mainRepository.submitToHistory() }
    }

    private fun addInputToInput(
        crossMultiplierInputValue: String, position: Pair<Int, Int>, repository: MainRepository
    ) {
        runBlocking { repository.clearInput(position = position) }
        for (value in crossMultiplierInputValue) {
            runBlocking { repository.addToInput(value = value.toString(), position = position) }
        }
    }

    @Test
    fun `Receive All Past Cross Multipliers`() {
        val crossMultipliers = listOf(
            CrossMultiplier(a = "1", b = "2.3", c = "45.3").resultCalculated(),
            CrossMultiplier(a = "45", b = "45.33", c = "45.3").resultCalculated(),
            CrossMultiplier(a = "207", b = "97.33", c = "454.567").resultCalculated()
        )
        val repository = MainRepository()
        for (crossMultiplier in crossMultipliers) {
            addInputToInput(
                crossMultiplierInputValue = crossMultiplier.a().value,
                position = Pair(0, 0),
                repository = repository
            )
            addInputToInput(
                crossMultiplierInputValue = crossMultiplier.b().value,
                position = Pair(0, 1),
                repository = repository
            )
            addInputToInput(
                crossMultiplierInputValue = crossMultiplier.c().value,
                position = Pair(1, 0),
                repository = repository
            )
            runBlocking { repository.submitToHistory() }
        }
        val lastCrossMultiplier =
            CrossMultiplier(a = "34.4", b = "82.13", c = "905.57").resultCalculated()

        repository.subscribeForAllPastCrossMultipliers { actualAllPassCrossMultipliers ->
            val expectedAllPassCrossMultipliers =
                listOf(lastCrossMultiplier) + crossMultipliers.reversed()
            for (i in actualAllPassCrossMultipliers.indices) {
                Assert.assertEquals(expectedAllPassCrossMultipliers, actualAllPassCrossMultipliers)
            }
        }

        addInputToInput(
            crossMultiplierInputValue = lastCrossMultiplier.a().value,
            position = Pair(0, 0),
            repository = repository
        )
        addInputToInput(
            crossMultiplierInputValue = lastCrossMultiplier.b().value,
            position = Pair(0, 1),
            repository = repository
        )
        addInputToInput(
            crossMultiplierInputValue = lastCrossMultiplier.c().value,
            position = Pair(1, 0),
            repository = repository
        )
        runBlocking { repository.submitToHistory() }
    }

    @Test
    fun `Remove Input Position of Current Cross Multiplier`() {
        val mainRepository = MainRepository(
            dataSource = CrossMultiplierDataSource(
                currentCrossMultiplier = CrossMultiplier(a = "2.45", b = "45", c = "3.5")
            )
        )
        mainRepository.subscribeForCrossMultipliers { expectedCrossMultiplier ->
            Assert
                .assertEquals(CrossMultiplier(a = "2.4", b = "45", c = "3.5")
                    .resultCalculated(),
                    expectedCrossMultiplier
                )
        }
        runBlocking { mainRepository.removeFromInput(position = Pair(0, 0)) }
    }

    @Test
    fun `Replace Current Numbers With One From History`() {
        val crossMultiplierToReplaceTo = CrossMultiplier(a = "207", b = "97.33", c = "454.567")
            .resultCalculated()
        val mainRepository = MainRepository(
            dataSource = CrossMultiplierDataSource(
                currentCrossMultiplier = CrossMultiplier(a = "2.45", b = "45", c = "3.5"),
                allPastCrossMultipliers = listOf(
                    CrossMultiplier(a = "1", b = "2.3", c = "45.3").resultCalculated(),
                    CrossMultiplier(a = "45", b = "45.33", c = "45.3").resultCalculated(),
                    crossMultiplierToReplaceTo
                )
            )
        )
        mainRepository.subscribeForCrossMultipliers { actualCrossMultiplier ->
            Assert.assertEquals(crossMultiplierToReplaceTo, actualCrossMultiplier)
        }
        runBlocking { mainRepository.replaceCurrentCrossMultiplier(index = 2) }
    }

    @Test
    fun `Remove Cross Multiplier from History`() {
        val mainRepository = MainRepository(
            dataSource = CrossMultiplierDataSource(
                allPastCrossMultipliers = listOf(
                    CrossMultiplier(a = "1", b = "2.3", c = "45.3"),
                    CrossMultiplier(a = "45", b = "45.33", c = "45.3"),
                    CrossMultiplier(a = "207", b = "97.33", c = "454.567")
                )
            )
        )
        mainRepository.subscribeForAllPastCrossMultipliers {
            Assert.assertEquals(
                listOf(
                    CrossMultiplier(a = "1", b = "2.3", c = "45.3"),
                    CrossMultiplier(a = "207", b = "97.33", c = "454.567")
                ), it)
        }
        runBlocking { mainRepository.deleteHistoryItem(index = 1) }
    }

    @Test
    fun `Remove All Past Cross Multipliers from History`() {
        val mainRepository = MainRepository(
            dataSource = CrossMultiplierDataSource(
                allPastCrossMultipliers = listOf(
                    CrossMultiplier(a = "1", b = "2.3", c = "45.3"),
                    CrossMultiplier(a = "45", b = "45.33", c = "45.3"),
                    CrossMultiplier(a = "207", b = "97.33", c = "454.567")
                )
            )
        )
        mainRepository.subscribeForAllPastCrossMultipliers { actualAllPastCrossMultipliers ->
            Assert.assertEquals(emptyList<CrossMultiplier>(), actualAllPastCrossMultipliers)
        }
        runBlocking { mainRepository.deleteHistory() }
    }

    @Test
    fun `Clear Input Position of Current Cross Multiplier`() {
        val mainRepository = MainRepository(
            dataSource = CrossMultiplierDataSource(
                currentCrossMultiplier = CrossMultiplier(a = "2.45", b = "45", c = "3.5")
            )
        )
        mainRepository.subscribeForCrossMultipliers {
            Assert.assertEquals(CrossMultiplier(a = "2.45", b = "", c = "3.5"), it)
        }
        runBlocking { mainRepository.clearInput(position = Pair(0, 1)) }
    }

    @Test
    fun `Clear All Inputs of Current Cross Multiplier`() {
        val mainRepository = MainRepository(
            dataSource = CrossMultiplierDataSource(
                currentCrossMultiplier = CrossMultiplier(a = "2.45", b = "45", c = "3.5")
            )
        )

        mainRepository.subscribeForCrossMultipliers {
            Assert.assertEquals(CrossMultiplier(), it)
        }

        runBlocking { mainRepository.clearAllInputs() }
    }

    @Test
    fun `Add Input Position of Current Cross Multiplier and then Remove it`() {
        val mainRepository = MainRepository()
        runBlocking { mainRepository.addToInput(value = "1", position = Pair(0, 0)) }
        mainRepository.subscribeForCrossMultipliers {
            Assert.assertEquals(CrossMultiplier(a = "", b = "", c = ""), it)
        }
        runBlocking { mainRepository.removeFromInput(position = Pair(0, 0)) }
    }

    @Test
    fun `Add Two Input to Position 0 in Current Cross Multiplier and then Remove it`() {
        val mainRepository = MainRepository()
        runBlocking { mainRepository.addToInput(value = "1", position = Pair(0, 0)) }
        runBlocking { mainRepository.addToInput(value = "4", position = Pair(0, 0)) }
        runBlocking { mainRepository.removeFromInput(position = Pair(0, 0)) }

        mainRepository.subscribeForCrossMultipliers {
            Assert.assertEquals(CrossMultiplier(a = "", b = "", c = ""), it)
        }
        runBlocking { mainRepository.removeFromInput(position = Pair(0, 0)) }
    }

    @Test
    fun `Add Two Input to Position 0, 1, and 2 in Current Cross Multiplier and then Remove Them All`() {
        val mainRepository = MainRepository()
        runBlocking { mainRepository.addToInput(value = "1", position = Pair(0, 0)) }
        runBlocking { mainRepository.addToInput(value = "4", position = Pair(0, 0)) }
        runBlocking { mainRepository.addToInput(value = "4", position = Pair(0, 1)) }
        runBlocking { mainRepository.addToInput(value = ".", position = Pair(0, 1)) }
        runBlocking { mainRepository.addToInput(value = "6", position = Pair(0, 1)) }
        runBlocking { mainRepository.addToInput(value = "0", position = Pair(1, 0)) }
        runBlocking { mainRepository.addToInput(value = ".", position = Pair(1, 0)) }
        runBlocking { mainRepository.addToInput(value = "5", position = Pair(1, 0)) }
        runBlocking { mainRepository.removeFromInput(position = Pair(0, 0)) }
        runBlocking { mainRepository.removeFromInput(position = Pair(0, 1)) }
        runBlocking { mainRepository.removeFromInput(position = Pair(0, 1)) }
        runBlocking { mainRepository.removeFromInput(position = Pair(0, 1)) }
        runBlocking { mainRepository.removeFromInput(position = Pair(1, 0)) }
        runBlocking { mainRepository.removeFromInput(position = Pair(1, 0)) }
        runBlocking { mainRepository.removeFromInput(position = Pair(1, 0)) }
        mainRepository.subscribeForCrossMultipliers {
            Assert.assertEquals(CrossMultiplier(a = "", b = "", c = ""), it)
        }
        runBlocking { mainRepository.removeFromInput(position = Pair(0, 0)) }
    }

    @Test
    fun `Add to a Cross Multiplier's Input from History`() {
        val mainRepository = MainRepository(
            dataSource = CrossMultiplierDataSource(
                allPastCrossMultipliers = listOf(
                    CrossMultiplier(a = "1", b = "2.3", c = "45.3").resultCalculated(),
                    CrossMultiplier(a = "45", b = "45.33", c = "45.3").resultCalculated(),
                    CrossMultiplier(a = "207", b = "97.33", c = "454.567").resultCalculated()
                )
            )
        )
        mainRepository.subscribeForAllPastCrossMultipliers {
            Assert.assertEquals(
                listOf(
                    CrossMultiplier(a = "1", b = "2.3", c = "45.3").resultCalculated(),
                    CrossMultiplier(a = "45", b = "45.334", c = "45.3").resultCalculated(),
                    CrossMultiplier(a = "207", b = "97.33", c = "454.567").resultCalculated()
                ), it)
        }
        runBlocking { mainRepository.addToInput(index = 1, "4", Pair(0, 1)) }
    }

    @Test
    fun `Remove to a Cross Multiplier's Input from History`() {
        val mainRepository = MainRepository(
            dataSource = CrossMultiplierDataSource(
                allPastCrossMultipliers = listOf(
                    CrossMultiplier(a = "1", b = "2.3", c = "45.3").resultCalculated(),
                    CrossMultiplier(a = "45", b = "45.33", c = "45.3").resultCalculated(),
                    CrossMultiplier(a = "207", b = "97.33", c = "454.567").resultCalculated()
                )
            )
        )
        mainRepository.subscribeForAllPastCrossMultipliers {
            Assert.assertEquals(
                listOf(
                    CrossMultiplier(a = "1", b = "2.3", c = "45.3").resultCalculated(),
                    CrossMultiplier(a = "45", b = "45.33", c = "45.3").resultCalculated(),
                    CrossMultiplier(a = "207", b = "97.33", c = "454.56").resultCalculated()
                ), it)
        }
        runBlocking { mainRepository.removeFromInput(index = 2, Pair(1, 0)) }
    }

    @Test
    fun `Clear a Cross Multiplier's Input from History`() {
        val mainRepository = MainRepository(
            dataSource = CrossMultiplierDataSource(
                allPastCrossMultipliers = listOf(
                    CrossMultiplier(a = "1", b = "2.3", c = "45.3").resultCalculated(),
                    CrossMultiplier(a = "45", b = "45.33", c = "45.3").resultCalculated(),
                    CrossMultiplier(a = "207", b = "97.33", c = "454.567").resultCalculated()
                )
            )
        )
        mainRepository.subscribeForAllPastCrossMultipliers {
            Assert.assertEquals(
                listOf(
                    CrossMultiplier(a = "", b = "2.3", c = "45.3").resultCalculated(),
                    CrossMultiplier(a = "45", b = "45.33", c = "45.3").resultCalculated(),
                    CrossMultiplier(a = "207", b = "97.33", c = "454.567").resultCalculated()
                ), it)
        }
        runBlocking { mainRepository.clearInput(index = 0, Pair(0, 0)) }
    }

    @Test
    fun `Change the Multiplier's Unknown Position from History`() {
        val mainRepository = MainRepository(
            dataSource = CrossMultiplierDataSource(
                allPastCrossMultipliers = listOf(
                    CrossMultiplier(a = "1", b = "2.3", c = "45.3").resultCalculated(),
                    CrossMultiplier(a = "45", b = "45.33", c = "45.3").resultCalculated(),
                    CrossMultiplier(a = "207", b = "97.33", c = "454.567").resultCalculated()
                )
            )
        )
        mainRepository.subscribeForAllPastCrossMultipliers {
            Assert.assertEquals(
                listOf(
                    CrossMultiplier(a = "1", b = "2.3", c = "45.3").resultCalculated(),
                    CrossMultiplier(
                        a = "",
                        b = "45.33",
                        c = "45.3",
                        unknownPosition = Pair(0, 0)
                    ).resultCalculated(),
                    CrossMultiplier(a = "207", b = "97.33", c = "454.567").resultCalculated()
                ), it)
        }
        runBlocking { mainRepository.changeUnknownPosition(index = 1, Pair(0, 0)) }
    }
}