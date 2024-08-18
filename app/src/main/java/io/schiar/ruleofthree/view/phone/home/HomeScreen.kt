package io.schiar.ruleofthree.view.phone.home

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import io.schiar.ruleofthree.view.navigation.Navigation
import io.schiar.ruleofthree.view.phone.home.component.ScreenBarNavigator
import io.schiar.ruleofthree.view.phone.home.component.TopBar
import io.schiar.ruleofthree.view.shared.util.Screen
import io.schiar.ruleofthree.view.shared.util.ScreenInfo

@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    val screens = remember {
        listOf(Screen.CrossMultipliersCreator, Screen.History)
    }
    var currentScreenInfo by remember { mutableStateOf(
        ScreenInfo(Screen.CrossMultipliersCreator.route.id))
    }
    Scaffold(
        topBar = { TopBar(navController = navController, screenInfo = currentScreenInfo) },
        bottomBar = { ScreenBarNavigator(screens = screens, navController = navController) }
    ) { innerPadding ->
        Navigation(
            navController = navController,
            innerPadding = innerPadding,
            onChangeToolbarInfo = { screenInfo -> currentScreenInfo = screenInfo }
        )
    }
}