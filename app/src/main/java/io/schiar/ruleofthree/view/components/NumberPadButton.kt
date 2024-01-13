package io.schiar.ruleofthree.view.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.CompactButton
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import io.schiar.ruleofthree.R

@Composable
fun NumberPadButton(modifier: Modifier = Modifier, name: String, onClick: (value: String) -> Unit) {
    CompactButton(
        modifier = modifier,
        backgroundPadding = 0.dp,
        onClick = { onClick(name) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(R.color.backgroundColor)
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
                    painter = painterResource(android.R.drawable.ic_menu_close_clear_cancel),
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

            "erase" -> {
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