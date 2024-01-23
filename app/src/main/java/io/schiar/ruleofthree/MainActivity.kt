package io.schiar.ruleofthree

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSource
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSource
import io.schiar.ruleofthree.model.repository.CrossMultipliersCreatorRepository
import io.schiar.ruleofthree.model.repository.MainRepository
import io.schiar.ruleofthree.view.screen.AppScreen
import io.schiar.ruleofthree.viewmodel.AppViewModel
import io.schiar.ruleofthree.viewmodel.CrossMultipliersCreatorViewModel
import io.schiar.ruleofthree.viewmodel.HistoryViewModel
import io.schiar.ruleofthree.viewmodel.PastCrossMultipliersViewModel
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
                crossMultiplierViewModel =
                    viewModelProvider[PastCrossMultipliersViewModel::class.java],
                createNewCrossMultiplierViewModel =
                    viewModelProvider[CrossMultipliersCreatorViewModel::class.java],
                historyViewModel = viewModelProvider[HistoryViewModel::class.java]
            )
        }
    }
}