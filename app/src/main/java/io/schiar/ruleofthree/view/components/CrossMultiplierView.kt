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
    onValueAddedToInput: (value: String, position: Pair<Int, Int>) -> Unit = {_,_ ->},
    onInputBackspaced: (position: Pair<Int, Int>) -> Unit = {},
    onInputCleared: (position: Pair<Int, Int>) -> Unit = {},
    onSubmitClicked: () -> Unit = {},
    onInputLongClicked: (position: Pair<Int, Int>) -> Unit = {}
) {
    val (values, unknownPosition, result) = crossMultiplier
    val (i, j) = unknownPosition
    val layoutValues = Array<Array<@Composable () -> Unit>>(size = 2) { arrayOf({}, {}) }
    layoutValues[i][j] = { ResultView(displayValue = result) }
    layoutValues[!i][j] = {
        InputView(
            displayValue = values[!i][j],
            editable = editable,
            onDigitPressed = { value -> onValueAddedToInput(value, Pair(!i, j)) },
            onErasePressed = { onInputBackspaced(Pair(!i, j)) },
            onClearPressed = { onInputCleared(Pair(!i, j)) },
            onEnterPressed = onSubmitClicked,
            onLongPress = { onInputLongClicked(Pair(!i, j)) }
        )
    }
    layoutValues[i][!j] = {
        InputView(
            displayValue = values[i][!j],
            editable = editable,
            onDigitPressed = { value -> onValueAddedToInput(value, Pair(i, !j)) },
            onErasePressed = { onInputBackspaced(Pair(i, !j)) },
            onClearPressed = { onInputCleared(Pair(i, !j)) },
            onEnterPressed = onSubmitClicked,
            onLongPress = { onInputLongClicked(Pair(i, !j)) }
        )
    }
    layoutValues[!i][!j] = {
        InputView(
            displayValue = values[!i][!j],
            editable = editable,
            onDigitPressed = { value -> onValueAddedToInput(value, Pair(!i, !j)) },
            onErasePressed = { onInputBackspaced(Pair(!i, !j)) },
            onClearPressed = { onInputCleared(Pair(!i, !j)) },
            onEnterPressed = onSubmitClicked,
            onLongPress = { onInputLongClicked(Pair(!i, !j)) }
        )
    }

    CrossMultiplierLayout(modifier = modifier, values = layoutValues)
}