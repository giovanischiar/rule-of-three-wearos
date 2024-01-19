package io.schiar.ruleofthree.view.screen

import android.content.res.Configuration
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.rememberActiveFocusRequester
import androidx.wear.tooling.preview.devices.WearDevices
import io.schiar.ruleofthree.R
import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CrossMultiplierDataSource
import io.schiar.ruleofthree.model.repository.MainRepository
import io.schiar.ruleofthree.view.components.CrossMultiplierView
import io.schiar.ruleofthree.view.components.TouchableArea
import io.schiar.ruleofthree.view.components.TouchableIcon
import io.schiar.ruleofthree.viewmodel.HistoryViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun HistoryScreen(viewModel: HistoryViewModel, onBackPressed: () -> Unit = {}) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val focusRequester = rememberActiveFocusRequester()
    val allPastCrossMultipliers by viewModel.allPastCrossMultipliers.collectAsState()

    val iconSize = 30.dp

    if (allPastCrossMultipliers.isEmpty()) { onBackPressed() }

    fun replaceCurrentCrossMultiplier(index: Int) {
        coroutineScope.launch { viewModel.replaceCurrentCrossMultiplier(index = index) }
        onBackPressed()
    }

    fun deleteHistoryItem(index: Int) {
        coroutineScope.launch { viewModel.deleteHistoryItem(index = index) }
    }

    fun deleteHistory() {
        coroutineScope.launch { viewModel.deleteHistory() }
    }

    Row {
        TouchableIcon(
            modifier = Modifier.fillMaxHeight().width(iconSize),
            onClick = { onBackPressed() },
            iconDrawableID = R.drawable.baseline_arrow_back_24,
            contentDescription = "back",
            colorID = R.color.hashColor
        )

        LazyColumn(
            modifier = Modifier
                .onRotaryScrollEvent {
                    coroutineScope.launch { listState.scrollBy(it.verticalScrollPixels) }
                    true
                }
                .focusRequester(focusRequester)
                .focusable(),
            state = listState
        ) {
            item {
                TouchableIcon(
                    modifier = Modifier.fillMaxWidth().padding(end = iconSize).height(iconSize),
                    onClick = { deleteHistory() },
                    iconDrawableID = R.drawable.baseline_delete_sweep_24,
                    contentDescription = "clear all inputs",
                    colorID = R.color.hashColor
                )
            }

            items(count = allPastCrossMultipliers.size) { index ->
                var crossMultiplierHeight by remember { mutableIntStateOf(0) }
                Column {
                    Row {
                        TouchableArea(
                            modifier = Modifier
                                .weight(1f)
                                .onSizeChanged { crossMultiplierHeight = it.height }
                                .aspectRatio(1f),
                            onClick = { replaceCurrentCrossMultiplier(index = index) }
                        ) {
                            CrossMultiplierView(
                                crossMultiplier = allPastCrossMultipliers[index],
                                editable = false
                            )
                        }

                        TouchableIcon(
                            modifier = Modifier.width(iconSize).height(
                                with(LocalDensity.current) { crossMultiplierHeight.toDp() }
                            ),
                            onClick = { deleteHistoryItem(index = index) },
                            iconDrawableID = R.drawable.baseline_delete_forever_24,
                            contentDescription = "clear input",
                            colorID = R.color.hashColor
                        )
                    }

                    if (index < allPastCrossMultipliers.size - 1) {
                        Divider(
                            modifier = Modifier.padding(
                                start = 5.dp, end = iconSize + 5.dp, top = 15.dp, bottom = 15.dp
                            ),
                            color = colorResource(id = R.color.squareStrokeColor),
                            thickness = 1.dp
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.fillMaxWidth().height(30.dp)) }
        }
    }
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun HistoryScreenPreview() {
    val dataSource = CrossMultiplierDataSource(
        allPastCrossMultipliers = mutableListOf(
            CrossMultiplier(a = "8342234", b = "324423", c = "45456").resultCalculated(),
            CrossMultiplier(a = "4", b = "40", c = "400").resultCalculated(),
            CrossMultiplier(a = "42", b = "440", c = "5").resultCalculated(),
            CrossMultiplier(a = "3", b = "10", c = "78").resultCalculated(),
            CrossMultiplier(a = "5", b = "135", c = "7").resultCalculated()
        )
    )
    val repository = MainRepository(dataSource = dataSource)
    val historyViewModel = HistoryViewModel(repository = repository)
    LaunchedEffect(Unit) { repository.loadDatabase() }
    HistoryScreen(viewModel = historyViewModel)
}