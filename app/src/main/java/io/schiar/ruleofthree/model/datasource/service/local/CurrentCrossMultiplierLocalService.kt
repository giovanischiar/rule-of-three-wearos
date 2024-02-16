package io.schiar.ruleofthree.model.datasource.service.local

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.service.CurrentCrossMultiplierService

class CurrentCrossMultiplierLocalService(
    currentCrossMultiplierToInsert: CrossMultiplier? = null
): CurrentCrossMultiplierService {
    private var currentCrossMultiplier = currentCrossMultiplierToInsert?.withIDChangedTo(newID = 1L)

    override fun create(crossMultiplier: CrossMultiplier) {
        currentCrossMultiplier = crossMultiplier
    }

    override fun retrieve(): CrossMultiplier? {
        return currentCrossMultiplier
    }

    override fun update(crossMultiplier: CrossMultiplier) {
        currentCrossMultiplier = crossMultiplier
    }
}