package io.schiar.ruleofthree.model

import java.lang.StringBuilder

data class Numbers(
    val a: Input = Input(),
    val b: Input = Input(),
    val c: Input = Input()
) {
    constructor() : this (a = Input(), b = Input(), c = Input())
    constructor(a: String = "", b: String = "", c: String = ""): this(
        a = Input(value = StringBuilder(a)),
        b = Input(value = StringBuilder(b)),
        c = Input(value = StringBuilder(c))
    )

    fun calculateResult(): Double? {
        val a = a.toDoubleOrNull() ?: return null
        val b = b.toDoubleOrNull() ?: return null
        val c = c.toDoubleOrNull() ?: return null
        if (a == 0.0) return null
        return (c * b) / a
    }

    fun addToInput(value: String, position: Int) {
        when(position) {
            0 -> a.add(newValue = value)
            1 -> b.add(newValue = value)
            2 -> c.add(newValue = value)
        }
    }

    fun removeFromInput(position: Int) {
        when(position) {
            0 -> a.remove()
            1 -> b.remove()
            2 -> c.remove()
        }
    }

    fun clear(position: Int) {
        when(position) {
            0 -> a.clear()
            1 -> b.clear()
            2 -> c.clear()
        }
    }
}