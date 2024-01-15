package io.schiar.ruleofthree.view

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import kotlin.math.sqrt

fun calculateTextUnitBasedOn(length: Int, baseSize: Int = 30): TextUnit {
    val ret = (baseSize * sqrt(x = (1.0/length))).sp
    return if (ret >= 14.sp) ret else 14.sp
}