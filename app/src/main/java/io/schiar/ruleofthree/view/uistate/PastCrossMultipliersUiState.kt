package io.schiar.ruleofthree.view.uistate

import androidx.compose.runtime.Immutable
import io.schiar.ruleofthree.viewmodel.viewdata.CrossMultiplierViewData

@Immutable
sealed interface PastCrossMultipliersUiState {
    data object Loading : PastCrossMultipliersUiState
    data class CrossMultipliersLoaded(
        val crossMultipliers: List<CrossMultiplierViewData>,
    ): PastCrossMultipliersUiState
}