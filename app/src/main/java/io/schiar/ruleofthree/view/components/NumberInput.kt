package io.schiar.ruleofthree.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.dialog.Dialog
import io.schiar.ruleofthree.R
import io.schiar.ruleofthree.view.calculateTextUnitBasedOn
import kotlin.math.sqrt

@Composable
fun NumberInput(
    modifier: Modifier = Modifier,
    displayValue: String,
    onDigitPressed: (value: String) -> Unit,
    onErasePressed: () -> Unit,
    onClearPressed: () -> Unit
) {
    var numericKeyboardShow by remember { mutableStateOf(false) }

    fun onEnterPressed() {
        if (displayValue == ".") { onClearPressed() }
        numericKeyboardShow = false
    }

    Dialog(
        showDialog = numericKeyboardShow,
        onDismissRequest = { numericKeyboardShow = false }
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

    Button(
        modifier = modifier.padding(all = 10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(R.color.backgroundColor)
        ),
        border = ButtonDefaults.outlinedButtonBorder(
            borderColor = colorResource(R.color.squareStrokeColor),
            borderWidth = 2.dp
        ),
        shape = RectangleShape,
        onClick = { numericKeyboardShow = true }
    ) {
        Text(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            text = displayValue,
            color = colorResource(id = R.color.hashColor),
            fontSize = calculateTextUnitBasedOn(length = displayValue.length)
        )
    }
}