package io.schiar.ruleofthree.model

import io.schiar.ruleofthree.not

fun Array<Array<Input>>.isValid(unknownPosition: Pair<Int, Int>): Boolean {
    val (i, j) = unknownPosition
    val denominator = this[!i][!j].toNumberOrNull() ?: return false
    this[i][!j].toNumberOrNull() ?: return false
    this[!i][j].toNumberOrNull() ?: return false
    return denominator != 0.0 && denominator != 0
}