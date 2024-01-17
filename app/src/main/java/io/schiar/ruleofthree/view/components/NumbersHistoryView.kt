package io.schiar.ruleofthree.view.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material3.Text
import androidx.wear.tooling.preview.devices.WearDevices
import io.schiar.ruleofthree.R
import io.schiar.ruleofthree.view.calculateTextUnitBasedOn

@Composable
fun NumbersHistoryView(modifier: Modifier = Modifier, value: String, isResult: Boolean = false) {
    val valueColor = if (!isResult) {
        colorResource(id = R.color.hashColor)
    } else {
        colorResource(id = R.color.questionMarkColor)
    }

    Box(
        modifier = modifier
            .width(ButtonDefaults.DefaultButtonSize)
            .height(ButtonDefaults.DefaultButtonSize)
            .padding(all = 5.dp)
            .run {
                if (!isResult) {
                    border(BorderStroke(2.dp, colorResource(id = R.color.squareStrokeColor)))
                } else {
                    this
                }
            }
            .padding(all = 5.dp)
        ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            text = value,
            color = valueColor,
            fontSize = calculateTextUnitBasedOn(value.length, baseSize = 19),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun NumbersHistoryViewPreview() {
    NumbersHistoryView(value = "0")
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun NumbersHistoryViewFullPreview() {
    NumbersHistoryView(value = "238947923")
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun NumbersHistoryViewResultPreview() {
    NumbersHistoryView(value = "23.3", isResult = true)
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun NumbersHistoryViewResultFullPreview() {
    NumbersHistoryView(value = "23383474", isResult = true)
}
