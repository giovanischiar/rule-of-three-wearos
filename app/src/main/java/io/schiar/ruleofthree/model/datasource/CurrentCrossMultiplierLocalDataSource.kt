package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.CrossMultiplier

class CurrentCrossMultiplierLocalDataSource(
    currentCrossMultiplierToInsert: CrossMultiplier? = null
): CurrentCrossMultiplierDataSource {
    private var currentCrossMultiplier = currentCrossMultiplierToInsert?.withIDChangedTo(newID = 1L)

    override suspend fun create(crossMultiplier: CrossMultiplier) {
        currentCrossMultiplier = crossMultiplier
    }

    override suspend fun retrieve(): CrossMultiplier? {
        return currentCrossMultiplier
    }

    override suspend fun update(crossMultiplier: CrossMultiplier) {
        currentCrossMultiplier = crossMultiplier
    }
}