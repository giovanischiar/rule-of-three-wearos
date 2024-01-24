package io.schiar.ruleofthree.viewmodel.util

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.Input
import io.schiar.ruleofthree.stringify
import io.schiar.ruleofthree.view.viewdata.CrossMultiplierViewData

fun CrossMultiplier.toViewData(): CrossMultiplierViewData {
    return CrossMultiplierViewData(
        values = arrayOf(
            arrayOf(valueAt00, valueAt01),
            arrayOf(valueAt10, valueAt11)
        ),
        unknownPosition = unknownPosition
    )
}