package io.schiar.ruleofthree.view.history

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
import io.schiar.ruleofthree.view.shared.components.CrossMultiplierView
import io.schiar.ruleofthree.view.shared.components.TouchableIcon
import io.schiar.ruleofthree.viewmodel.viewdata.CrossMultiplierViewData
import kotlinx.coroutines.launch

@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun HistoryScreen(
    pastCrossMultipliersUiState: PastCrossMultipliersUiState,
    deleteHistory: () -> Unit = {},
    pushCharacterToInputOnPositionOfTheCrossMultiplierAt: (
        index: Int, position: Pair<Int, Int>, character: String
    ) -> Unit = {_,_,_->},
    popCharacterOfInputOnPositionOfTheCrossMultiplierAt: (
        index: Int, position: Pair<Int, Int>
    ) -> Unit = {_,_->},
    clearInputOnPositionOfTheCrossMultiplierAt: (
        index: Int, position: Pair<Int, Int>
    ) -> Unit = {_,_->},
    changeTheUnknownPositionToPositionOfTheCrossMultiplierAt : (
        index: Int, position: Pair<Int, Int>
    ) -> Unit = {_,_->},
    deleteCrossMultiplierAt: (index: Int) -> Unit = {},
    onBackPressed: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val focusRequester = rememberActiveFocusRequester()
    val iconSize = 30.dp
    val pastCrossMultipliers = when (pastCrossMultipliersUiState) {
        is PastCrossMultipliersUiState.Loading -> emptyList()
        is PastCrossMultipliersUiState.CrossMultipliersLoaded -> {
            pastCrossMultipliersUiState.crossMultipliers
        }
    }

    if (pastCrossMultipliersUiState !is PastCrossMultipliersUiState.Loading &&
        pastCrossMultipliers.isEmpty()) {
        onBackPressed()
    }

    Row {
        TouchableIcon(
            modifier = Modifier.fillMaxHeight().width(iconSize),
            onClick = onBackPressed,
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
                    onClick = deleteHistory,
                    iconDrawableID = R.drawable.baseline_delete_sweep_24,
                    contentDescription = "clear all inputs",
                    colorID = R.color.hashColor
                )
            }

            items(count = pastCrossMultipliers.size) { index ->
                var crossMultiplierHeight by remember { mutableIntStateOf(0) }
                Column {
                    Row {
                        CrossMultiplierView(
                            modifier = Modifier
                                .weight(1f)
                                .onSizeChanged { crossMultiplierHeight = it.height }
                                .aspectRatio(1f),
                            crossMultiplier = pastCrossMultipliers[index],
                            onCharacterPressedAt = { position: Pair<Int, Int>, character: String ->
                                pushCharacterToInputOnPositionOfTheCrossMultiplierAt(
                                    index, position, character
                                )
                            },
                            onBackspacePressedAt = { position: Pair<Int, Int> ->
                                popCharacterOfInputOnPositionOfTheCrossMultiplierAt(index, position)
                            },
                            onClearPressedAt = { position: Pair<Int, Int> ->
                                clearInputOnPositionOfTheCrossMultiplierAt(index, position)
                            },
                            onLongPressedAt = { position: Pair<Int, Int> ->
                                changeTheUnknownPositionToPositionOfTheCrossMultiplierAt(
                                    index, position
                                )
                            },
                        )

                        TouchableIcon(
                            modifier = Modifier.width(iconSize).height(
                                with(LocalDensity.current) { crossMultiplierHeight.toDp() }
                            ),
                            onClick = { deleteCrossMultiplierAt(index) },
                            iconDrawableID = R.drawable.baseline_delete_forever_24,
                            contentDescription = "clear input",
                            colorID = R.color.hashColor
                        )
                    }

                    if (index < pastCrossMultipliers.size - 1) {
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
    HistoryScreen(pastCrossMultipliersUiState = PastCrossMultipliersUiState.CrossMultipliersLoaded(
        listOf(
            CrossMultiplierViewData(
                valueAt00 = "${(230 * 45)/160}", valueAt01 = "230",
                valueAt10 = "45",                valueAt11 = "160",
                unknownPosition = Pair(0, 0)
            ),

            CrossMultiplierViewData(
                valueAt00 = "6.4", valueAt01 = "${(6.4 * 35.4)/3}",
                valueAt10 = "3",   valueAt11 = "35.4",
                unknownPosition = Pair(0, 1)
            ),

            CrossMultiplierViewData(
                valueAt00 = "5", valueAt01 = "4.67",
                valueAt10 = "${(5 * 3.46)/4.67}",    valueAt11 = "3.46",
                unknownPosition = Pair(1, 0)
            ),

            CrossMultiplierViewData(
                valueAt00 = "3", valueAt01 = "4500",
                valueAt10 = "7", valueAt11 = "${(7 * 4500)/3}",
                unknownPosition = Pair(1, 1)
            ),
        ))
    )
}