package io.schiar.ruleofthree.viewmodel.viewdata

import io.schiar.ruleofthree.get
import io.schiar.ruleofthree.not
import io.schiar.ruleofthree.view.shared.util.toFormattedString

data class CrossMultiplierViewData(
    val valueAt00: String = "",
    val valueAt01: String = "",
    val valueAt10: String = "",
    val valueAt11: String = "",
    val unknownPosition: Pair<Int, Int> = Pair(1, 1)
) {
    val values: Array<Array<String>> get() = arrayOf(
        arrayOf(valueAt00, valueAt01),
        arrayOf(valueAt10, valueAt11)
    )

    val result: ResultViewData = ResultViewData(
        result = if (values[unknownPosition].isNotEmpty()) {
            values[unknownPosition].toDoubleOrNull().toFormattedString()
        } else {
            "?"
        },
        _result = values[unknownPosition].toDoubleOrNull() ?: 0.0
    )

    fun isNotEmpty(): Boolean {
        val (i, j) = unknownPosition
        return values[!i][j].isNotEmpty() ||
               values[i][!j].isNotEmpty() ||
               values[!i][!j].isNotEmpty()
    }
}
