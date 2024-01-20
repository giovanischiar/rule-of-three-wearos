package io.schiar.ruleofthree.model

import io.schiar.ruleofthree.not

fun Array<Array<Input>>.isValid(unknownPosition: Pair<Int, Int>): Boolean {
    val (i, j) = unknownPosition
    val denominator = this[!i][!j].toDoubleOrNull() ?: return false
    this[i][!j].toDoubleOrNull() ?: return false
    this[!i][j].toDoubleOrNull() ?: return false
    return denominator != 0.0
}