package io.schiar.ruleofthree.view.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.tooling.preview.devices.WearDevices
import io.schiar.ruleofthree.R
import io.schiar.ruleofthree.view.components.CrossMultiplierView
import io.schiar.ruleofthree.view.components.TouchableIcon
import io.schiar.ruleofthree.viewmodel.CrossMultipliersCreatorViewModel
import io.schiar.ruleofthree.viewmodel.viewdata.CrossMultiplierViewData

@Composable
fun CrossMultipliersCreatorScreen(
    crossMultipliersCreatorViewModel: CrossMultipliersCreatorViewModel,
    onSubmitPressed: () -> Unit = {},
    onNavigationToHistory: () -> Unit = {}
) {
    val crossMultiplier by crossMultipliersCreatorViewModel.crossMultiplier.collectAsState()
    val areTherePastCrossMultipliers by crossMultipliersCreatorViewModel
        .areTherePastCrossMultipliers
        .collectAsState()
    CrossMultipliersCreatorScreen(
        crossMultiplier,
        areTherePastCrossMultipliers,
        crossMultipliersCreatorViewModel::pushCharacterToInputAt,
        crossMultipliersCreatorViewModel::popCharacterOfInputAt,
        crossMultipliersCreatorViewModel::clearInputOn,
        crossMultipliersCreatorViewModel::changeTheUnknownPositionTo,
        onSubmitPressed,
        crossMultipliersCreatorViewModel::clearAllInputs,
        onNavigationToHistory
    )
}

@Composable
internal fun CrossMultipliersCreatorScreen(
    crossMultiplier: CrossMultiplierViewData,
    areTherePastCrossMultipliers: Boolean = false,
    pushCharacterToInputAt: (position: Pair<Int, Int>, character: String) -> Unit = {_,_->},
    popCharacterOfInputAt: (position: Pair<Int, Int>) -> Unit = {},
    clearInputOn: (position: Pair<Int, Int>) -> Unit = {},
    changeTheUnknownPositionTo: (position: Pair<Int, Int>) -> Unit = {},
    onSubmitPressed: () -> Unit = {},
    clearAllInputs: () -> Unit = {},
    onNavigationToHistory: () -> Unit = {}
) {
    val iconSize = 30.dp
    Row {
        Column(modifier = Modifier.weight(1f)) {
            CrossMultiplierView(
                modifier = Modifier.weight(1f).padding(start = iconSize, top = iconSize),
                crossMultiplier = crossMultiplier,
                onCharacterPressedAt = pushCharacterToInputAt,
                onBackspacePressedAt = popCharacterOfInputAt,
                onClearPressedAt = clearInputOn,
                onLongPressedAt = changeTheUnknownPositionTo,
                onSubmitPressed = onSubmitPressed
            )

            TouchableIcon(
                modifier = Modifier.fillMaxWidth().height(iconSize).padding(start = iconSize),
                onClick = clearAllInputs,
                iconDrawableID = R.drawable.baseline_delete_forever_24,
                contentDescription = "clear all inputs",
                colorID = R.color.hashColor,
                visible = crossMultiplier.isNotEmpty()
            )
        }

        TouchableIcon(
            modifier = Modifier.fillMaxHeight().width(iconSize),
            onClick = onNavigationToHistory,
            iconDrawableID = R.drawable.baseline_history_24,
            contentDescription = "history",
            colorID = R.color.hashColor,
            visible = areTherePastCrossMultipliers
        )
    }
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun CrossMultiplicationScreenEmptyPreview() {
    CrossMultipliersCreatorScreen(
        crossMultiplier = CrossMultiplierViewData()
    )
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun CurrentCrossMultiplierScreenWithNumbersPreview() {
    CrossMultipliersCreatorScreen(
        crossMultiplier = CrossMultiplierViewData(
            valueAt00 ="10", valueAt01= "345",
            valueAt11 ="15.3",
            unknownPosition = Pair(0, 1)
        )
    )
}

@Preview(device = WearDevices.SMALL_ROUND, uiMode = Configuration.UI_MODE_TYPE_WATCH)
@Composable
fun CurrentCrossMultiplierScreenWithNumbersAndHistoryPreview() {
    CrossMultipliersCreatorScreen(
        crossMultiplier = CrossMultiplierViewData(
            valueAt00 = "45", valueAt01 = "160",
            valueAt10 = "62", valueAt11 = "${(160 * 62)/45}"
        ),
        areTherePastCrossMultipliers = true
    )
}