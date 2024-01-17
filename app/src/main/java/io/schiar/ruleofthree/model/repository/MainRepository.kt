package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.Numbers
import io.schiar.ruleofthree.model.datasource.DataSource
import io.schiar.ruleofthree.model.datasource.NumbersDataSource
import io.schiar.ruleofthree.model.datasource.util.NumbersLocalDAO

class MainRepository(
    private val dataSource: DataSource = NumbersDataSource(numbersDAO = NumbersLocalDAO())
): AppRepository, NumbersRepository, HistoryRepository {
    private var numbersCallback = { _: Numbers -> }
    private var isThereHistoryCallback = { _: Boolean -> }
    private var allPastNumbersCallback = { _: List<Numbers> -> }

    // AppRepository

    override suspend fun loadDatabase() {
        numbersCallback(dataSource.requestCurrentNumbers())
        val allPastNumbers = dataSource.requestAllPastNumbers()
        allPastNumbersCallback(allPastNumbers)
        isThereHistoryCallback(allPastNumbers.isNotEmpty())
    }

    // NumbersRepository

    override fun subscribeForNumbers(callback: (numbers: Numbers) -> Unit) {
        this.numbersCallback = callback
    }

    override fun subscribeForIsThereHistory(callback: (value: Boolean) -> Unit) {
        isThereHistoryCallback = callback
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
            val allPastNumbers = dataSource.requestAllPastNumbers()
            allPastNumbersCallback(allPastNumbers)
            isThereHistoryCallback(allPastNumbers.isNotEmpty())
        }
    }

    override suspend fun clearAllInputs() {
        val numbers = dataSource
            .requestCurrentNumbers()
            .clearAll()
        dataSource.updateCurrentNumbers(numbers = numbers)
        numbersCallback(numbers)
    }

    // HistoryRepository

    override fun subscribeForAllPastNumbers(callback: (allPastNumbers: List<Numbers>) -> Unit) {
        allPastNumbersCallback = callback
    }
}