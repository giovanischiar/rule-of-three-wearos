package io.schiar.ruleofthree.view.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.schiar.ruleofthree.not
import io.schiar.ruleofthree.view.viewdata.CrossMultiplierViewData

@Composable
fun CrossMultiplierView(
    modifier: Modifier = Modifier,
    crossMultiplier: CrossMultiplierViewData,
    editable: Boolean = true,
    addInput: (value: String, position: Pair<Int, Int>) -> Unit = {_,_ ->},
    removeInput: (position: Pair<Int, Int>) -> Unit = {},
    clearInput: (position: Pair<Int, Int>) -> Unit = {},
    submit: () -> Unit = {},
    changeUnknownPosition: (position: Pair<Int, Int>) -> Unit = {}
) {
    val (values, unknownPosition, result) = crossMultiplier
    val (i, j) = unknownPosition
    val layoutValues = Array<Array<@Composable () -> Unit>>(size = 2) { arrayOf({}, {}) }
    layoutValues[i][j] = { ResultView(displayValue = result) }
    layoutValues[!i][j] = {
        InputView(
            displayValue = values[!i][j],
            editable = editable,
            onDigitPressed = { value -> addInput(value, Pair(!i, j)) },
            onErasePressed = { removeInput(Pair(!i, j)) },
            onClearPressed = { clearInput(Pair(!i, j)) },
            onEnterPressed = submit,
            onLongPress = { changeUnknownPosition(Pair(!i, j)) }
        )
    }
    layoutValues[i][!j] = {
        InputView(
            displayValue = values[i][!j],
            editable = editable,
            onDigitPressed = { value -> addInput(value, Pair(i, !j)) },
            onErasePressed = { removeInput(Pair(i, !j)) },
            onClearPressed = { clearInput(Pair(i, !j)) },
            onEnterPressed = submit,
            onLongPress = { changeUnknownPosition(Pair(i, !j)) }
        )
    }
    layoutValues[!i][!j] = {
        InputView(
            displayValue = values[!i][!j],
            editable = editable,
            onDigitPressed = { value -> addInput(value, Pair(!i, !j)) },
            onErasePressed = { removeInput(Pair(!i, !j)) },
            onClearPressed = { clearInput(Pair(!i, !j)) },
            onEnterPressed = submit,
            onLongPress = { changeUnknownPosition(Pair(!i, !j)) }
        )
    }

    CrossMultiplierLayout(modifier = modifier, values = layoutValues)
}