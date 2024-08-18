package io.schiar.ruleofthree.view.shared.util

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import io.schiar.ruleofthree.R

sealed class Screen(
    val route: Route,
    @StringRes val resourceId: Int,
    val icon: Icon? = null
) {
    data object CrossMultipliersCreator : Screen(
        route = Route.CROSS_MULTIPLIERS_CREATOR,
        resourceId = R.string.cross_multipliers_creator_screen,
        icon = Icon(
            selected = Icons.Filled.Home,
            unselected = Icons.Outlined.Home,
            contentDescriptionStringID = R.string.cross_multipliers_creator_screen
        )
    )

    data object History : Screen(
        route = Route.HISTORY,
        resourceId = R.string.history_screen,
        icon = Icon(
            selected = Icons.Filled.Menu,
            unselected = Icons.Outlined.Menu,
            contentDescriptionStringID = R.string.history_screen
        )
    )
}