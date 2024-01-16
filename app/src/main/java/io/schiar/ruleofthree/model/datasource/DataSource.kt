package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.Numbers

interface DataSource {
    suspend fun requestCurrentNumbers(): Numbers
    suspend fun updateCurrentNumbers(numbers: Numbers)
    suspend fun requestAllPastNumbers(): List<Numbers>
    suspend fun addToAllPastNumbers(numbers: Numbers)
}