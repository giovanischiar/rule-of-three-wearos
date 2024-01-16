package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.Numbers

class NumbersDataSource(
    private var allPastNumbers: List<Numbers> = emptyList(),
    private var currentNumbers: Numbers = Numbers()
): DataSource {
    override fun requestCurrentNumbers(): Numbers {
        return currentNumbers
    }

    override fun updateCurrentNumbers(numbers: Numbers) {
        currentNumbers = numbers
    }

    override fun requestAllPastNumbers(): List<Numbers> {
        return allPastNumbers
    }

    override fun addToAllPastNumbers(numbers: Numbers) {
        allPastNumbers = listOf(numbers) + allPastNumbers
    }
}