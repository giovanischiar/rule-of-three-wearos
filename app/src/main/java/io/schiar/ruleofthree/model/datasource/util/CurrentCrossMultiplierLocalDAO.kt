package io.schiar.ruleofthree.model.datasource.util

import io.schiar.ruleofthree.model.datasource.database.CurrentCrossMultiplierDAO
import io.schiar.ruleofthree.model.datasource.database.CurrentCrossMultiplierEntity

class CurrentCrossMultiplierLocalDAO(
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