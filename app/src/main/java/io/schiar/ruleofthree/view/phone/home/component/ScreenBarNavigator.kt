package io.schiar.ruleofthree.view.phone.home.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import io.schiar.ruleofthree.R
import io.schiar.ruleofthree.view.shared.util.Screen

@Composable
fun ScreenBarNavigator(
    screens: List<Screen>,
    navController: NavHostController
) {
    NavigationBar(containerColor = colorResource(id = R.color.backgroundColor)) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        screens.forEach { screen ->
            val selected = currentDestination?.hierarchy?.any {
                it.route == screen.route.id
            } == true

            NavigationBarItem(
                icon = {
                    screen.icon?.chooseWhether(isSelected = selected)?.let { imageVector ->
                        Icon(
                            imageVector = imageVector,
                            contentDescription = stringResource(
                                id = screen.icon.contentDescriptionStringID
                            ),
                            tint = colorResource(id = android.R.color.white)
                        )
                    }
                },
                label = {
                    Text(
                        stringResource(screen.resourceId),
                        color = colorResource(id = android.R.color.white)
                    )
                },
                selected = selected,
                onClick = {
                    navController.navigate(screen.route.id) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reelecting the same item
                        launchSingleTop = true
                        // Restore state when reelecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}