package io.schiar.threerule.viewmodel

import java.text.DecimalFormat

fun Double?.toFormattedString(): String {
    this ?: return "?"
    return DecimalFormat("#,###.##").format(this)
}