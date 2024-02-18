package io.schiar.ruleofthree.viewmodel.util

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.viewmodel.viewdata.CrossMultiplierViewData

fun CrossMultiplier.toViewData(): CrossMultiplierViewData {
    return CrossMultiplierViewData(
        valueAt00 = valueAt00,
        valueAt01 = valueAt01,
        valueAt10 = valueAt10,
        valueAt11 = valueAt11,
        unknownPosition = unknownPosition
    )
}

fun List<CrossMultiplier>.toViewDataList(): List<CrossMultiplierViewData> {
    return map { it.toViewData() }
}