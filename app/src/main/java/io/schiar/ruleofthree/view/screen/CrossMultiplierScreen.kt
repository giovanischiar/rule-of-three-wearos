package io.schiar.ruleofthree.view.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.tooling.preview.devices.WearDevices
import io.schiar.ruleofthree.R
import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CrossMultiplierDataSource
import io.schiar.ruleofthree.model.repository.MainRepository
import io.schiar.ruleofthree.view.components.CrossMultiplierView
import io.schiar.ruleofthree.view.components.TouchableIcon
import io.schiar.ruleofthree.viewmodel.CrossMultiplierViewModel
import kotlinx.coroutines.launch

@Composable
fun CrossMultiplierScreen(viewModel: CrossMultiplierViewModel, onNavigationToHistory: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val crossMultiplier by viewModel.crossMultiplier.collectAsState()
    val isThereHistory by viewModel.isThereHistory.collectAsState()

    val iconSize = 30.dp

    fun addInput(value: String, position: Pair<Int, Int>) {
        coroutineScope.launch { viewModel.addInput(value = value, position = position) }
    }

    fun removeInput(position: Pair<Int, Int>) {
        coroutineScope.launch { viewModel.removeInput(position = position) }
    }

    fun clearInput(position: Pair<Int, Int>) {
        coroutineScope.launch { viewModel.clearInput(position = position) }
    }

    fun submitInput() {
        coroutineScope.launch { viewModel.submitInput() }
    }

    fun changeUnknownPosition(position: Pair<Int, Int>) {
        coroutineScope.launch { viewModel.changeUnknownPosition(position = position) }
    }
    
    fun clearAllInputs() {
        coroutineScope.launch { viewModel.clearAllInputs() }
    }

    Row {
        Column(modifier = Modifier.weight(1f)) {
            CrossMultiplierView(
                modifier = Modifier.weight(1f).padding(start = iconSize, top = iconSize),
                crossMultiplier = crossMultiplier,
                addInput = ::addInput,
                removeInput = ::removeInput,
                clearInput = ::clearInput,
                submit = ::submitInput,
                changeUnknownPosition = ::changeUnknownPosition
            )

            TouchableIcon(
                modifier = Modifier.fillMaxWidth().height(iconSize).padding(start = iconSize),
                onClick = { clearAllInputs() },
                iconDrawableID = R.drawable.baseline_delete_forever_24,
                contentDescription = "clear all inputs",
                colorID = R.color.hashColor,
                visible = crossMultiplier.isNotEmpty()
            )
        }

        TouchableIcon(
            modifier = Modifier.fillMaxHeight().width(iconSize),
            onClick = { onNavigationToHistory() },
            iconDrawableID = R.drawable.baseline_history_24,
            contentDescription = "history",
            colorID = R.color.hashColor,
            visible = isThereHistory
        )
    }
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun CrossMultiplicationScreenEmptyPreview() {
    val dataSource = CrossMultiplierDataSource(currentCrossMultiplier = CrossMultiplier())
    val repository = MainRepository(dataSource = dataSource)
    val crossMultiplierViewModel = CrossMultiplierViewModel(repository = repository)
    LaunchedEffect(Unit) { repository.loadDatabase() }
    CrossMultiplierScreen(viewModel = crossMultiplierViewModel, onNavigationToHistory = {})
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun CrossMultiplicationScreenWithNumbersPreview() {
    val dataSource = CrossMultiplierDataSource(
        currentCrossMultiplier = CrossMultiplier(a = "10", b = "345", c = "15.3").resultCalculated()
    )
    val repository = MainRepository(dataSource = dataSource)
    val crossMultiplierViewModel = CrossMultiplierViewModel(repository = repository)
    LaunchedEffect(Unit) { repository.loadDatabase() }
    CrossMultiplierScreen(viewModel = crossMultiplierViewModel, onNavigationToHistory = {})
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun CrossMultiplicationScreenWithNumbersAndHistoryPreview() {
    val dataSource = CrossMultiplierDataSource(
        currentCrossMultiplier = CrossMultiplier(a = "10", b = "35", c = "15.3").resultCalculated(),
        allPastCrossMultipliers = listOf(
            CrossMultiplier(a = "10", b = "345", c = "15.3").resultCalculated()
        )
    )
    val repository = MainRepository(dataSource = dataSource)
    val crossMultiplierViewModel = CrossMultiplierViewModel(repository = repository)
    LaunchedEffect(Unit) { repository.loadDatabase() }
    CrossMultiplierScreen(viewModel = crossMultiplierViewModel, onNavigationToHistory = {})
}