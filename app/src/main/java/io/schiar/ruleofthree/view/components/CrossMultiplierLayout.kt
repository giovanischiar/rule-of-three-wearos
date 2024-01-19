package io.schiar.ruleofthree.view.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Icon
import androidx.wear.tooling.preview.devices.WearDevices
import io.schiar.ruleofthree.R

@Composable
fun CrossMultiplierLayout(
    modifier: Modifier = Modifier,
    upperLeftQuadrant: @Composable () -> Unit,
    upperRightQuadrant: @Composable () -> Unit,
    lowerLeftQuadrant: @Composable () -> Unit,
    lowerRightQuadrant: @Composable () -> Unit
) {
    Column(modifier = modifier.aspectRatio(1f)) {
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.BottomCenter) {
            Row {
                Box(modifier = Modifier.weight(1f).padding(end = 10.dp)) { upperLeftQuadrant() }
                Box(modifier = Modifier.weight(1f).padding(start = 10.dp)) { upperRightQuadrant() }
            }
        }


        Box(
            modifier = Modifier.fillMaxWidth().height(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.x),
                contentDescription = "x",
                tint = colorResource(R.color.xColor)
            )
        }

        Box(modifier = Modifier.weight(1f)) {
            Row {
                Box(modifier = Modifier.weight(1f).padding(end = 10.dp)) { lowerLeftQuadrant() }
                Box(modifier = Modifier.weight(1f).padding(start = 10.dp)) { lowerRightQuadrant() }
            }
        }
    }
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun CrossMultiplierLayoutPreview() {
    CrossMultiplierLayout(
        upperLeftQuadrant = { CrossMultiplierItemView(displayValue = "2") },
        upperRightQuadrant = { CrossMultiplierItemView(displayValue = "23") },
        lowerLeftQuadrant = { CrossMultiplierItemView(displayValue = "1.23") },
        lowerRightQuadrant = { CrossMultiplierItemView(displayValue = "14.145", isResult = true) }
    )
}