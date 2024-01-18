package io.schiar.ruleofthree.view.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.IconButton
import androidx.wear.compose.material3.Text
import androidx.wear.tooling.preview.devices.WearDevices
import io.schiar.ruleofthree.R
import io.schiar.ruleofthree.view.calculateTextUnitBasedOn

@Composable
fun ResultDialogView(value: String, onClosePressed: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.backgroundColor)),
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

        IconButton(
            modifier = Modifier.align(alignment = Alignment.TopStart)
                .padding(start = 20.dp, top = 20.dp).size(40.dp),
            onClick = { onClosePressed() }
        ) {
            Icon(
                modifier = Modifier.padding(horizontal = 5.dp),
                painter = painterResource(id = android.R.drawable.ic_menu_close_clear_cancel),
                contentDescription = "close",
                tint = colorResource(R.color.hashColor)
            )
        }
    }
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun ResultDialogViewPreview() {
    ResultDialogView(value = "1")
}