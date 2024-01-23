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
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSource
import io.schiar.ruleofthree.model.repository.CrossMultipliersCreatorRepository
import io.schiar.ruleofthree.view.components.CrossMultiplierView
import io.schiar.ruleofthree.view.components.TouchableIcon
import io.schiar.ruleofthree.viewmodel.CrossMultipliersCreatorViewModel
import kotlinx.coroutines.launch

@Composable
fun CrossMultipliersCreatorScreen(
    crossMultipliersCreatorViewModel: CrossMultipliersCreatorViewModel,
    isThereHistory: Boolean = false,
    onSubmitClicked: () -> Unit = {},
    onNavigationToHistory: () -> Unit = {},
) {
    LaunchedEffect(Unit) { crossMultipliersCreatorViewModel.loadCurrentCrossMultipliers() }
    val coroutineScope = rememberCoroutineScope()
    val crossMultiplier by crossMultipliersCreatorViewModel.crossMultiplier.collectAsState()
    val iconSize = 30.dp

    fun addValueToInputAt(value: String, position: Pair<Int, Int>) {
        coroutineScope.launch { crossMultipliersCreatorViewModel.pushCharacterToInputAt(character = value, position = position) }
    }

    fun backspaceInputAt(position: Pair<Int, Int>) {
        coroutineScope.launch { crossMultipliersCreatorViewModel.popCharacterOfInputAt(position = position) }
    }

    fun clearInputOn(position: Pair<Int, Int>) {
        coroutineScope.launch { crossMultipliersCreatorViewModel.clearInputOn(position = position) }
    }

    fun changeUnknownPosition(position: Pair<Int, Int>) {
        coroutineScope.launch { crossMultipliersCreatorViewModel.changeTheUnknownPositionTo(position = position) }
    }

    fun handleSubmitClicked() { onSubmitClicked() }

    fun clearAllInputs() { coroutineScope.launch { crossMultipliersCreatorViewModel.clearAllInputs() } }

    Row {
        Column(modifier = Modifier.weight(1f)) {
            CrossMultiplierView(
                modifier = Modifier.weight(1f).padding(start = iconSize, top = iconSize),
                crossMultiplier = crossMultiplier,
                onValueAddedToInput = ::addValueToInputAt,
                onInputBackspaced = ::backspaceInputAt,
                onInputCleared = ::clearInputOn,
                onInputLongClicked = ::changeUnknownPosition,
                onSubmitClicked = ::handleSubmitClicked
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
    val viewModel = CrossMultipliersCreatorViewModel()
    LaunchedEffect(Unit) { viewModel.loadCurrentCrossMultipliers() }
    CrossMultipliersCreatorScreen(crossMultipliersCreatorViewModel = viewModel)
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun CurrentCrossMultiplierScreenWithNumbersPreview() {
    val dataSource = CurrentCrossMultiplierDataSource(
        CrossMultiplier(a = "10", b = "345", c = "15.3").resultCalculated()
    )
    val repository = CrossMultipliersCreatorRepository(
        currentCrossMultiplierDataSourceable = dataSource
    )
    val viewModel = CrossMultipliersCreatorViewModel(crossMultipliersCreatorRepository = repository)
    LaunchedEffect(Unit) { viewModel.loadCurrentCrossMultipliers() }
    CrossMultipliersCreatorScreen(crossMultipliersCreatorViewModel = viewModel)
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun CurrentCrossMultiplierScreenWithNumbersAndHistoryPreview() {
    val dataSource = CurrentCrossMultiplierDataSource(
        currentCrossMultiplier = CrossMultiplier(a = "10", b = "35", c = "15.3").resultCalculated(),
    )
    val repository = CrossMultipliersCreatorRepository(
        currentCrossMultiplierDataSourceable = dataSource
    )
    val viewModel = CrossMultipliersCreatorViewModel(
        crossMultipliersCreatorRepository = repository
    )
    LaunchedEffect(Unit) { viewModel.loadCurrentCrossMultipliers() }
    CrossMultipliersCreatorScreen(
        crossMultipliersCreatorViewModel = viewModel,
        isThereHistory = true
    )
}