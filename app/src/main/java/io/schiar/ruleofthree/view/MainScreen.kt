package io.schiar.ruleofthree.view

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import io.schiar.ruleofthree.R
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
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 25.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxSize().weight(1f)) {
                NumberInput(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    displayValue = numbers.a,
                    onDigitPressed = { value -> viewModel.addInput(value = value, position = 0) },
                    onErasePressed = { viewModel.removeInput(position = 0) },
                    onClearPressed = { viewModel.clearInput(position = 0) }
                )
                NumberInput(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    displayValue = numbers.b,
                    onDigitPressed = { value -> viewModel.addInput(value = value, position = 1) },
                    onErasePressed = { viewModel.removeInput(position = 1) },
                    onClearPressed = { viewModel.clearInput(position = 1) }
                )
            }

            Row(modifier = Modifier.fillMaxSize().weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NumberInput(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    displayValue = numbers.c,
                    onDigitPressed = { value -> viewModel.addInput(value = value, position = 2) },
                    onErasePressed = { viewModel.removeInput(position = 2) },
                    onClearPressed = { viewModel.clearInput(position = 2) }
                )
                Box(
                    modifier = Modifier.fillMaxSize().weight(1f),
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
            color = colorResource(id = R.color.xColor),
            fontSize = 24.sp
        )
    }
}