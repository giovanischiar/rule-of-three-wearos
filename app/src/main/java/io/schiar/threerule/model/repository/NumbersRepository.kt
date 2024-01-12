package io.schiar.threerule.model.repository

import io.schiar.threerule.model.Numbers

interface NumbersRepository {
    fun subscribeForNumbers(callback: (numbers: Numbers) -> Unit)
    fun subscribeForResult(callback: (result: Double?) -> Unit)
    fun addToInput(value: String, position: Int)
    fun removeFromInput(position: Int)
    fun clearInput(position: Int)
}