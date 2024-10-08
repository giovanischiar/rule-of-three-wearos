package io.schiar.ruleofthree.view.history

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.schiar.ruleofthree.view.shared.util.Route
import io.schiar.ruleofthree.view.shared.util.ScreenInfo
import io.schiar.ruleofthree.viewmodel.HistoryViewModel

fun NavGraphBuilder.historyScreen(
    historyViewModel: HistoryViewModel? = null,
    onBackPressed: () -> Unit,
    onChangeToolbarInfo: (screenInfo: ScreenInfo) -> Unit
) {
    composable(
        route = Route.HISTORY.id,
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
            onBackPressed,
            onChangeToolbarInfo
        )
    }
}