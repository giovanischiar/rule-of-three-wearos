package io.schiar.ruleofthree.view.crossmultiplierscreator.uistate

import androidx.compose.runtime.Immutable
import io.schiar.ruleofthree.viewmodel.viewdata.CrossMultiplierViewData

@Immutable
sealed interface CurrentCrossMultiplierUiState {
    data object Loading : CurrentCrossMultiplierUiState
    data class CurrentCrossMultiplierLoaded(
        val crossMultiplier: CrossMultiplierViewData,
    ): CurrentCrossMultiplierUiState
}