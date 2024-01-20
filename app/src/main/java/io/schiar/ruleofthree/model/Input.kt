package io.schiar.ruleofthree.model

data class Input(val value: String = "") {
    constructor(value: Double): this(value = value.toString())
    constructor(value: Int): this(value = value.toString())

    fun add(newValue: String): Input {
        val valueBuilder = StringBuilder(value)
        val newNumber = value + newValue
        if (newNumber != "." && newNumber.toDoubleOrNull() == null) return this

        if (valueBuilder.isNotEmpty()
            && value != "."
            && value.toDouble() == 0.0
            && !newNumber.contains('.')
        ) {
            valueBuilder.clear()
        }
        valueBuilder.append(newValue)
        return Input(value = valueBuilder.toString())
    }

    fun remove(): Input {
        val valueBuilder = StringBuilder(value)
        if (valueBuilder.isNotEmpty()) {
            valueBuilder.deleteCharAt(value.length - 1)
        }
        return Input(value = valueBuilder.toString())
    }

    fun clear(): Input {
        val valueBuilder = StringBuilder(value)
        valueBuilder.clear()
        return Input(value = valueBuilder.toString())
    }

    fun toDoubleOrNull(): Double? {
        return value.toDoubleOrNull()
    }

    override fun toString(): String { return value }

    operator fun times(other: Input): Input {
        val thisDoubleValue = this.value.toDoubleOrNull() ?: return this
        val otherDoubleValue = other.value.toDoubleOrNull() ?: return this
        return Input(value = (thisDoubleValue * otherDoubleValue))
    }

    operator fun div(other: Input): Input {
        val thisDoubleValue = this.value.toDoubleOrNull() ?: return this
        val otherDoubleValue = other.value.toDoubleOrNull() ?: return this
        if (otherDoubleValue == 0.0) return this
        return Input(value = (thisDoubleValue / otherDoubleValue))
    }
}