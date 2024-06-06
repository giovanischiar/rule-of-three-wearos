package io.schiar.ruleofthree.view.shared.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.schiar.ruleofthree.not
import io.schiar.ruleofthree.viewmodel.viewdata.CrossMultiplierViewData

@Composable
fun CrossMultiplierView(
    modifier: Modifier = Modifier,
    crossMultiplier: CrossMultiplierViewData,
    editable: Boolean = true,
    onCharacterPressedAt: (position: Pair<Int, Int>, character: String) -> Unit = {_,_ ->},
    onBackspacePressedAt: (position: Pair<Int, Int>) -> Unit = {},
    onClearPressedAt: (position: Pair<Int, Int>) -> Unit = {},
    onLongPressedAt: (position: Pair<Int, Int>) -> Unit = {},
    onSubmitPressed: () -> Unit = {}
) {
    val unknownPosition = crossMultiplier.unknownPosition
    val values = crossMultiplier.values
    val (i, j) = unknownPosition
    val layoutValues = Array<Array<@Composable () -> Unit>>(size = 2) { arrayOf({}, {}) }
    layoutValues[i][j] = { ResultView(displayValue = crossMultiplier.result) }
    layoutValues[!i][j] = {
        InputView(
            displayValue = values[!i][j],
            editable = editable,
            onCharacterPressed = { character -> onCharacterPressedAt(Pair(!i, j), character) },
            onBackspacePressed = { onBackspacePressedAt(Pair(!i, j)) },
            onClearPressed = { onClearPressedAt(Pair(!i, j)) },
            onLongPressed = { onLongPressedAt(Pair(!i, j)) },
            onEnterPressed = onSubmitPressed
        )
    }
    layoutValues[i][!j] = {
        InputView(
            displayValue = values[i][!j],
            editable = editable,
            onCharacterPressed = { value -> onCharacterPressedAt(Pair(i, !j), value) },
            onBackspacePressed = { onBackspacePressedAt(Pair(i, !j)) },
            onClearPressed = { onClearPressedAt(Pair(i, !j)) },
            onLongPressed = { onLongPressedAt(Pair(i, !j)) },
            onEnterPressed = onSubmitPressed
        )
    }
    layoutValues[!i][!j] = {
        InputView(
            displayValue = values[!i][!j],
            editable = editable,
            onCharacterPressed = { value -> onCharacterPressedAt(Pair(!i, !j), value) },
            onBackspacePressed = { onBackspacePressedAt(Pair(!i, !j)) },
            onClearPressed = { onClearPressedAt(Pair(!i, !j)) },
            onLongPressed = { onLongPressedAt(Pair(!i, !j)) },
            onEnterPressed = onSubmitPressed
        )
    }

    CrossMultiplierLayout(modifier = modifier, values = layoutValues)
}