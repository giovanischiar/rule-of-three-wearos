package io.schiar.ruleofthree

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import io.schiar.ruleofthree.model.datasource.CrossMultiplierDataSource
import io.schiar.ruleofthree.model.datasource.room.RuleOfThreeDatabase
import io.schiar.ruleofthree.model.repository.MainRepository
import io.schiar.ruleofthree.view.screen.MainScreen
import io.schiar.ruleofthree.viewmodel.AppViewModel
import io.schiar.ruleofthree.viewmodel.HistoryViewModel
import io.schiar.ruleofthree.viewmodel.CrossMultiplierViewModel
import io.schiar.ruleofthree.viewmodel.util.ViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ruleOfThreeDatabase = RuleOfThreeDatabase.getDatabase(context = applicationContext)
        val crossMultiplierDAO = ruleOfThreeDatabase.crossMultiplierDAO()
        val repository = MainRepository(dataSource = CrossMultiplierDataSource(
            crossMultiplierDAO = crossMultiplierDAO)
        )
        val viewModelProvider = ViewModelProvider(
            this,
            ViewModelFactory(repository = repository)
        )

        setContent {
            MainScreen(
                appViewModel = viewModelProvider[AppViewModel::class.java],
                crossMultiplierViewModel = viewModelProvider[CrossMultiplierViewModel::class.java],
                historyViewModel = viewModelProvider[HistoryViewModel::class.java]
            )
        }
    }
}