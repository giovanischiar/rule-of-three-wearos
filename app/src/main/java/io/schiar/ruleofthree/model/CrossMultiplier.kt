package io.schiar.ruleofthree.model

import io.schiar.ruleofthree.get
import io.schiar.ruleofthree.not
import io.schiar.ruleofthree.set

data class CrossMultiplier(
    val id: Long = 0,
    val values: Array<Array<Input>> = arrayOf(
        arrayOf(Input(), Input()),
        arrayOf(Input(), Input())
    ),
    val unknownPosition: Pair<Int, Int> = Pair(1, 1)
) {
    constructor() : this (
        values = arrayOf(
            arrayOf(Input(), Input()),
            arrayOf(Input(), Input())
        ),
        unknownPosition = Pair(1, 1)
    )

    constructor(
        a: String = "",
        b: String = "",
        c: String = "",
        unknownPosition: Pair<Int, Int> = Pair(1, 1)
    ): this(
        values = arrayOf(
            arrayOf(Input(value = a), Input(value = b)),
            arrayOf(Input(value = c), Input())
        ),
        unknownPosition = unknownPosition
    )

    fun isTheResultValid(): Boolean {
        return values[unknownPosition].toDoubleOrNull() != null
    }

    fun a(): Input { return values[aPosition()] }

    fun b(): Input { return values[bPosition()] }

    fun c(): Input { return values[cPosition()] }

    fun result(): Double? { return values[unknownPosition].toDoubleOrNull() }

    fun resultCalculated(): CrossMultiplier {
        if (!values.isValid(unknownPosition = unknownPosition)) return this
        val newValues = copyValues()
        newValues[unknownPosition] = (c() * b()) / a()
        return CrossMultiplier(id = id, values = newValues, unknownPosition = unknownPosition)
    }

    fun characterPushedAt(position: Pair<Int, Int>, character: String): CrossMultiplier {
        val updatedValues = copyValues()
        updatedValues[position] = updatedValues[position].add(newValue = character)
        return CrossMultiplier(id = id, values = updatedValues, unknownPosition = unknownPosition)
    }

    fun characterPoppedAt(position: Pair<Int, Int>): CrossMultiplier {
        val updatedValues = copyValues()
        updatedValues[position] = updatedValues[position].remove()
        return CrossMultiplier(id = id, values = updatedValues, unknownPosition = unknownPosition)
    }

    fun inputClearedAt(position: Pair<Int, Int>): CrossMultiplier {
        val updatedValues : Array<Array<Input>> = copyValues()
        updatedValues[position] = updatedValues[position].clear()
        return CrossMultiplier(id = id, values = updatedValues, unknownPosition = unknownPosition)
    }

    fun unknownPositionChangedTo(position: Pair<Int, Int>): CrossMultiplier {
        val newValues = copyValues()
        newValues[position] = Input()
        return CrossMultiplier(
            id = id,
            values = newValues,
            unknownPosition = position
        ).resultCalculated()
    }

    fun allInputsCleared(): CrossMultiplier {
        return CrossMultiplier(id = id, unknownPosition = unknownPosition)
    }

    private fun aPosition(): Pair<Int, Int> { val (i, j) = unknownPosition; return Pair(!i, !j) }

    private fun bPosition(): Pair<Int, Int> { val (i, j) = unknownPosition; return Pair(!i, j) }

    private fun cPosition(): Pair<Int, Int> { val (i, j) = unknownPosition; return Pair(i, !j) }

    private fun copyValues(): Array<Array<Input>> {
        val clonedValues = Array(2) { arrayOf(Input(), Input()) }
        clonedValues[aPosition()] = values[aPosition()]
        clonedValues[bPosition()] = values[bPosition()]
        clonedValues[cPosition()] = values[cPosition()]
        return clonedValues
    }

    override fun toString(): String {
        var maxLength = -1
        val data = listOf(a().value, b().value, c().value, (result() ?: "?").toString())
        for (datum in data) { if (datum.length > maxLength) { maxLength = datum.length } }
        val adjustedData = data.map {
            val builder = StringBuilder(it)
            var i = 0
            while (i++ < maxLength - it.length) { builder.append(" ") }
            builder.toString()
        }

        return "\n${adjustedData[0]}\t${adjustedData[1]}\n" +
                "${adjustedData[2]}\t${adjustedData[3]}\n"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CrossMultiplier

        if (!values.contentDeepEquals(other.values)) return false
        return unknownPosition == other.unknownPosition
    }

    override fun hashCode(): Int {
        var result = values.contentDeepHashCode()
        result = 31 * result + unknownPosition.hashCode()
        return result
    }
}