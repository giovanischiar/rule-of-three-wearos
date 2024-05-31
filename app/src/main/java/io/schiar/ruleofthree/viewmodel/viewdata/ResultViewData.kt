package io.schiar.ruleofthree.viewmodel.viewdata

import io.schiar.ruleofthree.view.shared.util.toFormattedString

data class ResultViewData(
    val result: String,
    private val _result: Double,
) {
    fun longerResult(decimals: Int = 2): String {
        return _result.toFormattedString(decimals = decimals)
    }
}