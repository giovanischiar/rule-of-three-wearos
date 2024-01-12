package io.schiar.ruleofthree.viewmodel

import io.schiar.ruleofthree.model.Numbers
import io.schiar.ruleofthree.view.viewdata.NumbersViewData
import java.text.DecimalFormat

fun Numbers.toViewData(): NumbersViewData {
    return NumbersViewData(
        a = a.toValueString(),
        b = b.toValueString(),
        c = c.toValueString()
    )
}

fun Double?.toFormattedString(): String {
    this ?: return "?"
    return DecimalFormat("#,###.##").format(this)
}