package io.schiar.ruleofthree

operator fun Int.not(): Int = if (this != 0) 0 else 1

operator fun <T> Array<Array<T>>.get(position: Pair<Int, Int>): T {
    val (i, j) = position
    return this[i][j]
}

operator fun <T> Array<Array<T>>.set(position: Pair<Int, Int>, value: T) {
    val (i, j) = position
    this[i][j] = value
}

fun Number?.stringify(): String {
    return (this ?: "").toString()
}