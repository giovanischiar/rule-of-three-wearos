package io.schiar.ruleofthree.model.datasource.local

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CurrentCrossMultiplierLocalDataSource(
    currentCrossMultiplierToInsert: CrossMultiplier? = null
): CurrentCrossMultiplierDataSource {
    private val _currentCrossMultiplier = MutableStateFlow(
        value = currentCrossMultiplierToInsert?.withIDChangedTo(newID = 1L)
    )
    private val currentCrossMultiplier: StateFlow<CrossMultiplier?> = _currentCrossMultiplier

    override suspend fun create(crossMultiplier: CrossMultiplier) {
        _currentCrossMultiplier.update { crossMultiplier }
    }

    override fun retrieve(): Flow<CrossMultiplier?> {
        return currentCrossMultiplier
    }

    override suspend fun update(crossMultiplier: CrossMultiplier) {
        _currentCrossMultiplier.update { crossMultiplier }
    }
}