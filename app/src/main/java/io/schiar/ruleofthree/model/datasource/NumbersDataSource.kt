package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.Numbers
import java.lang.StringBuilder

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
        allPastNumbers = listOf(numbers.copy(
            a = numbers.a.copy(value = StringBuilder(numbers.a.value.toString())),
            b = numbers.b.copy(value = StringBuilder(numbers.b.value.toString())),
            c = numbers.c.copy(value = StringBuilder(numbers.c.value.toString())),
            result = numbers.result
        )) + allPastNumbers
    }
}