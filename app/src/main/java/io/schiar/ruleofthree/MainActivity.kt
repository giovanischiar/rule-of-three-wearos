package io.schiar.ruleofthree

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.tooling.preview.devices.WearDevices
import dagger.hilt.android.AndroidEntryPoint
import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierLocalDataSource
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersLocalDataSource
import io.schiar.ruleofthree.model.repository.AppRepository
import io.schiar.ruleofthree.model.repository.CrossMultipliersCreatorRepository
import io.schiar.ruleofthree.model.repository.HistoryRepository
import io.schiar.ruleofthree.view.screen.AppScreen
import io.schiar.ruleofthree.viewmodel.AppViewModel
import io.schiar.ruleofthree.viewmodel.CrossMultipliersCreatorViewModel
import io.schiar.ruleofthree.viewmodel.HistoryViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val appViewModel: AppViewModel by viewModels()
    private val crossMultipliersCreatorViewModel: CrossMultipliersCreatorViewModel by viewModels()
    private val historyViewModel: HistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppScreen(
                appViewModel = appViewModel,
                crossMultipliersCreatorViewModel = crossMultipliersCreatorViewModel,
                historyViewModel = historyViewModel
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
        val pastCrossMultipliersDataSource = PastCrossMultipliersLocalDataSource(
            crossMultipliersToInsert = listOf(
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
            )
        )
        val crossMultipliersCreatorRepository = CrossMultipliersCreatorRepository(
            currentCrossMultiplierDataSource = currentCrossMultiplierDataSource,
        )
        val historyRepository = HistoryRepository(
            pastCrossMultipliersDataSource = pastCrossMultipliersDataSource,
            areTherePastCrossMultipliersListener = crossMultipliersCreatorRepository
        )
        val appRepository = AppRepository(
            pastCrossMultipliersDataSource = pastCrossMultipliersDataSource,
            currentCrossMultiplierDataSource = currentCrossMultiplierDataSource
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