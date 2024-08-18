package io.schiar.ruleofthree.view.shared.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.dialog.Dialog
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.IconButton
import androidx.wear.tooling.preview.devices.WearDevices
import io.schiar.ruleofthree.R
import java.text.DecimalFormatSymbols

@Composable
fun InputView(
    displayValue: String = "",
    editable: Boolean = true,
    onCharacterPressed: (value: String) -> Unit = {},
    onBackspacePressed: () -> Unit = {},
    onClearPressed: () -> Unit = {},
    onLongPressed: () -> Unit = {},
    onEnterPressed: () -> Unit = {}
) {
    var numericKeyboardShow by remember { mutableStateOf(value = false) }

    fun handleEnterPressed() {
        val decimalSeparator = DecimalFormatSymbols.getInstance().decimalSeparator
        if (displayValue == "$decimalSeparator") { onClearPressed() }
        if (displayValue.isNotEmpty() && displayValue.last() == decimalSeparator) {
            onBackspacePressed()
        }
        numericKeyboardShow = false
        onEnterPressed()
    }

    Dialog(
        showDialog = numericKeyboardShow,
        onDismissRequest = { numericKeyboardShow = false }
    ) {
        Box {
            CharacterPad(
                displayValue = displayValue,
                onCharacterPressed = onCharacterPressed,
                onBackspacePressed = onBackspacePressed,
                onClearPressed = onClearPressed,
                onEnterPressed = ::handleEnterPressed
            )
            IconButton(
                modifier = Modifier.align(alignment = Alignment.TopStart)
                    .padding(start = 20.dp, top = 20.dp).size(30.dp),
                onClick = { numericKeyboardShow = false }
            ) {
                Icon(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    painter = painterResource(id = android.R.drawable.ic_menu_close_clear_cancel),
                    contentDescription = "close",
                    tint = colorResource(R.color.hashColor)
                )
            }
        }
    }

    CrossMultiplierItemView(
        displayValue = displayValue,
        onClick = { numericKeyboardShow = true },
        onLongPress = onLongPressed,
        enabled = editable
    )
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun InputPreview() {
    InputView(displayValue = "0")
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun InputFullPreview() {
    InputView(displayValue = "238947923")
}