package io.schiar.ruleofthree.view.shared.util

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import java.text.DecimalFormat
import kotlin.math.sqrt

fun calculateTextUnitBasedOn(length: Int, baseSize: Int = 30): TextUnit {
    val ret = (baseSize * sqrt(x = (1.0/length))).sp
    return if (ret >= 14.sp) ret else 14.sp
}

fun Double?.toFormattedString(decimals: Int = 2): String {
    this ?: return "?"
    val patternBuilder = StringBuilder("#,###.")
    for (i in 1..decimals) { patternBuilder.append("#") }
    return DecimalFormat(patternBuilder.toString()).format(this)
}