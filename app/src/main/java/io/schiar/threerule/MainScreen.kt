package io.schiar.threerule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import io.schiar.threerule.components.NumberInput
import java.text.DecimalFormat
import java.text.NumberFormat

@Composable
fun MainScreen() {
    var result by remember { mutableStateOf("?") }

    val values by remember { mutableStateOf(arrayOf(0.0, 0.0, 0.0)) }

    fun onValueChange(position: Int, value: Double) {
        values[position] = value

        result = if (values[0] != 0.0) {
            (values[1] * values[2] / values[0]).toString()
        } else {
            "?"
        }
    }

    val decimalFormat = DecimalFormat("#,###.00")

    Column(
        modifier = Modifier.fillMaxSize().padding(all = 25.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .weight(1f)
        ) {
            NumberInput(
                modifier = Modifier.fillMaxSize().weight(1f),
                onValueChanged = {
                onValueChange(0, it)
            })
            NumberInput(
                modifier = Modifier.fillMaxSize().weight(1f),
                onValueChanged = {
                onValueChange(1, it)
            })
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NumberInput(
                modifier = Modifier.fillMaxSize().weight(1f),
                onValueChanged = {
                onValueChange(2, it)
            })
            Box(
                modifier = Modifier.fillMaxSize().weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = if (result != "?") decimalFormat.format(result.toDouble()) else result,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3
                )
            }
        }
    }
}