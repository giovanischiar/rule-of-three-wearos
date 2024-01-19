package io.schiar.ruleofthree.view.screen

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.tooling.preview.devices.WearDevices
import io.schiar.ruleofthree.R
import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CrossMultiplierDataSource
import io.schiar.ruleofthree.model.repository.MainRepository
import io.schiar.ruleofthree.viewmodel.AppViewModel
import io.schiar.ruleofthree.viewmodel.CrossMultiplierViewModel
import io.schiar.ruleofthree.viewmodel.HistoryViewModel

@Composable
fun AppScreen(
    appViewModel: AppViewModel,
    crossMultiplierViewModel: CrossMultiplierViewModel,
    historyViewModel: HistoryViewModel,
    navController: NavHostController = rememberNavController()
) {
    LaunchedEffect(Unit) { appViewModel.loadDatabase() }

    NavHost(
        modifier = Modifier.background(color = colorResource(R.color.backgroundColor)),
        navController = navController, startDestination = "CrossMultiplier"
    ) {
        composable("CrossMultiplier") {
            CrossMultiplierScreen(viewModel = crossMultiplierViewModel, onNavigationToHistory = {
                navController.navigate(route = "History")
            })
        }
        composable(
            route = "History",
            enterTransition = { slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            ) },
            popExitTransition = { slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End
            ) }
        ) { HistoryScreen(viewModel = historyViewModel, onBackPressed = {
            navController.navigateUp()
        }) }
    }
}
@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun AppScreenPreview() {
    val dataSource = CrossMultiplierDataSource(
        currentCrossMultiplier = CrossMultiplier(a = "10", b = "345", c = "15.3").resultCalculated(),
        allPastCrossMultipliers = mutableListOf(
            CrossMultiplier(a = "8342234", b = "324423", c = "45456").resultCalculated(),
            CrossMultiplier(a = "4", b = "40", c = "400").resultCalculated(),
            CrossMultiplier(a = "42", b = "440", c = "5").resultCalculated(),
            CrossMultiplier(a = "3", b = "10", c = "78").resultCalculated(),
            CrossMultiplier(a = "5", b = "135", c = "7").resultCalculated()
        )
    )
    val repository = MainRepository(dataSource = dataSource)
    val appViewModel = AppViewModel(repository = repository)
    val crossMultiplierViewModel = CrossMultiplierViewModel(repository = repository)
    val historyViewModel = HistoryViewModel(repository = repository)
    LaunchedEffect(Unit) { repository.loadDatabase() }
    AppScreen(
        appViewModel = appViewModel,
        crossMultiplierViewModel = crossMultiplierViewModel,
        historyViewModel = historyViewModel
    )
}