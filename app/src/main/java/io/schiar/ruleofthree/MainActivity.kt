package io.schiar.ruleofthree

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.wear.tooling.preview.devices.WearDevices
import io.schiar.ruleofthree.library.room.RuleOfThreeRoomDatabase
import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.library.room.CurrentCrossMultiplierRoomDataSource
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSource
import io.schiar.ruleofthree.library.room.PastCrossMultipliersRoomService
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierLocalDataSource
import io.schiar.ruleofthree.model.repository.AppRepository
import io.schiar.ruleofthree.model.repository.CrossMultipliersCreatorRepository
import io.schiar.ruleofthree.model.repository.HistoryRepository
import io.schiar.ruleofthree.view.screen.AppScreen
import io.schiar.ruleofthree.viewmodel.AppViewModel
import io.schiar.ruleofthree.viewmodel.CrossMultipliersCreatorViewModel
import io.schiar.ruleofthree.viewmodel.HistoryViewModel
import io.schiar.ruleofthree.viewmodel.util.ViewModelFactory
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ruleOfThreeDatabase = RuleOfThreeRoomDatabase.getDatabase(context = applicationContext)
        val currentCrossMultiplierDataSource = CurrentCrossMultiplierRoomDataSource(
            currentCrossMultiplierRoomDAO = ruleOfThreeDatabase.currentCrossMultiplierDAO()
        )
        val pastCrossMultipliersDataSource = PastCrossMultipliersDataSource(
            pastCrossMultipliersService = PastCrossMultipliersRoomService(
                pastCrossMultipliersRoomDAO = ruleOfThreeDatabase.pastCrossMultipliersDAO()
            )
        )
        val crossMultipliersCreatorRepository = CrossMultipliersCreatorRepository(
            currentCrossMultiplierDataSource = currentCrossMultiplierDataSource
        )
        val historyRepository = HistoryRepository(
            pastCrossMultipliersDataSource = pastCrossMultipliersDataSource,
            areTherePastCrossMultipliersListener = crossMultipliersCreatorRepository
        )
        val appRepository = AppRepository(
            pastCrossMultipliersDataSource = pastCrossMultipliersDataSource,
            currentCrossMultiplierDataSource = currentCrossMultiplierDataSource,
            pastCrossMultipliersListener = historyRepository,
            areTherePastCrossMultipliersListener = crossMultipliersCreatorRepository
        )
        val viewModelProvider = ViewModelProvider(
            this,
            ViewModelFactory(
                appRepository = appRepository,
                crossMultipliersCreatorRepository = crossMultipliersCreatorRepository,
                historyRepository = historyRepository
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

    @Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
    @Composable
    fun MainActivityPreview() {
        val currentCrossMultiplierDataSource = CurrentCrossMultiplierLocalDataSource(
            currentCrossMultiplierToInsert = CrossMultiplier(
                valueAt00 = 10, valueAt01 = 345,
                valueAt10 = 15.3
            ).resultCalculated()
        )
        val pastCrossMultipliersDataSource = PastCrossMultipliersDataSource(
            crossMultipliers = listOf(
                CrossMultiplier(
                    valueAt00 = 45, valueAt01 = 160,
                                    valueAt11 = 200,
                    unknownPosition = Pair(1, 0)
                ).resultCalculated(),
                CrossMultiplier(
                                    valueAt01 = 34,
                    valueAt10 = 72, valueAt11 = 14.5,
                    unknownPosition = Pair(0, 0)
                ).resultCalculated(),
                CrossMultiplier(
                    valueAt00 = 48,
                    valueAt10 = 120, valueAt11 = 100,
                    unknownPosition = Pair(0, 1)
                ).resultCalculated(),
                CrossMultiplier(
                    valueAt00 = 1.4, valueAt01 = 92.45,
                    valueAt10 = 5,
                    unknownPosition = Pair(1, 1)
                ).resultCalculated()
            ),
            coroutineDispatcher = Dispatchers.Main
        )
        val crossMultipliersCreatorRepository = CrossMultipliersCreatorRepository(
            currentCrossMultiplierDataSource = currentCrossMultiplierDataSource,
            coroutineDispatcher = Dispatchers.Main
        )
        val historyRepository = HistoryRepository(
            pastCrossMultipliersDataSource = pastCrossMultipliersDataSource,
            areTherePastCrossMultipliersListener = crossMultipliersCreatorRepository
        )
        val appRepository = AppRepository(
            pastCrossMultipliersDataSource = pastCrossMultipliersDataSource,
            currentCrossMultiplierDataSource = currentCrossMultiplierDataSource,
            pastCrossMultipliersListener = historyRepository,
            areTherePastCrossMultipliersListener = crossMultipliersCreatorRepository,
            coroutineDispatcher = Dispatchers.Main
        )
        val appViewModel = AppViewModel(appRepository = appRepository)
        val createNewCrossMultiplierViewModel = CrossMultipliersCreatorViewModel(
            crossMultipliersCreatorRepository = crossMultipliersCreatorRepository
        )
        val historyViewModel = HistoryViewModel(historyRepository = historyRepository)
        AppScreen(
            appViewModel = appViewModel,
            crossMultipliersCreatorViewModel = createNewCrossMultiplierViewModel,
            historyViewModel = historyViewModel
        )
    }
}