package io.schiar.threerule.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import io.schiar.threerule.view.components.NumberInput
import io.schiar.threerule.viewmodel.NumbersViewModel

@Composable
fun MainScreen(viewModel: NumbersViewModel) {
    LaunchedEffect(Unit) { viewModel.init() }
    val numbers by viewModel.numbers.collectAsState()
    val result by viewModel.result.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(all = 25.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxSize().weight(1f)) {
            NumberInput(
                modifier = Modifier.fillMaxSize().weight(1f),
                userInput = numbers.a,
                onInputAdded = { value -> viewModel.addInput(value = value, position = 0) },
                onErase = { viewModel.removeInput(position = 0) },
                onClear = { viewModel.clearInput(position = 0) }
            )
            NumberInput(
                modifier = Modifier.fillMaxSize().weight(1f),
                userInput = numbers.b,
                onInputAdded = { value -> viewModel.addInput(value = value, position = 1) },
                onErase = { viewModel.removeInput(position = 1) },
                onClear = { viewModel.clearInput(position = 1) }
            )
        }

        Row(
            modifier = Modifier.fillMaxSize().weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NumberInput(
                modifier = Modifier.fillMaxSize().weight(1f),
                userInput = numbers.c,
                onInputAdded = { value -> viewModel.addInput(value = value, position = 2) },
                onErase = { viewModel.removeInput(position = 2) },
                onClear = { viewModel.clearInput(position = 2) }
            )
            Box(
                modifier = Modifier.fillMaxSize().weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = result,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3
                )
            }
        }
    }
}