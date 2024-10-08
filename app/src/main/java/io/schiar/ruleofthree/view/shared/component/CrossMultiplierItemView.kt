package io.schiar.ruleofthree.view.shared.component

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CrossMultiplierItemView(
    modifier: Modifier = Modifier,
    displayValue: String,
    isResult: Boolean = false,
    onClick: () -> Unit = {},
    onLongPress: () -> Unit = {},
    enabled: Boolean = true,
    baseTextSize: Int = 30
) {
    val valueColor = if (!isResult) {
        colorResource(id = R.color.hashColor)
    } else {
        colorResource(id = R.color.questionMarkColor)
    }

    Box(
        modifier = modifier
            .run {
                if (!isResult) {
                    border(BorderStroke(2.dp, colorResource(id = R.color.squareStrokeColor)))
                } else {
                    this
                }
            }
            .padding(all = 5.dp)
            .aspectRatio(ratio = 1f)
            .combinedClickable (
                enabled = enabled && displayValue != "?",
                onClick = { onClick() },
                onLongClick = { onLongPress() }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            text = displayValue,
            color = valueColor,
            fontSize = calculateTextUnitBasedOn(displayValue.length, baseSize = baseTextSize),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun CrossMultiplierItemViewPreview() {
    CrossMultiplierItemView(displayValue = "0")
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun CrossMultiplierItemViewFullPreview() {
    CrossMultiplierItemView(displayValue = "238947923")
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun CrossMultiplierItemViewResultPreview() {
    CrossMultiplierItemView(displayValue = "23.3", isResult = true)
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun CrossMultiplierItemViewResultFullPreview() {
    CrossMultiplierItemView(displayValue = "23383474", isResult = true)
}
