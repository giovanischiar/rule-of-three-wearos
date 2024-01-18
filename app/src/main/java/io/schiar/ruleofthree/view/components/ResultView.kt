package io.schiar.ruleofthree.view.components

import android.content.res.Configuration
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.dialog.Dialog
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material3.Text
import androidx.wear.tooling.preview.devices.WearDevices
import io.schiar.ruleofthree.R
import io.schiar.ruleofthree.view.calculateTextUnitBasedOn

@Composable
fun ResultView(modifier: Modifier = Modifier, displayValue: String = "") {
    var dialogShow by remember { mutableStateOf(false) }

    Dialog(
        showDialog = dialogShow,
        onDismissRequest = { dialogShow = false }
    ) {
        ResultDialogView(value = displayValue, onClosePressed = { dialogShow = false })
    }

    Button(
        modifier = modifier.padding(all = 15.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = Color.Transparent,
            disabledBackgroundColor = Color.Transparent,
            disabledContentColor = Color.Transparent
        ),
        enabled = displayValue != "?",
        shape = RectangleShape,
        onClick = { dialogShow = true }
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                text = displayValue,
                color = colorResource(id = R.color.questionMarkColor),
                fontSize = calculateTextUnitBasedOn(length = displayValue.length),
                textAlign = TextAlign.Center
            )
        }
    }
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