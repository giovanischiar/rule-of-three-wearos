package io.schiar.ruleofthree.view.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.schiar.ruleofthree.R

@Composable
fun NumberPad(
    displayValue: String = "",
    onDigitPressed: (value: String) -> Unit = {},
    onErasePressed: () -> Unit = {},
    onClearPressed: () -> Unit = {},
    onEnterPressed: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.backgroundColor)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Display(modifier = Modifier.weight(1f, fill = true), text = displayValue)
        }

        Row {
            NumberPadButton(name = "7") { onDigitPressed(it) }
            NumberPadButton(name = "8") { onDigitPressed(it) }
            NumberPadButton(name = "9") { onDigitPressed(it) }
        }

        Row {
            NumberPadButton(name = "clear") { onClearPressed() }
            Spacer(modifier = Modifier.width(5.dp))
            NumberPadButton(name = "4") { onDigitPressed(it) }
            NumberPadButton(name = "5") { onDigitPressed(it) }
            NumberPadButton(name = "6") { onDigitPressed(it) }
            Spacer(modifier = Modifier.width(5.dp))
            NumberPadButton(name = "enter") { onEnterPressed() }
        }

        Row {
            NumberPadButton(name = "1") { onDigitPressed(it) }
            NumberPadButton(name = "2") { onDigitPressed(it) }
            NumberPadButton(name = "3") { onDigitPressed(it) }
        }

        Row {
            NumberPadButton(name = "0") { onDigitPressed(it) }
            NumberPadButton(name = ".") { onDigitPressed(it) }
            NumberPadButton(name = "erase") { onErasePressed() }
        }
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun NumberPadPreview() {
    var displayValue by remember { mutableStateOf("") }
    NumberPad(
        displayValue = displayValue,
        onDigitPressed = { displayValue += it },
        onClearPressed = { displayValue = "" },
        onErasePressed = { displayValue = displayValue.dropLast(n = 1) }
    )
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun NumberPadFullPreview() {
    var displayValue by remember { mutableStateOf("29347623986593847239074") }
    NumberPad(
        displayValue = displayValue,
        onDigitPressed = { displayValue += it },
        onClearPressed = { displayValue = "" },
        onErasePressed = { displayValue = displayValue.dropLast(n = 1) }
    )
}