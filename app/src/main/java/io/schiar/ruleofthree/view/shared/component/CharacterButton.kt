package io.schiar.ruleofthree.view.shared.component

import android.content.res.Configuration
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.CompactButton
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import androidx.wear.tooling.preview.devices.WearDevices
import io.schiar.ruleofthree.R

@Composable
fun CharacterButton(
    modifier: Modifier = Modifier,
    name: String = "",
    onClick: (value: String) -> Unit = {}
) {
    CompactButton(
        modifier = modifier,
        backgroundPadding = 0.dp,
        onClick = { onClick(name) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        border = ButtonDefaults.outlinedButtonBorder(
            borderColor = colorResource(R.color.squareStrokeColor),
            borderWidth = 2.dp
        ),

        ) {
        when(name) {
            "clear" -> {
                Icon(
                    modifier = Modifier.padding(5.dp),
                    painter = painterResource(R.drawable.baseline_delete_forever_24),
                    contentDescription = name,
                    tint = colorResource(id = R.color.hashColor)
                )
            }

            "enter" -> {
                Icon(
                    modifier = Modifier.padding(5.dp),
                    painter = painterResource(android.R.drawable.ic_menu_send),
                    contentDescription = name,
                    tint = colorResource(id = R.color.hashColor)

                )
            }

            "backspace" -> {
                Icon(
                    modifier = Modifier.padding(5.dp),
                    painter = painterResource(android.R.drawable.ic_input_delete),
                    contentDescription = name,
                    tint = colorResource(id = R.color.hashColor)
                )
            }

            else -> {
                Text(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    text = name,
                    color = colorResource(id = R.color.hashColor)
                )
            }
        }
    }
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun CharacterButtonDigitPreview() {
    CharacterButton(name = "0")
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun CharacterButtonDecimalPointPreview() {
    CharacterButton(name = ".")
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun CharacterButtonClearPreview() {
    CharacterButton(name = "clear")
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun CharacterButtonBackspacePreview() {
    CharacterButton(name = "backspace")
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun CharacterButtonEnterPreview() {
    CharacterButton(name = "enter")
}