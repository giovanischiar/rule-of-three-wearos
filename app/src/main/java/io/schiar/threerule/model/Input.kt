package io.schiar.threerule.model

data class Input(var value: StringBuilder = StringBuilder()) {
    fun add(newValue: String) {
        val valueNumber = value.toString()
        val newNumber = valueNumber + newValue
        if (newNumber != "." && newNumber.toDoubleOrNull() == null) return

        if (value.isNotEmpty()
            && newValue != "."
            && valueNumber != "."
            && valueNumber.toDouble() == 0.0
            && !value.contains('.')
        ) {
            value.clear()
        }
        value.append(newValue)
    }

    fun remove() {
        if (value.isNotEmpty()) {
            value.deleteCharAt(value.length - 1)
        }
    }

    fun clear() {
        value.clear()
    }

    fun toDoubleOrNull(): Double? {
        return value.toString().toDoubleOrNull()
    }

    fun toValueString(): String {
        return value.toString()
    }
}