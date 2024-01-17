package io.schiar.ruleofthree.view.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.IconButton
import androidx.wear.tooling.preview.devices.WearDevices
import io.schiar.ruleofthree.R
import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CrossMultiplierDataSource
import io.schiar.ruleofthree.model.repository.MainRepository
import io.schiar.ruleofthree.view.components.CrossMultiplierView
import io.schiar.ruleofthree.viewmodel.CrossMultiplierViewModel
import kotlinx.coroutines.launch

@Composable
fun CrossMultiplierScreen(viewModel: CrossMultiplierViewModel, onNavigationToHistory: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    val crossMultiplier by viewModel.crossMultiplier.collectAsState()
    val isThereHistory by viewModel.isThereHistory.collectAsState()

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
    
    fun clearAllInputs() {
        coroutineScope.launch { viewModel.clearAllInputs() }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = colorResource(id = R.color.backgroundColor))
    ) {
        CrossMultiplierView(
            crossMultiplier = crossMultiplier,
            addInput = ::addInput,
            removeInput = ::removeInput,
            clearInput = ::clearInput,
            submitInput = ::submitInput
        )

        if (crossMultiplier.isNotEmpty()) {
            IconButton(
                modifier = Modifier.align(alignment = Alignment.BottomCenter).size(25.dp),
                onClick = { clearAllInputs() }
            ) {
                Icon(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    painter = painterResource(id = R.drawable.baseline_delete_forever_24),
                    contentDescription = "clear all inputs",
                    tint = colorResource(R.color.hashColor)
                )
            }
        }

        if (isThereHistory) {
            IconButton(
                modifier = Modifier.align(alignment = Alignment.CenterEnd).size(25.dp),
                onClick = { onNavigationToHistory() }
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
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun CrossMultiplicationScreenPreview() {
    val dataSource = CrossMultiplierDataSource(
        currentCrossMultiplier = CrossMultiplier(a = "10", b = "345", c = "15.3").resultCalculated()
    )
    val repository = MainRepository(dataSource = dataSource)
    val crossMultiplierViewModel = CrossMultiplierViewModel(repository = repository)
    LaunchedEffect(Unit) { repository.loadDatabase() }
    CrossMultiplierScreen(viewModel = crossMultiplierViewModel, onNavigationToHistory = {})
}