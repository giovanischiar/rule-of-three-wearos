package io.schiar.ruleofthree.viewmodel.util

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.view.viewdata.CrossMultiplierViewData
import io.schiar.ruleofthree.view.viewdata.ResultViewData
import java.text.DecimalFormat

fun CrossMultiplier.toViewData(): CrossMultiplierViewData {
    return CrossMultiplierViewData(
        a = a.value,
        b = b.value,
        c = c.value,
        result = ResultViewData(result = result.toFormattedString(), _result = result ?: 0.0)
    )
}

fun Double?.toFormattedString(decimals: Int = 2): String {
    this ?: return "?"
    val patternBuilder = StringBuilder("#,###.")
    for (i in 1..decimals) { patternBuilder.append("#") }
    return DecimalFormat(patternBuilder.toString()).format(this)
}