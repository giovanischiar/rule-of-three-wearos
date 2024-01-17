package io.schiar.ruleofthree.view.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.IconButton
import androidx.wear.tooling.preview.devices.WearDevices
import io.schiar.ruleofthree.R
import io.schiar.ruleofthree.model.Numbers
import io.schiar.ruleofthree.model.datasource.NumbersDataSource
import io.schiar.ruleofthree.model.repository.MainRepository
import io.schiar.ruleofthree.view.calculateTextUnitBasedOn
import io.schiar.ruleofthree.view.components.NumberInput
import io.schiar.ruleofthree.viewmodel.NumbersViewModel
import kotlinx.coroutines.launch

@Composable
fun NumbersScreen(viewModel: NumbersViewModel, onNavigationNumbers: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    val numbers by viewModel.numbers.collectAsState()

    fun addInput(value: String, position: Int) {
        coroutineScope.launch { viewModel.addInput(value = value, position = position) }
    }

    fun removeInput(position: Int) {
        coroutineScope.launch { viewModel.removeInput(position = position) }
    }

    fun clearInput(position: Int) {
        coroutineScope.launch { viewModel.clearInput(position = position) }
    }

    fun submitInput() {
        coroutineScope.launch { viewModel.submitInput() }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = colorResource(id = R.color.backgroundColor))
    ) {
        Column(
            modifier = Modifier
                .padding(all = 25.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.weight(1f) ) {
                NumberInput(
                    modifier = Modifier.weight(1f),
                    displayValue = numbers.a,
                    onDigitPressed = { addInput(value = it, position = 0) },
                    onErasePressed = { removeInput(position = 0) },
                    onClearPressed = { clearInput(position = 0) },
                    onEnterPressed = { submitInput() }
                )
                NumberInput(
                    modifier = Modifier.weight(1f),
                    displayValue = numbers.b,
                    onDigitPressed = { addInput(value = it, position = 1) },
                    onErasePressed = { removeInput(position = 1) },
                    onClearPressed = { clearInput(position = 1) },
                    onEnterPressed = { submitInput() }
                )
            }

            Row(modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NumberInput(
                    modifier = Modifier.weight(1f),
                    displayValue = numbers.c,
                    onDigitPressed = { addInput(value = it, position = 2) },
                    onErasePressed = { removeInput(position = 2) },
                    onClearPressed = { clearInput(position = 2) },
                    onEnterPressed = { submitInput() }
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(all = 15.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        textAlign = TextAlign.Center,
                        text = numbers.result,
                        color = colorResource(id = R.color.questionMarkColor),
                        fontSize = calculateTextUnitBasedOn(length = numbers.result.length)
                    )
                }
            }
        }
        Text(
            modifier = Modifier.align(alignment = Alignment.Center),
            text = "×",
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.xColor),
            fontSize = 24.sp
        )

        IconButton(
            modifier = Modifier
                .align(alignment = Alignment.CenterEnd)
                .size(25.dp),
            onClick = { onNavigationNumbers() }
        ) {
            Icon(
                modifier = Modifier.padding(horizontal = 5.dp),
                painter = painterResource(id = android.R.drawable.ic_menu_recent_history),
                contentDescription = "history",
                tint = colorResource(R.color.hashColor)
            )
        }
    }
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun NumbersScreenPreview() {
    val dataSource = NumbersDataSource(
        currentNumbers = Numbers(a = "10", b = "345", c = "15.3").resultCalculated()
    )
    val repository = MainRepository(dataSource = dataSource)
    val numbersViewModel = NumbersViewModel(repository = repository)
    LaunchedEffect(Unit) { repository.loadDatabase() }
    NumbersScreen(viewModel = numbersViewModel, onNavigationNumbers = {})
}