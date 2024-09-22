package io.schiar.ruleofthree.view.shared.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Text
import androidx.wear.tooling.preview.devices.WearDevices
import io.schiar.ruleofthree.R
import io.schiar.ruleofthree.view.shared.util.calculateTextUnitBasedOn
import io.schiar.ruleofthree.viewmodel.viewdata.ResultViewData

@Composable
fun ResultDialogView(result: ResultViewData) {
    var decimals by remember { mutableIntStateOf(value = 2) }
    val value = result.longerResult(decimals = decimals)
    val nextValue = result.longerResult(decimals = decimals + 1)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.backgroundColor)),
        contentAlignment = Alignment.Center
    ) {
        Column {
            TouchableIcon(
                modifier = Modifier.fillMaxWidth().height(30.dp),
                onClick = { decimals-- },
                iconDrawableID = R.drawable.baseline_decimal_decrease_24,
                contentDescription = "decrease decimal",
                colorID = R.color.hashColor,
                visible = decimals > 2
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .align(alignment = Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    text = value,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.questionMarkColor),
                    fontSize = calculateTextUnitBasedOn(
                        length = value.length,
                        baseSize = 120
                    )
                )
            }
            TouchableIcon(
                modifier = Modifier.fillMaxWidth().height(30.dp),
                onClick = { decimals++ },
                iconDrawableID = R.drawable.baseline_decimal_increase_24,
                contentDescription = "increase decimal",
                colorID = R.color.hashColor,
                visible = value != nextValue
            )
        }
    }
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun ResultDialogViewPreview() {
    ResultDialogView(result = ResultViewData(result = "1.34", _result = 1.34567893))
}