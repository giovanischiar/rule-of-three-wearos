package io.schiar.ruleofthree.view.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.dialog.Dialog
import androidx.wear.tooling.preview.devices.WearDevices
import io.schiar.ruleofthree.R
import io.schiar.ruleofthree.view.calculateTextUnitBasedOn

@Composable
fun NumberInput(
    modifier: Modifier = Modifier,
    displayValue: String = "",
    onDigitPressed: (value: String) -> Unit = {},
    onErasePressed: () -> Unit = {},
    onClearPressed: () -> Unit = {},
    onEnterPressed: () -> Unit = {},
    numberPadOpened: Boolean = false
) {
    var numericKeyboardShow by remember { mutableStateOf(numberPadOpened) }

    fun handleEnterPressed() {
        if (displayValue == ".") { onClearPressed() }
        numericKeyboardShow = false
        onEnterPressed()
    }

    Dialog(
        showDialog = numericKeyboardShow,
        onDismissRequest = { numericKeyboardShow = false }
    ) {
        NumberPad(
            displayValue = displayValue,
            onDigitPressed = onDigitPressed,
            onErasePressed = onErasePressed,
            onClearPressed = onClearPressed,
            onEnterPressed = ::handleEnterPressed
        )
    }

    Button(
        modifier =
        modifier
            .padding(all = 10.dp)
            .border(BorderStroke(2.dp, colorResource(id = R.color.squareStrokeColor)))
            .padding(all = 5.dp)
        ,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(R.color.backgroundColor)
        ),
        shape = RectangleShape,
        onClick = { numericKeyboardShow = true }
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                text = displayValue,
                color = colorResource(id = R.color.hashColor),
                fontSize = calculateTextUnitBasedOn(length = displayValue.length),
                textAlign = TextAlign.Center
            )
        }

    }
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun NumberInputPreview() {
    NumberInput(displayValue = "0")
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun NumberInputFullPreview() {
    NumberInput(displayValue = "238947923")
}