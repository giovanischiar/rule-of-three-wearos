package io.schiar.threerule.model.repository

interface Repository {
    fun subscribeForResult(callback: (result: Double?) -> Unit)
    fun inputNumber(number: Double?, position: Int)
}