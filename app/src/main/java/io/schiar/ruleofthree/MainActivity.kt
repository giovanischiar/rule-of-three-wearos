package io.schiar.ruleofthree

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.schiar.ruleofthree.view.MainScreen
import io.schiar.ruleofthree.viewmodel.NumbersViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainScreen(viewModel = NumbersViewModel()) }
    }
}