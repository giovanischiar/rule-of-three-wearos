package io.schiar.ruleofthree.model

data class CrossMultiplier(
    val a: Input = Input(),
    val b: Input = Input(),
    val c: Input = Input(),
    val result: Double? = null
) {
    constructor() : this (a = Input(), b = Input(), c = Input())
    constructor(a: String = "", b: String = "", c: String = ""): this(
        a = Input(value = a),
        b = Input(value = b),
        c = Input(value = c)
    )

    fun resultCalculated(): CrossMultiplier {
        val a = a.toDoubleOrNull() ?: return this
        val b = b.toDoubleOrNull() ?: return this
        val c = c.toDoubleOrNull() ?: return this
        if (a == 0.0) return this
        return CrossMultiplier(a = this.a, b = this.b, c = this.c, result = (c * b) / a)
    }

    fun addToInput(value: String, position: Int): CrossMultiplier {
        return when(position) {
            0 -> CrossMultiplier(a = a.add(newValue = value), b = b, c = c)
            1 -> CrossMultiplier(a = a, b = b.add(newValue = value), c = c)
            else -> CrossMultiplier(a = a, b = b, c = c.add(newValue = value))
        }
    }

    fun removeFromInput(position: Int): CrossMultiplier {
        return when(position) {
            0 -> CrossMultiplier(a = a.remove(), b = b, c = c)
            1 -> CrossMultiplier(a = a, b = b.remove(), c = c)
            else -> CrossMultiplier(a = a, b = b, c = c.remove())
        }
    }

    fun clear(position: Int): CrossMultiplier {
        return when(position) {
            0 -> CrossMultiplier(a = a.clear(), b = b, c = c)
            1 -> CrossMultiplier(a = a, b = b.clear(), c = c)
            else -> CrossMultiplier(a = a, b = b, c = c.clear())
        }
    }

    fun clearAll(): CrossMultiplier {
        return CrossMultiplier()
    }
}