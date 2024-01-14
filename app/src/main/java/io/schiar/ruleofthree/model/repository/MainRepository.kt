package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.Numbers
import io.schiar.ruleofthree.model.datasource.DataSource
import io.schiar.ruleofthree.model.datasource.NumbersDataSource

class MainRepository(private val dataSource: DataSource = NumbersDataSource()): NumbersRepository {
    private var numbersCallback = { _: Numbers -> }
    private var resultCallback = { _: Double? -> }

    override fun requestCurrentNumbers(): Numbers {
        return dataSource.requestCurrentNumbers()
    }

    override fun subscribeForNumbers(callback: (numbers: Numbers) -> Unit) {
        this.numbersCallback = callback
    }

    override fun subscribeForResult(callback: (result: Double?) -> Unit) {
        this.resultCallback = callback
    }

    private fun callbacks(numbers: Numbers) {
        numbersCallback(numbers)
        resultCallback(numbers.calculateResult())
    }

    override fun addToInput(value: String, position: Int) {
        val numbers = dataSource.requestCurrentNumbers()
        numbers.addToInput(value = value, position = position)
        dataSource.updateCurrentNumbers(numbers = numbers)
        callbacks(numbers = numbers)
    }

    override fun removeFromInput(position: Int) {
        val numbers = dataSource.requestCurrentNumbers()
        numbers.removeFromInput(position = position)
        callbacks(numbers = numbers)
    }

    override fun clearInput(position: Int) {
        val numbers = dataSource.requestCurrentNumbers()
        numbers.clear(position = position)
        callbacks(numbers = numbers)
    }
}