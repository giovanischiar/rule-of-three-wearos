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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.tooling.preview.devices.WearDevices
import io.schiar.ruleofthree.R

@Composable
fun CharacterPad(
    displayValue: String = "",
    onCharacterPressed: (value: String) -> Unit = {},
    onBackspacePressed: () -> Unit = {},
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
            InputDisplay(modifier = Modifier.weight(1f, fill = true), text = displayValue)
        }

        Row {
            CharacterButton(name = "7") { onCharacterPressed(it) }
            CharacterButton(name = "8") { onCharacterPressed(it) }
            CharacterButton(name = "9") { onCharacterPressed(it) }
        }

        Row {
            CharacterButton(name = "clear") { onClearPressed() }
            Spacer(modifier = Modifier.width(5.dp))
            CharacterButton(name = "4") { onCharacterPressed(it) }
            CharacterButton(name = "5") { onCharacterPressed(it) }
            CharacterButton(name = "6") { onCharacterPressed(it) }
            Spacer(modifier = Modifier.width(5.dp))
            CharacterButton(name = "enter") { onEnterPressed() }
        }

        Row {
            CharacterButton(name = "1") { onCharacterPressed(it) }
            CharacterButton(name = "2") { onCharacterPressed(it) }
            CharacterButton(name = "3") { onCharacterPressed(it) }
        }

        Row {
            CharacterButton(name = "0") { onCharacterPressed(it) }
            CharacterButton(name = ".") { onCharacterPressed(it) }
            CharacterButton(name = "backspace") { onBackspacePressed() }
        }
    }
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun NumberPadPreview() {
    var displayValue by remember { mutableStateOf("") }
    CharacterPad(
        displayValue = displayValue,
        onCharacterPressed = { displayValue += it },
        onClearPressed = { displayValue = "" },
        onBackspacePressed = { displayValue = displayValue.dropLast(n = 1) }
    )
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun NumberPadFullPreview() {
    var displayValue by remember { mutableStateOf("29347623986593847239074") }
    CharacterPad(
        displayValue = displayValue,
        onCharacterPressed = { displayValue += it },
        onClearPressed = { displayValue = "" },
        onBackspacePressed = { displayValue = displayValue.dropLast(n = 1) }
    )
}