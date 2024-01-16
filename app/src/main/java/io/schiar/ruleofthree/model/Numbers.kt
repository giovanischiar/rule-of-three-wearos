package io.schiar.ruleofthree.model

data class Numbers(
    val a: Input = Input(),
    val b: Input = Input(),
    val c: Input = Input(),
    var result: Double? = null
) {
    constructor() : this (a = Input(), b = Input(), c = Input())
    constructor(a: String = "", b: String = "", c: String = ""): this(
        a = Input(value = a),
        b = Input(value = b),
        c = Input(value = c)
    )

    fun calculateResult(): Double? {
        val a = a.toDoubleOrNull() ?: return null
        val b = b.toDoubleOrNull() ?: return null
        val c = c.toDoubleOrNull() ?: return null
        if (a == 0.0) return null
        result = (c * b) / a
        return result
    }

    fun addToInput(value: String, position: Int): Numbers {
        return when(position) {
            0 -> Numbers(a = a.add(newValue = value), b = b, c = c)
            1 -> Numbers(a = a, b = b.add(newValue = value), c = c)
            else -> Numbers(a = a, b = b, c = c.add(newValue = value))
        }
    }

    fun removeFromInput(position: Int): Numbers {
        return when(position) {
            0 -> Numbers(a = a.remove(), b = b, c = c)
            1 -> Numbers(a = a, b = b.remove(), c = c)
            else -> Numbers(a = a, b = b, c = c.remove())
        }
    }

    fun clear(position: Int): Numbers {
        return when(position) {
            0 -> Numbers(a = a.clear(), b = b, c = c)
            1 -> Numbers(a = a, b = b.clear(), c = c)
            else -> Numbers(a = a, b = b, c = c.clear())
        }
    }
}