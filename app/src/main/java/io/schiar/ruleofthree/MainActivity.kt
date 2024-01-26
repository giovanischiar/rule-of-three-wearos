package io.schiar.ruleofthree

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.wear.tooling.preview.devices.WearDevices
import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSource
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSource
import io.schiar.ruleofthree.model.repository.CrossMultipliersCreatorRepository
import io.schiar.ruleofthree.model.repository.MainRepository
import io.schiar.ruleofthree.view.screen.AppScreen
import io.schiar.ruleofthree.viewmodel.AppViewModel
import io.schiar.ruleofthree.viewmodel.CrossMultipliersCreatorViewModel
import io.schiar.ruleofthree.viewmodel.HistoryViewModel
import io.schiar.ruleofthree.viewmodel.util.ViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ruleOfThreeDatabase = RuleOfThreeDatabase.getDatabase(context = applicationContext)
        val currentCrossMultiplierDataSource = CurrentCrossMultiplierDataSource(
            currentCrossMultiplierDAO = ruleOfThreeDatabase.currentCrossMultiplierDAO()
        )
        val currentCrossMultiplierRepository = CrossMultipliersCreatorRepository(
            currentCrossMultiplierDataSourceable = currentCrossMultiplierDataSource
        )
        val mainRepository = MainRepository(
            pastCrossMultipliersDataSourceable = PastCrossMultipliersDataSource(
                pastCrossMultipliersDAO = ruleOfThreeDatabase.pastCrossMultipliersDAO()
            ),
            currentCrossMultiplierDataSourceable = currentCrossMultiplierDataSource
        )
        val viewModelProvider = ViewModelProvider(
            this,
            ViewModelFactory(
                mainRepository = mainRepository,
                crossMultipliersCreatorRepository = currentCrossMultiplierRepository
            )
        )

        setContent {
            AppScreen(
                appViewModel = viewModelProvider[AppViewModel::class.java],
                crossMultipliersCreatorViewModel =
                    viewModelProvider[CrossMultipliersCreatorViewModel::class.java],
                historyViewModel = viewModelProvider[HistoryViewModel::class.java]
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
    val createNewCrossMultiplierViewModel = CrossMultipliersCreatorViewModel(
        crossMultipliersCreatorRepository = currentCrossMultipliersRepository
    )
    val historyViewModel = HistoryViewModel(historyRepository = repository)
    LaunchedEffect(Unit) { repository.loadPastCrossMultipliers() }
    AppScreen(
        appViewModel = appViewModel,
        crossMultipliersCreatorViewModel = createNewCrossMultiplierViewModel,
        historyViewModel = historyViewModel
    )
}