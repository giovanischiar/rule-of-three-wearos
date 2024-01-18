package io.schiar.ruleofthree.view.components

import android.content.res.Configuration
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import androidx.wear.tooling.preview.devices.WearDevices
import io.schiar.ruleofthree.R
import io.schiar.ruleofthree.view.calculateTextUnitBasedOn
import io.schiar.ruleofthree.view.viewdata.CrossMultiplierViewData

@Composable
fun CrossMultiplierView(
    crossMultiplier: CrossMultiplierViewData,
    addInput: (value: String, position: Int) -> Unit = { _,_ -> },
    removeInput: (position: Int) -> Unit = { _ -> },
    clearInput: (position: Int) -> Unit = { _ -> },
    submitInput: () -> Unit = {}
) {
    val (a, b, c, result) = crossMultiplier
    Box {
        Column(
            modifier = Modifier.padding(all = 25.dp).fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.weight(1f) ) {
                InputView(
                    modifier = Modifier.weight(1f),
                    displayValue = a,
                    onDigitPressed = { value -> addInput(value, 0) },
                    onErasePressed = { removeInput(0) },
                    onClearPressed = { clearInput(0) },
                    onEnterPressed = { submitInput() }
                )
                InputView(
                    modifier = Modifier.weight(1f),
                    displayValue = b,
                    onDigitPressed = { value -> addInput(value, 1) },
                    onErasePressed = { removeInput(1) },
                    onClearPressed = { clearInput(1) },
                    onEnterPressed = { submitInput() }
                )
            }

            Row(modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                InputView(
                    modifier = Modifier.weight(1f),
                    displayValue = c,
                    onDigitPressed = { value -> addInput(value, 2) },
                    onErasePressed = { removeInput(2) },
                    onClearPressed = { clearInput(2) },
                    onEnterPressed = { submitInput() }
                )
                ResultView(modifier = Modifier.weight(1f), displayValue = result)
            }
        }
        Text(
            modifier = Modifier.align(alignment = Alignment.Center),
            text = "×",
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.xColor),
            fontSize = 24.sp
        )
    }
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun CrossMultiplierViewPreview() {
    val crossMultiplier = CrossMultiplierViewData(
        a = "2",
        b = "23",
        c = "1.23",
        result = "${(23 * 1.23)/2}"
    )
    CrossMultiplierView(crossMultiplier = crossMultiplier)
}