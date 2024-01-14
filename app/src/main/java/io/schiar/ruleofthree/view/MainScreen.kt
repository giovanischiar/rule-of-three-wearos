package io.schiar.ruleofthree.view

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import io.schiar.ruleofthree.R
import io.schiar.ruleofthree.model.Numbers
import io.schiar.ruleofthree.model.datasource.DataSource
import io.schiar.ruleofthree.model.repository.MainRepository
import io.schiar.ruleofthree.model.repository.NumbersRepository
import io.schiar.ruleofthree.view.components.NumberInput
import io.schiar.ruleofthree.viewmodel.NumbersViewModel
import kotlin.math.sqrt

@Composable
fun MainScreen(viewModel: NumbersViewModel) {
    LaunchedEffect(Unit) { viewModel.init() }
    val numbers by viewModel.numbers.collectAsState()
    val result by viewModel.result.collectAsState()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = colorResource(id = R.color.backgroundColor))
    ) {
        Column(
            modifier = Modifier.padding(all = 25.dp).fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.weight(1f) ) {
                NumberInput(
                    modifier = Modifier.weight(1f),
                    displayValue = numbers.a,
                    onDigitPressed = { value -> viewModel.addInput(value = value, position = 0) },
                    onErasePressed = { viewModel.removeInput(position = 0) },
                    onClearPressed = { viewModel.clearInput(position = 0) }
                )
                NumberInput(
                    modifier = Modifier.weight(1f),
                    displayValue = numbers.b,
                    onDigitPressed = { value -> viewModel.addInput(value = value, position = 1) },
                    onErasePressed = { viewModel.removeInput(position = 1) },
                    onClearPressed = { viewModel.clearInput(position = 1) }
                )
            }

            Row(modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NumberInput(
                    modifier = Modifier.weight(1f),
                    displayValue = numbers.c,
                    onDigitPressed = { value -> viewModel.addInput(value = value, position = 2) },
                    onErasePressed = { viewModel.removeInput(position = 2) },
                    onClearPressed = { viewModel.clearInput(position = 2) }
                )
                Box(
                    modifier = Modifier.weight(1f).padding(all = 15.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        textAlign = TextAlign.Center,
                        text = result,
                        color = colorResource(id = R.color.questionMarkColor),
                        fontSize = calculateTextUnitBasedOn(length = result.length)
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
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun MainScreenPreview() {
    val dataSource = object: DataSource {
        var numbers = Numbers(a = "4", b = "40", c = "400")
        override fun requestCurrentNumbers(): Numbers { return numbers }
        override fun updateCurrentNumbers(numbers: Numbers) { this.numbers = numbers }
    }
    MainScreen(
        viewModel = NumbersViewModel(repository = MainRepository(dataSource = dataSource))
    )
}