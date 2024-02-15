package io.schiar.ruleofthree.model.datasource.currentcrossmultiplier.requester

import io.schiar.ruleofthree.model.CrossMultiplier

class CurrentCrossMultiplierMemoryDAO(
    currentCrossMultiplierToInsert: CrossMultiplier? = null
): CurrentCrossMultiplierDAO {
    private var currentCrossMultiplier = currentCrossMultiplierToInsert?.withIDChangedTo(newID = 1L)

    override fun create(crossMultiplier: CrossMultiplier) {
        currentCrossMultiplier = crossMultiplier
    }

    override fun requestCurrentCrossMultiplier(): CrossMultiplier? {
        return currentCrossMultiplier
    }

    override fun updateCurrentCrossMultiplierTo(crossMultiplierUpdated: CrossMultiplier) {
        currentCrossMultiplier = crossMultiplierUpdated
    }
}