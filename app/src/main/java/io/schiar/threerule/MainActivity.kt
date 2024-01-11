package io.schiar.threerule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.schiar.threerule.view.MainScreen
import io.schiar.threerule.viewmodel.NumbersViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainScreen(viewModel = NumbersViewModel()) }
    }
}