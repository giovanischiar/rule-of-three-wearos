package io.schiar.ruleofthree.view.viewdata

import io.schiar.ruleofthree.viewmodel.util.toFormattedString

data class ResultViewData(
    val result: String,
    private val _result: Double,
) {
    fun longerResult(decimals: Int = 2): String {
        return _result.toFormattedString(decimals = decimals)
    }
}