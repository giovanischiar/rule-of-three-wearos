package io.schiar.ruleofthree

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import io.schiar.ruleofthree.model.repository.MainRepository
import io.schiar.ruleofthree.view.screen.MainScreen
import io.schiar.ruleofthree.viewmodel.HistoryViewModel
import io.schiar.ruleofthree.viewmodel.NumbersViewModel
import io.schiar.ruleofthree.viewmodel.util.ViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = MainRepository()
        val viewModelProvider = ViewModelProvider(
            this,
            ViewModelFactory(repository = repository)
        )

        setContent {
            MainScreen(
                numbersViewModel = viewModelProvider[NumbersViewModel::class.java],
                historyViewModel = viewModelProvider[HistoryViewModel::class.java]
            )
        }
    }
}