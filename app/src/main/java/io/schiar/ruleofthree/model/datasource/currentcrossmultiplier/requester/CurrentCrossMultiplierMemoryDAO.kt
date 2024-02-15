package io.schiar.ruleofthree.model.datasource.currentcrossmultiplier.requester

import io.schiar.ruleofthree.library.room.CurrentCrossMultiplierDAO
import io.schiar.ruleofthree.library.room.CurrentCrossMultiplierEntity

class CurrentCrossMultiplierMemoryDAO(
    private var currentCrossMultiplier: CurrentCrossMultiplierEntity? = null
): CurrentCrossMultiplierDAO() {
    override fun insert(currentCrossMultiplierEntity: CurrentCrossMultiplierEntity): Long {
        val currentCrossMultiplierID = 1L
        currentCrossMultiplier = currentCrossMultiplierEntity
        return currentCrossMultiplierID
    }

    override fun update(currentCrossMultiplierEntity: CurrentCrossMultiplierEntity) {
        currentCrossMultiplier = currentCrossMultiplierEntity
    }

    override fun select(): CurrentCrossMultiplierEntity? {
        return currentCrossMultiplier
    }
}