package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.Numbers
import io.schiar.ruleofthree.model.datasource.DataSource
import io.schiar.ruleofthree.model.datasource.NumbersDataSource

class MainRepository(
    private val dataSource: DataSource = NumbersDataSource()
): AppRepository, NumbersRepository, HistoryRepository {
    private var numbersCallback = { _: Numbers -> }
    private var allPastNumbersCallback = { _: List<Numbers> -> }

    // AppRepository

    override suspend fun loadDatabase() {
        numbersCallback(dataSource.requestCurrentNumbers())
        allPastNumbersCallback(dataSource.requestAllPastNumbers())
    }

    // NumbersRepository

    override fun subscribeForNumbers(callback: (numbers: Numbers) -> Unit) {
        this.numbersCallback = callback
    }

    override suspend fun addToInput(value: String, position: Int) {
        val numbers = dataSource
            .requestCurrentNumbers()
            .addToInput(value = value, position = position)
            .resultCalculated()
        dataSource.updateCurrentNumbers(numbers = numbers)
        numbersCallback(numbers)
    }

    override suspend fun removeFromInput(position: Int) {
        val numbers = dataSource
            .requestCurrentNumbers()
            .removeFromInput(position = position)
            .resultCalculated()
        dataSource.updateCurrentNumbers(numbers = numbers)
        numbersCallback(numbers)
    }

    override suspend fun clearInput(position: Int) {
        val numbers = dataSource
            .requestCurrentNumbers()
            .clear(position = position)
            .resultCalculated()
        dataSource.updateCurrentNumbers(numbers = numbers)
        numbersCallback(numbers)
    }

    override suspend fun submitToHistory() {
        val numbers = dataSource.requestCurrentNumbers()
        if (numbers.result != null) {
            dataSource.updateCurrentNumbers(numbers)
            dataSource.addToAllPastNumbers(numbers)
            allPastNumbersCallback(dataSource.requestAllPastNumbers())
        }
    }

    // HistoryRepository

    override fun subscribeForAllPastNumbers(callback: (allPastNumbers: List<Numbers>) -> Unit) {
        allPastNumbersCallback = callback
    }
}