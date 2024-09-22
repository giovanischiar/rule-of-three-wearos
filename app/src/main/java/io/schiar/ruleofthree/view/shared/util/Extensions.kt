package io.schiar.ruleofthree.view.shared.util

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
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

fun Modifier.fillMaxRectangle(): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "fillMaxRectangle"
    },
) {
    val isRound = LocalConfiguration.current.isScreenRound
    var inset: Dp = 0.dp
    if (isRound) {
        val screenHeightDp = LocalConfiguration.current.screenHeightDp
        val screenWidthDp = LocalConfiguration.current.smallestScreenWidthDp
        val maxSquareEdge = (sqrt(((screenHeightDp * screenWidthDp) / 2).toDouble()))
        inset = Dp(((screenHeightDp - maxSquareEdge) / 2).toFloat())
    }
    fillMaxSize().padding(all = inset)
}