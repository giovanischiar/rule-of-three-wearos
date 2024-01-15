package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.Numbers
import io.schiar.ruleofthree.model.datasource.DataSource
import io.schiar.ruleofthree.model.datasource.NumbersDataSource

class MainRepository(
    private val dataSource: DataSource = NumbersDataSource()
): NumbersRepository, HistoryRepository {
    private var numbersCallback = { _: Numbers -> }
    private var resultCallback = { _: Double? -> }
    private var allPastNumbersCallback = { _: List<Numbers> -> }

    override fun requestCurrentNumbers(): Numbers {
        return dataSource.requestCurrentNumbers()
    }

    override fun subscribeForNumbers(callback: (numbers: Numbers) -> Unit) {
        this.numbersCallback = callback
    }

    override fun subscribeForResult(callback: (result: Double?) -> Unit) {
        this.resultCallback = callback
    }

    private fun notifyListeners() {
        val numbers = dataSource.requestCurrentNumbers()
        numbersCallback(numbers)
        resultCallback(numbers.calculateResult())
    }

    override fun addToInput(value: String, position: Int) {
        val numbers = dataSource.requestCurrentNumbers()
        numbers.addToInput(value = value, position = position)
        dataSource.updateCurrentNumbers(numbers = numbers)
        notifyListeners()
    }

    override fun removeFromInput(position: Int) {
        val numbers = dataSource.requestCurrentNumbers()
        numbers.removeFromInput(position = position)
        notifyListeners()
    }

    override fun clearInput(position: Int) {
        val numbers = dataSource.requestCurrentNumbers()
        numbers.clear(position = position)
        notifyListeners()
    }

    override fun submitToHistory() {
        val numbers = dataSource.requestCurrentNumbers()
        if (numbers.result != null) {
            dataSource.addToAllPastNumbers(numbers)
            allPastNumbersCallback(dataSource.requestAllPastNumbers())
        }
    }

    override fun requestAllPastNumbers(): List<Numbers> {
        return dataSource.requestAllPastNumbers()
    }

    override fun subscribeForAllPastNumbers(callback: (allPastNumbers: List<Numbers>) -> Unit) {
        allPastNumbersCallback = callback
    }
}