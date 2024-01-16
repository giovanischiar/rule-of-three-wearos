package io.schiar.ruleofthree.viewmodel.util

import io.schiar.ruleofthree.model.Numbers
import io.schiar.ruleofthree.view.viewdata.NumbersViewData
import java.text.DecimalFormat

fun Numbers.toViewData(): NumbersViewData {
    return NumbersViewData(
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