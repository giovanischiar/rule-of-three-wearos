package io.schiar.ruleofthree.view.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import io.schiar.ruleofthree.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

@Composable
fun Display(modifier: Modifier = Modifier, text: String = "") {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    SideEffect { coroutineScope.launch { scrollState.scrollTo(value = scrollState.maxValue) } }

    Text(
        modifier = modifier
            .padding(bottom = 5.dp, start = 50.dp, end = 50.dp)
            .border(BorderStroke(2.dp, colorResource(id = R.color.squareStrokeColor)))
            .padding(start = 5.dp, end = 5.dp)
            .horizontalScroll(scrollState),
        textAlign = TextAlign.Center,
        text = text,
        color = colorResource(id = R.color.hashColor)
    )
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun DisplayPreview() {
    Display(text = "0")
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun DisplayFullPreview() {
    Display(text = "3947328047320")
}