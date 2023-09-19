package io.schiar.threerule.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.CompactButton
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.dialog.Dialog

@Composable
fun NumberInput(modifier: Modifier = Modifier, onValueChanged: (value: Double) -> Unit) {
    var numericKeyboardShow by remember { mutableStateOf(false) }
    var userInput by remember { mutableStateOf("") }

    fun setNumber(input: String) {
        if ((userInput + input).toDoubleOrNull() != null) {
            userInput += input
            onValueChanged(userInput.toDouble())
        }
    }

    fun erase() {
        if (userInput.isNotEmpty()) {
            userInput = userInput.substring(0 until userInput.length-1)
        }
    }

    Dialog(
        showDialog = numericKeyboardShow,
        onDismissRequest = { numericKeyboardShow = false }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f, true)
                        .padding(bottom = 5.dp, start = 50.dp, end = 50.dp)
                        .border(BorderStroke(2.dp, MaterialTheme.colors.primary)),
                    textAlign = TextAlign.Center,
                    text = userInput
                )
            }
            Row {
                CompactButton(backgroundPadding = 0.dp, onClick = { setNumber("7") }) {
                    Text("7")
                }

                CompactButton(backgroundPadding = 0.dp, onClick = { setNumber("8") }) {
                    Text("8")
                }

                CompactButton(backgroundPadding = 0.dp, onClick = { setNumber("9") }) {
                    Text("9")
                }
            }
            Row {
                CompactButton(
                    modifier = Modifier.padding(end = 5.dp),
                    backgroundPadding = 0.dp,
                    onClick = { userInput = "" }
                ) {
                    Icon(
                        painter = painterResource(android.R.drawable.ic_menu_close_clear_cancel),
                        contentDescription = "clear"
                    )
                }

                CompactButton(backgroundPadding = 0.dp, onClick = { setNumber("4") }) {
                    Text("4")
                }

                CompactButton(backgroundPadding = 0.dp, onClick = { setNumber("5") }) {
                    Text("5")
                }

                CompactButton(backgroundPadding = 0.dp, onClick = { setNumber("6") }) {
                    Text("6")
                }

                CompactButton(
                    modifier = Modifier.padding(start = 5.dp),
                    backgroundPadding = 0.dp, onClick = { numericKeyboardShow = false }) {
                    Icon(
                        painter = painterResource(android.R.drawable.ic_menu_send),
                        contentDescription = "enter"
                    )
                }
            }

            Row {
                CompactButton(backgroundPadding = 0.dp, onClick = { setNumber("1") }) {
                    Text("1")
                }

                CompactButton(backgroundPadding = 0.dp, onClick = { setNumber("2") }) {
                    Text("2")
                }

                CompactButton(backgroundPadding = 0.dp, onClick = { setNumber("3") }) {
                    Text("3")
                }
            }

            Row {
                CompactButton(backgroundPadding = 0.dp, onClick = { setNumber("0") }) {
                    Text("0")
                }
                CompactButton(backgroundPadding = 0.dp, onClick = { setNumber(".") }) {
                    Text(".")
                }
                CompactButton(backgroundPadding = 0.dp, onClick = { erase() }) {
                    Icon(
                        painter = painterResource(android.R.drawable.ic_input_delete),
                        contentDescription = "erase"
                    )
                }
            }
        }
    }

    Button(
        modifier = modifier.padding(all = 10.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
        border = ButtonDefaults.outlinedButtonBorder(
        borderColor = MaterialTheme.colors.primary,
        borderWidth = 2.dp
    ), shape = RectangleShape, onClick = { numericKeyboardShow = true }) {
        Text(userInput)
    }
}