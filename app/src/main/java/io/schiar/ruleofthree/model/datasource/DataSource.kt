package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.Numbers

interface DataSource {
    fun requestCurrentNumbers(): Numbers
    fun updateCurrentNumbers(numbers: Numbers)
}