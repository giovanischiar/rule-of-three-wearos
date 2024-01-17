package io.schiar.ruleofthree.view.screen

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.tooling.preview.devices.WearDevices
import io.schiar.ruleofthree.model.Numbers
import io.schiar.ruleofthree.model.datasource.NumbersDataSource
import io.schiar.ruleofthree.model.repository.MainRepository
import io.schiar.ruleofthree.viewmodel.AppViewModel
import io.schiar.ruleofthree.viewmodel.HistoryViewModel
import io.schiar.ruleofthree.viewmodel.NumbersViewModel

@Composable
fun MainScreen(
    appViewModel: AppViewModel,
    numbersViewModel: NumbersViewModel,
    historyViewModel: HistoryViewModel,
    navController: NavHostController = rememberNavController()
) {
    LaunchedEffect(Unit) { appViewModel.loadDatabase() }

    NavHost(navController = navController, startDestination = "Numbers") {
        composable("Numbers") {
            NumbersScreen(viewModel = numbersViewModel, onNavigationNumbers = {
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
fun MainScreenPreview() {
    val dataSource = NumbersDataSource(
        currentNumbers = Numbers(a = "10", b = "345", c = "15.3").resultCalculated(),
        allPastNumbers = mutableListOf(
            Numbers(a = "8342234", b = "324423", c = "45456").resultCalculated(),
            Numbers(a = "4", b = "40", c = "400").resultCalculated(),
            Numbers(a = "42", b = "440", c = "5").resultCalculated(),
            Numbers(a = "3", b = "10", c = "78").resultCalculated(),
            Numbers(a = "5", b = "135", c = "7").resultCalculated()
        )
    )
    val repository = MainRepository(dataSource = dataSource)
    val appViewModel = AppViewModel(repository = repository)
    val numbersViewModel = NumbersViewModel(repository = repository)
    val historyViewModel = HistoryViewModel(repository = repository)
    LaunchedEffect(Unit) { repository.loadDatabase() }
    MainScreen(
        appViewModel = appViewModel,
        numbersViewModel = numbersViewModel,
        historyViewModel = historyViewModel
    )
}