package io.schiar.ruleofthree.view.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.tooling.preview.devices.WearDevices
import io.schiar.ruleofthree.R
import io.schiar.ruleofthree.view.viewdata.CrossMultiplierViewData

@Composable
fun CrossMultiplierHistoryView(crossMultiplier: CrossMultiplierViewData) {
    val (a, b, c, result) = crossMultiplier
    Box {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 45.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row {
                CrossMultiplierHistoryItemView(modifier = Modifier.weight(1f), value = a)
                CrossMultiplierHistoryItemView(modifier = Modifier.weight(1f), value = b)
            }

            Row {
                CrossMultiplierHistoryItemView(modifier = Modifier.weight(1f), value = c)
                CrossMultiplierHistoryItemView(
                    modifier = Modifier.weight(1f),
                    value = result,
                    isResult = true
                )
            }
        }

        Text(
            modifier = Modifier.align(alignment = Alignment.Center),
            text = "×",
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.xColor),
            fontSize = 11.2.sp
        )
    }
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun CrossMultiplierHistoryViewPreview() {
    val crossMultiplier = CrossMultiplierViewData(
        a = "2",
        b = "23",
        c = "1.23",
        result = "${(23 * 1.23)/2}"
    )
    CrossMultiplierHistoryView(crossMultiplier = crossMultiplier)
}