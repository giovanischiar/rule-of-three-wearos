package io.schiar.ruleofthree.view.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.schiar.ruleofthree.view.viewdata.CrossMultiplierViewData

@Composable
fun CrossMultiplierView(
    modifier: Modifier = Modifier,
    crossMultiplier: CrossMultiplierViewData,
    editable: Boolean = true,
    addInput: (value: String, position: Int) -> Unit = {_,_ ->},
    removeInput: (position: Int) -> Unit = {},
    clearInput: (position: Int) -> Unit = {},
    submit: () -> Unit = {}
) {
    val (a, b, c, result) = crossMultiplier
    CrossMultiplierLayout(
        modifier = modifier,
        upperLeftQuadrant = {
            InputView(
                displayValue = a,
                editable = editable,
                onDigitPressed = { value -> addInput(value, 0) },
                onErasePressed = { removeInput(0) },
                onClearPressed = { clearInput(0) },
                onEnterPressed = submit,
            )
        },
        upperRightQuadrant = {
            InputView(
                displayValue = b,
                editable = editable,
                onDigitPressed = { value -> addInput(value, 1) },
                onErasePressed = { removeInput(1) },
                onClearPressed = { clearInput(1) },
                onEnterPressed = submit,
            )
        },
        lowerLeftQuadrant = {
            InputView(
                displayValue = c,
                editable = editable,
                onDigitPressed = { value -> addInput(value, 2) },
                onErasePressed = { removeInput(2) },
                onClearPressed = { clearInput(2) },
                onEnterPressed = submit,
            )
        },
        lowerRightQuadrant = { ResultView(displayValue = result) }
    )
}