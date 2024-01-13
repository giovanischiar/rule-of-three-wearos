package io.schiar.ruleofthree.view

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import kotlin.math.sqrt

fun calculateTextUnitBasedOn(length: Int): TextUnit {
    val ret = (34 * sqrt(x = (1.0/length))).sp
    return if (ret >= 14.sp) ret else 14.sp
}