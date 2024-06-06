package io.schiar.ruleofthree.view.crossmultiplierscreator

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.schiar.ruleofthree.view.crossmultiplierscreator.uistate.AreTherePastCrossMultipliersUiState
import io.schiar.ruleofthree.view.crossmultiplierscreator.uistate.CurrentCrossMultiplierUiState
import io.schiar.ruleofthree.view.shared.util.Route
import io.schiar.ruleofthree.viewmodel.CrossMultipliersCreatorViewModel

fun NavGraphBuilder.crossMultipliersCreatorScreen(
    crossMultipliersCreatorViewModel: CrossMultipliersCreatorViewModel? = null,
    onNavigateToHistory: () -> Unit
) {
    composable(Route.CROSS_MULTIPLIERS_CREATOR.id) {
        val viewModel = crossMultipliersCreatorViewModel ?: hiltViewModel()
        val crossMultiplier by viewModel.currentCrossMultiplierUiStateFlow.collectAsState(
            CurrentCrossMultiplierUiState.Loading
        )
        val areTherePastCrossMultipliers by viewModel
            .areTherePastCrossMultipliersUiStateFlow
            .collectAsState(initial = AreTherePastCrossMultipliersUiState.Loading)
        CrossMultipliersCreatorScreen(
            crossMultiplier,
            areTherePastCrossMultipliers,
            viewModel::pushCharacterToInputAt,
            viewModel::popCharacterOfInputAt,
            viewModel::clearInputOn,
            viewModel::changeTheUnknownPositionTo,
            viewModel::addToPastCrossMultipliers,
            viewModel::clearAllInputs,
            onNavigateToHistory
        )
    }
}