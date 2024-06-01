package io.schiar.ruleofthree.view.navigation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import io.schiar.ruleofthree.R
import io.schiar.ruleofthree.view.crossmultiplierscreator.crossMultipliersCreatorScreen
import io.schiar.ruleofthree.view.history.historyScreen
import io.schiar.ruleofthree.view.shared.util.Route
import io.schiar.ruleofthree.viewmodel.CrossMultipliersCreatorViewModel
import io.schiar.ruleofthree.viewmodel.HistoryViewModel

@Composable
fun Navigation(
    crossMultipliersCreatorViewModel: CrossMultipliersCreatorViewModel? = null,
    historyViewModel: HistoryViewModel? = null,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = Modifier.background(color = colorResource(R.color.backgroundColor)),
        navController = navController, startDestination = Route.CROSS_MULTIPLIERS_CREATOR.id
    ) {
        crossMultipliersCreatorScreen(
            crossMultipliersCreatorViewModel = crossMultipliersCreatorViewModel,
            onNavigateToHistory = { navController.navigate(route = Route.HISTORY.id) }
        )

        historyScreen(
            historyViewModel = historyViewModel,
            onBackPressed = navController::navigateUp
        )
    }
}