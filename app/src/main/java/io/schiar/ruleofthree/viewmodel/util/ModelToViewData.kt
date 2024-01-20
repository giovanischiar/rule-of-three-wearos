package io.schiar.ruleofthree.viewmodel.util

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.Input
import io.schiar.ruleofthree.view.viewdata.CrossMultiplierViewData

fun CrossMultiplier.toViewData(): CrossMultiplierViewData {
    return CrossMultiplierViewData(values = values.toViewData(), unknownPosition = unknownPosition)
}

fun Array<Array<Input>>.toViewData(): Array<Array<String>> {
    val newArray = Array(this.size) { arrayOf("", "") }
    for (i in indices) {
        for (j in this[i].indices) {
            newArray[i][j] = this[i][j].value
        }
    }
    return newArray
}