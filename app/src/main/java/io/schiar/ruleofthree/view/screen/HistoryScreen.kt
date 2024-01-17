package io.schiar.ruleofthree.view.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.rememberActiveFocusRequester
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.IconButton
import androidx.wear.tooling.preview.devices.WearDevices
import io.schiar.ruleofthree.R
import io.schiar.ruleofthree.model.Numbers
import io.schiar.ruleofthree.model.datasource.NumbersDataSource
import io.schiar.ruleofthree.model.repository.MainRepository
import io.schiar.ruleofthree.view.components.NumbersHistoryView
import io.schiar.ruleofthree.viewmodel.HistoryViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun HistoryScreen(viewModel: HistoryViewModel, onBackPressed: () -> Unit = {}) {
    val allPastNumbers by viewModel.allPastNumbers.collectAsState()
    if (allPastNumbers.isEmpty()) {
        onBackPressed()
    }

    val listState = rememberLazyListState()
    val focusRequester = rememberActiveFocusRequester()
    val coroutineScope = rememberCoroutineScope()

    fun deleteHistoryItem(index: Int) {
        coroutineScope.launch { viewModel.deleteHistoryItem(index = index) }
    }

    Box(modifier = Modifier.background(color = colorResource(id = R.color.backgroundColor))) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
                item { Spacer(modifier = Modifier.fillMaxWidth().height(25.dp)) }
                items(count = allPastNumbers.size) {
                    val (a, b, c, result) = allPastNumbers[it]

                    Box {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 45.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Row {
                                NumbersHistoryView(modifier = Modifier.weight(1f), value = a)
                                NumbersHistoryView(modifier = Modifier.weight(1f), value = b)
                            }

                            Row {
                                NumbersHistoryView(modifier = Modifier.weight(1f), value = c)
                                NumbersHistoryView(
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

                        IconButton(
                            modifier = Modifier
                                .align(alignment = Alignment.CenterEnd)
                                .size(25.dp),
                            onClick = { deleteHistoryItem(it) }
                        ) {
                            Icon(
                                modifier = Modifier.padding(horizontal = 5.dp),
                                painter = painterResource(id = R.drawable.baseline_delete_forever_24),
                                contentDescription = "clear all inputs",
                                tint = colorResource(R.color.hashColor)
                            )
                        }
                    }

                    if (it < allPastNumbers.size - 1) {
                        Divider(
                            modifier = Modifier.padding(vertical = 5.dp, horizontal = 25.dp),
                            color = colorResource(id = R.color.squareStrokeColor),
                            thickness = 1.dp
                        )
                    }
                }
                item { Spacer(modifier = Modifier.fillMaxWidth().height(25.dp)) }
            }
        }

        IconButton(
            modifier = Modifier
                .align(alignment = Alignment.CenterStart)
                .size(25.dp),
            onClick = { onBackPressed() }
        ) {
            Icon(
                modifier = Modifier.padding(horizontal = 5.dp),
                painter = painterResource(id = R.drawable.baseline_arrow_back_24dp),
                contentDescription = "back",
                tint = colorResource(R.color.hashColor)
            )
        }
    }
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun HistoryScreenPreview() {
    val dataSource = NumbersDataSource(
        allPastNumbers = mutableListOf(
            Numbers(a = "8342234", b = "324423", c = "45456").resultCalculated(),
            Numbers(a = "4", b = "40", c = "400").resultCalculated(),
            Numbers(a = "42", b = "440", c = "5").resultCalculated(),
            Numbers(a = "3", b = "10", c = "78").resultCalculated(),
            Numbers(a = "5", b = "135", c = "7").resultCalculated()
        )
    )
    val repository = MainRepository(dataSource = dataSource)
    val historyViewModel = HistoryViewModel(repository = repository)
    LaunchedEffect(Unit) { repository.loadDatabase() }
    HistoryScreen(viewModel = historyViewModel)
}