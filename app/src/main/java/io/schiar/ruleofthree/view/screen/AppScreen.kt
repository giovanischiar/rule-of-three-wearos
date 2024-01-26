package io.schiar.ruleofthree.view.screen

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.tooling.preview.devices.WearDevices
import io.schiar.ruleofthree.R
import io.schiar.ruleofthree.viewmodel.AppViewModel
import io.schiar.ruleofthree.viewmodel.CrossMultipliersCreatorViewModel
import io.schiar.ruleofthree.viewmodel.HistoryViewModel

@Composable
fun AppScreen(
    appViewModel: AppViewModel,
    crossMultipliersCreatorViewModel: CrossMultipliersCreatorViewModel,
    historyViewModel: HistoryViewModel,
    navController: NavHostController = rememberNavController()
) {
    val isThereHistory by appViewModel.isThereHistory.collectAsState()

    fun addCurrentCrossMultiplierToPastCrossMultipliers() {
         appViewModel.addCurrentCrossMultiplierToPastCrossMultipliers()
    }

    NavHost(
        modifier = Modifier.background(color = colorResource(R.color.backgroundColor)),
        navController = navController, startDestination = "CrossMultipliersCreator"
    ) {
        composable("CrossMultipliersCreator") {
            CrossMultipliersCreatorScreen(
                crossMultipliersCreatorViewModel = crossMultipliersCreatorViewModel,
                isThereHistory = isThereHistory,
                onSubmitClicked = ::addCurrentCrossMultiplierToPastCrossMultipliers,
                onNavigationToHistory = { navController.navigate(route = "History") }
            )
        }
        composable(
            route = "History",
            enterTransition = { slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            ) },
            popExitTransition = { slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End
            ) }
        ) {
            HistoryScreen(
                historyViewModel = historyViewModel,
                onBackPressed = navController::navigateUp
            )
        }
    }
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun AppScreenPreview() {
    AppScreen(
        appViewModel = AppViewModel(isThereHistory = true),
        crossMultipliersCreatorViewModel = CrossMultipliersCreatorViewModel(),
        historyViewModel = HistoryViewModel()
    )
}