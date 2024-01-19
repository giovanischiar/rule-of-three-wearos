package io.schiar.ruleofthree.view.viewdata

data class CrossMultiplierViewData(
    val a: String = "",
    val b: String = "",
    val c: String = "",
    val result: ResultViewData = ResultViewData(result = "", _result = 0.0)
) {
    fun isNotEmpty(): Boolean {
        return a.isNotEmpty() || b.isNotEmpty() || c.isNotEmpty()
    }
}