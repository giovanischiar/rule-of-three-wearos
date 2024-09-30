package io.schiar.ruleofthree.view.shared.component

import android.content.pm.PackageManager
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.tooling.preview.devices.WearDevices
import io.schiar.ruleofthree.R
import java.text.DecimalFormatSymbols

@OptIn(ExperimentalMaterial3Api::class)
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
    val isWatch = LocalContext.current.packageManager.hasSystemFeature(PackageManager.FEATURE_WATCH)
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

    if (isWatch) {
        ClosingDialog(
            showDialog = numericKeyboardShow,
            onDismissRequest = { numericKeyboardShow = false }
        ) {
            CharacterPad(
                displayValue = displayValue,
                onCharacterPressed = onCharacterPressed,
                onBackspacePressed = onBackspacePressed,
                onClearPressed = onClearPressed,
                onEnterPressed = ::handleEnterPressed
            )
        }
    } else {
        if (numericKeyboardShow) {
            ModalBottomSheet(
                containerColor =  colorResource(id = R.color.backgroundColor),
                onDismissRequest = { handleEnterPressed() }
            ) {
                Box(modifier = Modifier
                    .height(275.dp)
                    .padding(bottom = 35.dp)) {
                    CharacterPad(
                        displayValue = displayValue,
                        onCharacterPressed = onCharacterPressed,
                        onBackspacePressed = onBackspacePressed,
                        onClearPressed = onClearPressed,
                        onEnterPressed = ::handleEnterPressed,
                        displayHidden = true
                    )
                }
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