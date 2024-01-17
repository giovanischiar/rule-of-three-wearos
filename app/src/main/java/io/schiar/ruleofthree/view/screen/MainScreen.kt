package io.schiar.ruleofthree.view.screen

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.schiar.ruleofthree.model.Numbers
import io.schiar.ruleofthree.model.datasource.NumbersDataSource
import io.schiar.ruleofthree.model.datasource.util.NumbersLocalDAO
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
            navController.popBackStack()
        }) }
    }
}
@Preview(device = Devices.WEAR_OS_LARGE_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun MainScreenPreview() {
    val repository = MainRepository(dataSource = NumbersDataSource(
        NumbersLocalDAO(currentNumbers = Numbers(a = "4", b = "40", c = "400")))
    )
    val appViewModel = AppViewModel(repository = repository)
    val numbersViewModel = NumbersViewModel(repository = repository)
    val historyViewModel = HistoryViewModel(repository = repository)

    MainScreen(
        appViewModel = appViewModel,
        numbersViewModel = numbersViewModel,
        historyViewModel = historyViewModel
    )
}