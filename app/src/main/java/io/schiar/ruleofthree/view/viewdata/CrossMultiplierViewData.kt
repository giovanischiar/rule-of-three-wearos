package io.schiar.ruleofthree.view.viewdata

import io.schiar.ruleofthree.get
import io.schiar.ruleofthree.not
import io.schiar.ruleofthree.view.toFormattedString

data class CrossMultiplierViewData(
    val values: Array<Array<String>> = arrayOf(arrayOf("", ""), arrayOf("", "")),
    val unknownPosition: Pair<Int, Int> = Pair(1, 1),
    val result: ResultViewData = ResultViewData(
        result = if (values[unknownPosition].isNotEmpty()) {
            values[unknownPosition].toDouble().toFormattedString()
        } else {
            "?"
        },
        _result = values[unknownPosition].toDoubleOrNull() ?: 0.0
    )
) {
    fun isNotEmpty(): Boolean {
        val (i, j) = unknownPosition
        return values[!i][j].isNotEmpty() ||
               values[i][!j].isNotEmpty() ||
               values[!i][!j].isNotEmpty()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CrossMultiplierViewData

        if (!values.contentDeepEquals(other.values)) return false
        return unknownPosition == other.unknownPosition
    }

    override fun hashCode(): Int {
        var result = values.contentDeepHashCode()
        result = 31 * result + unknownPosition.hashCode()
        return result
    }
}
