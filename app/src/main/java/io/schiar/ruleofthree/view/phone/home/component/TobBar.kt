package io.schiar.ruleofthree.view.phone.home.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import io.schiar.ruleofthree.R
import io.schiar.ruleofthree.view.shared.util.ScreenInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController, screenInfo: ScreenInfo) {
    TopAppBar(
        title = { Text(screenInfo.title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.backgroundColor),
            titleContentColor = colorResource(id = android.R.color.white)
        ),
        actions = screenInfo.actions,
        navigationIcon = {
            if (navController.previousBackStackEntry == null) return@TopAppBar
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = colorResource(id = android.R.color.white)
                )
            }
        }
    )
}