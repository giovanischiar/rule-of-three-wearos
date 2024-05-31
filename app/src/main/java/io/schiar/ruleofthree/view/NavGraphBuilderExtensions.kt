package io.schiar.ruleofthree.view

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.schiar.ruleofthree.view.screen.HistoryScreen
import io.schiar.ruleofthree.view.uistate.PastCrossMultipliersUiState
import io.schiar.ruleofthree.viewmodel.HistoryViewModel

fun NavGraphBuilder.historyScreen(
    historyViewModel: HistoryViewModel? = null,
    onBackPressed: () -> Unit
) {
    composable(
        route = "History",
        enterTransition = { slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Start
        ) },
        popExitTransition = { slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.End
        ) }
    ) {
        val viewModel = historyViewModel ?: hiltViewModel()
        val pastCrossMultipliersUiState by viewModel
            .pastCrossMultipliersUiStateFlow
            .collectAsState(PastCrossMultipliersUiState.Loading)

        HistoryScreen(
            pastCrossMultipliersUiState,
            viewModel::deleteHistory,
            viewModel::pushCharacterToInputOnPositionOfTheCrossMultiplierAt,
            viewModel::popCharacterOfInputOnPositionOfTheCrossMultiplierAt,
            viewModel::clearInputOnPositionOfTheCrossMultiplierAt,
            viewModel::changeTheUnknownPositionToPositionOfTheCrossMultiplierAt,
            viewModel::deleteCrossMultiplierAt,
            onBackPressed
        )
    }
}