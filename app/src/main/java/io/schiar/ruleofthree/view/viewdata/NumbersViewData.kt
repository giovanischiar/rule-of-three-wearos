package io.schiar.ruleofthree.view.viewdata

data class NumbersViewData(
    val a: String = "",
    val b: String = "",
    val c: String = "",
    val result: String = ""
) {
    fun isNotEmpty(): Boolean {
        return a.isNotEmpty() || b.isNotEmpty() || c.isNotEmpty()
    }
}