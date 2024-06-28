package io.schiar.ruleofthree.model

import io.schiar.ruleofthree.stringify

data class Input(val value: String = "") {
    constructor(value: Number?): this(value = value.stringify())

    fun characterPushed(character: String): Input {
        val valueBuilder = StringBuilder(value)
        val newNumber = value + character
        if (newNumber != "." &&
            newNumber != "," &&
            newNumber.toDoubleLocaleOrNull() == null
        ) return this

        if (valueBuilder.isNotEmpty()
            && value != "."
            && value != ","
            && value.toDoubleLocaleOrNull() == 0.0
            && !newNumber.hasDecimalSeparator()
        ) {
            valueBuilder.clear()
        }
        valueBuilder.append(character)
        return Input(value = valueBuilder.toString())
    }

    fun characterPopped(): Input {
        val valueBuilder = StringBuilder(value)
        if (valueBuilder.isNotEmpty()) {
            valueBuilder.deleteCharAt(value.length - 1)
        }
        return Input(value = valueBuilder.toString())
    }

    fun cleared(): Input {
        val valueBuilder = StringBuilder(value)
        valueBuilder.clear()
        return Input(value = valueBuilder.toString())
    }

    override fun toString(): String { return value }

    operator fun times(other: Input): Input {
        val thisDoubleValue = this.value.toDoubleLocaleOrNull() ?: return this
        val otherDoubleValue = other.value.toDoubleLocaleOrNull() ?: return this
        return Input(value = (thisDoubleValue * otherDoubleValue))
    }

    operator fun div(other: Input): Input {
        val thisDoubleValue = this.value.toDoubleLocaleOrNull() ?: return this
        val otherDoubleValue = other.value.toDoubleLocaleOrNull() ?: return this
        if (otherDoubleValue == 0.0) return this
        return Input(value = (thisDoubleValue / otherDoubleValue))
    }

    fun toNumberOrNull(): Number? {
        return if (value.hasDecimalSeparator()) {
            value.toDoubleLocaleOrNull()
        } else {
            value.toIntOrNull()
        }
    }
}