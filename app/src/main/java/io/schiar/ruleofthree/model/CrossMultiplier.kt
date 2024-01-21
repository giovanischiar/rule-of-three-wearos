package io.schiar.ruleofthree.model

import io.schiar.ruleofthree.get
import io.schiar.ruleofthree.not
import io.schiar.ruleofthree.set

data class CrossMultiplier(
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

    constructor(a: Input, b: Input, c: Input): this(a = a.value, b = b.value, c = c.value)

    constructor(a: Input, b: Input, c: Input, result: Double?): this(
        values = arrayOf(
            arrayOf(a, b),
            arrayOf(c, if (result != null) Input(value = result) else Input() )
        ),
        unknownPosition = Pair(1, 1)
    )

    constructor(
        a: String = "",
        b: String = "",
        c: String = "",
        result: Double? = null,
        unknownPosition: Pair<Int, Int>
    ) : this(
        values = arrayOf(
            arrayOf(Input(value = a), Input(value = b)),
            arrayOf(Input(value = c), Input(value = result?.toString() ?: ""))
        ),
        unknownPosition = unknownPosition
    )

    constructor(a: String = "", b: String = "", c: String = ""): this(
        values = arrayOf(
            arrayOf(Input(value = a), Input(value = b)),
            arrayOf(Input(value = c), Input())
        ),
        unknownPosition = Pair(1, 1)
    )

    fun a(): Input { return values[aPosition()] }

    fun b(): Input { return values[bPosition()] }

    fun c(): Input { return values[cPosition()] }

    fun result(): Double? { return values[unknownPosition].toDoubleOrNull() }

    fun unknownPositionChangedTo(newPosition: Pair<Int, Int>): CrossMultiplier {
        val (newI, newJ) = newPosition
        val newValues = copyValues()
        newValues[newI][newJ] = Input()
        return CrossMultiplier(values = newValues, unknownPosition = newPosition).resultCalculated()
    }

    fun resultCalculated(): CrossMultiplier {
        if (!values.isValid(unknownPosition = unknownPosition)) return this
        val newValues = copyValues()
        newValues[unknownPosition] = (c() * b()) / a()
        return CrossMultiplier(values = newValues, unknownPosition = unknownPosition)
    }

    fun addToInput(value: String, position: Pair<Int, Int>): CrossMultiplier {
        val (i, j) = position
        val updatedValues = copyValues()
        updatedValues[i][j] = updatedValues[i][j].add(newValue = value)
        return CrossMultiplier(values = updatedValues, unknownPosition = unknownPosition)
    }

    fun removeFromInput(position: Pair<Int, Int>): CrossMultiplier {
        val (i, j) = position
        val updatedValues = copyValues()
        updatedValues[i][j] = updatedValues[i][j].remove()
        return CrossMultiplier(values = updatedValues, unknownPosition = unknownPosition)
    }

    fun clear(position: Pair<Int, Int>): CrossMultiplier {
        val (i, j) = position
        val updatedValues : Array<Array<Input>> = copyValues()
        updatedValues[i][j] = updatedValues[i][j].clear()
        return CrossMultiplier(values = updatedValues, unknownPosition = unknownPosition)
    }

    fun clearAll(): CrossMultiplier {
        return CrossMultiplier(unknownPosition = unknownPosition)
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
        return "\n${a()}\t${b()}\n" +
               "${c()}\t${result() ?: "?"}\n"
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