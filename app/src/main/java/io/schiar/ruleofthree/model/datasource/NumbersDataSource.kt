package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.Numbers

class NumbersDataSource: DataSource {
    private val pastNumbers = mutableListOf(Numbers())

    override fun requestCurrentNumbers(): Numbers {
        return pastNumbers.last()
    }

    override fun updateCurrentNumbers(numbers: Numbers) {
        pastNumbers.removeLast()
        pastNumbers.add(numbers)
    }
}