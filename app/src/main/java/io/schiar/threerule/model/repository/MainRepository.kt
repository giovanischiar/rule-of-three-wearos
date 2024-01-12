package io.schiar.threerule.model.repository

import io.schiar.threerule.model.Numbers

class MainRepository: NumbersRepository {
    private var numbersCallback = { _: Numbers -> }
    private var resultCallback = { _: Double? -> }
    private var numbers = Numbers()

    override fun subscribeForNumbers(callback: (numbers: Numbers) -> Unit) {
        this.numbersCallback = callback
    }

    override fun subscribeForResult(callback: (result: Double?) -> Unit) {
        this.resultCallback = callback
    }

    override fun addToInput(value: String, position: Int) {
        numbers.addToInput(value = value, position = position)
        numbersCallback(numbers)
        resultCallback(numbers.calculateResult())
    }

    override fun removeFromInput(position: Int) {
        numbers.removeFromInput(position = position)
        numbersCallback(numbers)
        resultCallback(numbers.calculateResult())
    }

    override fun clearInput(position: Int) {
        numbers.clear(position = position)
        numbersCallback(numbers)
        resultCallback(numbers.calculateResult())
    }
}