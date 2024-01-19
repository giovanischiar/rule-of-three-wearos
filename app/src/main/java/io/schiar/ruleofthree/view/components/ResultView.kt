package io.schiar.ruleofthree.view.components

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.dialog.Dialog
import androidx.wear.tooling.preview.devices.WearDevices

@Composable
fun ResultView(displayValue: String = "") {
    var dialogShow by remember { mutableStateOf(false) }

    Dialog(showDialog = dialogShow, onDismissRequest = { dialogShow = false }) {
        ResultDialogView(value = displayValue, onClosePressed = { dialogShow = false })
    }

    CrossMultiplierItemView(
        displayValue = displayValue,
        onClick = { dialogShow = true },
        isResult = true
    )
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun ResultViewPreview() {
    ResultView(displayValue = "0")
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun ResultViewFullPreview() {
    ResultView(displayValue = "238947923")
}