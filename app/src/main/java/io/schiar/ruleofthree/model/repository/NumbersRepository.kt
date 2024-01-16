package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.Numbers

interface NumbersRepository {
    fun requestCurrentNumbers(): Numbers
    fun subscribeForNumbers(callback: (numbers: Numbers) -> Unit)
    fun subscribeForResult(callback: (result: Double?) -> Unit)
    fun addToInput(value: String, position: Int)
    fun submitToHistory()
    fun removeFromInput(position: Int)
    fun clearInput(position: Int)
}