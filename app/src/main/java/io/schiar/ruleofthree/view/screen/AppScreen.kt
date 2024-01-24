package io.schiar.ruleofthree.view.screen

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import io.schiar.ruleofthree.model.Input
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSource
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSource
import io.schiar.ruleofthree.model.repository.CrossMultipliersCreatorRepository
import io.schiar.ruleofthree.model.repository.MainRepository
import io.schiar.ruleofthree.viewmodel.AppViewModel
import io.schiar.ruleofthree.viewmodel.CrossMultipliersCreatorViewModel
import io.schiar.ruleofthree.viewmodel.HistoryViewModel
import io.schiar.ruleofthree.viewmodel.PastCrossMultipliersViewModel
import kotlinx.coroutines.launch

@Composable
fun AppScreen(
    appViewModel: AppViewModel,
    crossMultiplierViewModel: PastCrossMultipliersViewModel,
    createNewCrossMultiplierViewModel: CrossMultipliersCreatorViewModel,
    historyViewModel: HistoryViewModel,
    navController: NavHostController = rememberNavController()
) {
    LaunchedEffect(Unit) { appViewModel.loadPastCrossMultipliers() }
    val coroutineScope = rememberCoroutineScope()
    val isThereHistory by appViewModel.isThereHistory.collectAsState()

    fun addCurrentCrossMultiplierToPastCrossMultipliers() = coroutineScope.launch {
         appViewModel.addCurrentCrossMultiplierToPastCrossMultipliers()
    }

    NavHost(
        modifier = Modifier.background(color = colorResource(R.color.backgroundColor)),
        navController = navController, startDestination = "CrossMultipliersCreator"
    ) {
        composable("CrossMultipliersCreator") {
            CrossMultipliersCreatorScreen(
                crossMultipliersCreatorViewModel = createNewCrossMultiplierViewModel,
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
                crossMultiplierViewModel = crossMultiplierViewModel,
                onBackPressed = navController::navigateUp
            )
        }
    }
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun AppScreenPreview() {
    val dataSource = PastCrossMultipliersDataSource(
        crossMultipliers = listOf(
            CrossMultiplier(valueAt00 = 8342234, valueAt01 = 324423, valueAt10 = 45456)
                .resultCalculated(),
            CrossMultiplier(valueAt00 = 4, valueAt01 = 40, valueAt10 = 400)
                .resultCalculated(),
            CrossMultiplier(valueAt00 = 42, valueAt01 = 440, valueAt10 = 5)
                .resultCalculated(),
            CrossMultiplier(valueAt00 = 3, valueAt01 = 10, valueAt10 = 78)
                .resultCalculated(),
            CrossMultiplier(valueAt00 = 5, valueAt01 = 135, valueAt10 = 7)
                .resultCalculated()
        )
    )
    val repository = MainRepository(pastCrossMultipliersDataSourceable = dataSource)
    val currentCrossMultipliersRepository = CrossMultipliersCreatorRepository(
        currentCrossMultiplierDataSourceable = CurrentCrossMultiplierDataSource(
            currentCrossMultiplier = CrossMultiplier(
                valueAt00 = 10,
                valueAt01 = 345,
                valueAt10 = 15.3
            ).resultCalculated(),
        )
    )
    val appViewModel = AppViewModel(appRepository = repository)
    val crossMultiplierViewModel = PastCrossMultipliersViewModel(pastCrossMultipliersRepository = repository)
    val createNewCrossMultiplierViewModel = CrossMultipliersCreatorViewModel(
        crossMultipliersCreatorRepository = currentCrossMultipliersRepository
    )
    val historyViewModel = HistoryViewModel(historyRepository = repository)
    LaunchedEffect(Unit) { repository.loadPastCrossMultipliers() }
    AppScreen(
        appViewModel = appViewModel,
        crossMultiplierViewModel = crossMultiplierViewModel,
        createNewCrossMultiplierViewModel = createNewCrossMultiplierViewModel,
        historyViewModel = historyViewModel
    )
}