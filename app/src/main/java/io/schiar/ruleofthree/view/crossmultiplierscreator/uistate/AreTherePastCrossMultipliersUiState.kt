package io.schiar.ruleofthree.view.crossmultiplierscreator.uistate

import androidx.compose.runtime.Immutable

@Immutable
sealed interface AreTherePastCrossMultipliersUiState {
    data object Loading : AreTherePastCrossMultipliersUiState
    data class AreThereCrossMultipliersLoaded(
        val areTherePastCrossMultipliers: Boolean,
    ): AreTherePastCrossMultipliersUiState
}