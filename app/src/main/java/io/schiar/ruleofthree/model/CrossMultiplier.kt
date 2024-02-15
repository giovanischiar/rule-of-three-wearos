package io.schiar.ruleofthree.model

import io.schiar.ruleofthree.get
import io.schiar.ruleofthree.not
import io.schiar.ruleofthree.set
import io.schiar.ruleofthree.stringify

data class CrossMultiplier(
    val id: Long = 0,
    val valueAt00: String = "",
    val valueAt01: String = "",
    val valueAt10: String = "",
    val valueAt11: String = "",
    val unknownPosition: Pair<Int, Int> = Pair(1, 1)
) {
    constructor(): this(
        id = 0,
        valueAt00 = "",
        valueAt01 = "",
        valueAt10 = "",
        valueAt11 = "",
        unknownPosition = Pair(1, 1)
    )

    constructor(
        id: Long = 0,
        valueAt00: Number? = null,
        valueAt01: Number? = null,
        valueAt10: Number? = null,
        valueAt11: Number? = null,
        unknownPosition: Pair<Int, Int> = Pair(1, 1)
    ) : this(
        id = id,
        valueAt00 = valueAt00.stringify(),
        valueAt01 = valueAt01.stringify(),
        valueAt10 = valueAt10.stringify(),
        valueAt11 = valueAt11.stringify(),
        unknownPosition = unknownPosition
    )

    private val inputsMatrix = arrayOf(
        arrayOf(Input(value = valueAt00), Input(valueAt01)),
        arrayOf(Input(value = valueAt10), Input(valueAt11))
    )

    val unknownValue: Number? get() {
        return inputsMatrix[unknownPosition].toNumberOrNull()
    }

    private constructor(
        id: Long,
        inputsMatrix: Array<Array<Input>>,
        unknownPosition: Pair<Int, Int>
    ) : this(
        id = id,
        valueAt00 = inputsMatrix[0][0].value,
        valueAt01 = inputsMatrix[0][1].value,
        valueAt10 = inputsMatrix[1][0].value,
        valueAt11 = inputsMatrix[1][1].value,
        unknownPosition = unknownPosition
    )

    private val differentColumnAndRowToUnknownInput: Input get() {
        val (i, j) = unknownPosition
        val position = Pair(!i, !j)
        return inputsMatrix[position]
    }

    private val differentRowSameColumnToUnknownInput: Input get() {
        val (i, j) = unknownPosition
        val position = Pair(!i, j)
        return inputsMatrix[position]
    }

    private val sameRowDifferentColumnToUnknownInput: Input get() {
        val (i, j) = unknownPosition
        val position = Pair(i, !j)
        return inputsMatrix[position]
    }

    private fun inputsMatrixCloned(): Array<Array<Input>> {
        val clonedInputsMatrix = Array(2) { arrayOf(Input(), Input()) }
        clonedInputsMatrix[0][0] = inputsMatrix[0][0]
        clonedInputsMatrix[0][1] = inputsMatrix[0][1]
        clonedInputsMatrix[1][0] = inputsMatrix[1][0]
        clonedInputsMatrix[1][1] = inputsMatrix[1][1]
        return clonedInputsMatrix
    }

    fun characterPushedAt(position: Pair<Int, Int>, character: String): CrossMultiplier {
        val updatedValues = inputsMatrixCloned()
        updatedValues[position] = updatedValues[position].characterPushed(character = character)
        return CrossMultiplier(id = id, inputsMatrix = updatedValues, unknownPosition = unknownPosition)
    }

    fun characterPoppedAt(position: Pair<Int, Int>): CrossMultiplier {
        val updatedValues = inputsMatrixCloned()
        updatedValues[position] = updatedValues[position].characterPopped()
        return CrossMultiplier(id = id, inputsMatrix = updatedValues, unknownPosition = unknownPosition)
    }

    fun unknownPositionChangedTo(position: Pair<Int, Int>): CrossMultiplier {
        val updatedInputsMatrix = inputsMatrixCloned()
        updatedInputsMatrix[unknownPosition] = Input()
        updatedInputsMatrix[position] = Input()
        return CrossMultiplier(
            id = id,
            inputsMatrix = updatedInputsMatrix,
            unknownPosition = position
        )
    }

    fun inputClearedAt(position: Pair<Int, Int>): CrossMultiplier {
        val updatedInputsMatrix : Array<Array<Input>> = inputsMatrixCloned()
        updatedInputsMatrix[position] = inputsMatrix[position].cleared()
        return CrossMultiplier(
            id = id,
            inputsMatrix = updatedInputsMatrix,
            unknownPosition = unknownPosition
        )
    }

    fun allInputsCleared(): CrossMultiplier {
        return CrossMultiplier(
            id = id,
            valueAt00 = "",
            valueAt01 = "",
            valueAt10 = "",
            valueAt11 = "",
            unknownPosition = unknownPosition
        )
    }

    fun isTheResultValid(): Boolean {
        return inputsMatrix[unknownPosition].toNumberOrNull() != null
    }

    fun resultCalculated(): CrossMultiplier {
        if (!inputsMatrix.isValid(unknownPosition = unknownPosition)) return this.inputClearedAt(
            position = unknownPosition
        )
        val updatedInputsMatrix = inputsMatrixCloned()
        updatedInputsMatrix[unknownPosition] =
            (sameRowDifferentColumnToUnknownInput * differentRowSameColumnToUnknownInput) /
                    differentColumnAndRowToUnknownInput
        return CrossMultiplier(
            id = id,
            inputsMatrix = updatedInputsMatrix,
            unknownPosition = unknownPosition
        )
    }

    fun withIDChangedTo(newID: Long): CrossMultiplier {
        return CrossMultiplier(
            id = newID,
            valueAt00 = valueAt00,
            valueAt01 = valueAt01,
            valueAt10 = valueAt10,
            valueAt11 = valueAt11,
            unknownPosition = unknownPosition
        )
    }

    override fun toString(): String {
        var maxLength = -1
        val dataMatrix = inputsMatrix.mapIndexed { i, valuesRow ->
            valuesRow.mapIndexed { j, input: Input ->
                if (Pair(i, j) == unknownPosition) {
                    if (input.toNumberOrNull() == null) "?" else input.value
                } else {
                    input.value
                }
            }
        }

        val data = listOf(dataMatrix[0][0], dataMatrix[0][1], dataMatrix[1][0], dataMatrix[1][1])
        for (datum in data) { if (datum.length > maxLength) { maxLength = datum.length } }
        val adjustedData = data.map {
            val builder = StringBuilder(it)
            var i = 0
            while (i++ < maxLength - it.length) { builder.append(" ") }
            builder.toString()
        }

        return "\n${adjustedData[0]} ${adjustedData[1]}\n" +
                "${adjustedData[2]} ${adjustedData[3]}\n"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CrossMultiplier

        if (!inputsMatrix.contentDeepEquals(other.inputsMatrix)) return false
        return unknownPosition == other.unknownPosition
    }

    override fun hashCode(): Int {
        var result = inputsMatrix.contentDeepHashCode()
        result = 31 * result + unknownPosition.hashCode()
        return result
    }
}