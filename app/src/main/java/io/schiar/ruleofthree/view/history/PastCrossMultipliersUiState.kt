package io.schiar.ruleofthree.view.history

import androidx.compose.runtime.Immutable
import io.schiar.ruleofthree.viewmodel.viewdata.CrossMultiplierViewData

@Immutable
sealed interface PastCrossMultipliersUiState {
    data object Loading : PastCrossMultipliersUiState
    data class PastCrossMultipliersLoaded(
        val crossMultipliers: List<CrossMultiplierViewData>,
    ): PastCrossMultipliersUiState
}