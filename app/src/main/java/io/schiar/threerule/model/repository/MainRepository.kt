package io.schiar.threerule.model.repository

import io.schiar.threerule.model.Numbers

class MainRepository: Repository {
    private var numbers = Numbers()
    private var callback = { _: Double? -> }

    override fun subscribeForResult(callback: (result: Double?) -> Unit) {
        this.callback = callback
    }

    override fun inputNumber(number: Double?, position: Int) {
        when(position) {
            0 -> numbers = Numbers(a = number, b = numbers.b, c = numbers.c)
            1 -> numbers = Numbers(a = numbers.a, b = number, c = numbers.c)
            2 -> numbers = Numbers(a = numbers.a, b = numbers.b, c = number)
        }
        callback(numbers.calculateD())
    }
}