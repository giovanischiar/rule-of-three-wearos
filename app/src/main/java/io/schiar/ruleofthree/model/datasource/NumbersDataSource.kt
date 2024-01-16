package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.Numbers

class NumbersDataSource(
    private var allPastNumbers: List<Numbers> = emptyList(),
    private var currentNumbers: Numbers = Numbers()
): DataSource {
    override suspend fun requestCurrentNumbers(): Numbers {
        return currentNumbers
    }

    override suspend fun updateCurrentNumbers(numbers: Numbers) {
        currentNumbers = numbers
    }

    override suspend fun requestAllPastNumbers(): List<Numbers> {
        return allPastNumbers
    }

    override suspend fun addToAllPastNumbers(numbers: Numbers) {
        allPastNumbers = listOf(numbers) + allPastNumbers
    }
}