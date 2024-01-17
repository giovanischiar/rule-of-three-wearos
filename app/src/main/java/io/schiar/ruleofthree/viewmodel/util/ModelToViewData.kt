package io.schiar.ruleofthree.viewmodel.util

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.view.viewdata.CrossMultiplierViewData
import java.text.DecimalFormat

fun CrossMultiplier.toViewData(): CrossMultiplierViewData {
    return CrossMultiplierViewData(
        a = a.value,
        b = b.value,
        c = c.value,
        result = result.toFormattedString()
    )
}

fun Double?.toFormattedString(): String {
    this ?: return "?"
    return DecimalFormat("#,###.##").format(this)
}