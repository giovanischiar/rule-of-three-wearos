package io.schiar.ruleofthree.library.room

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSource

class CurrentCrossMultiplierRoomDataSource(
    private val currentCrossMultiplierRoomDAO: CurrentCrossMultiplierRoomDAO
): CurrentCrossMultiplierDataSource {
    override suspend fun create(crossMultiplier: CrossMultiplier) {
        val currentCrossMultiplierEntity = crossMultiplier.toCurrentCrossMultiplierEntity()
        currentCrossMultiplierRoomDAO.insert(currentCrossMultiplierEntity.apply {
            createdAt = System.currentTimeMillis()
            modifiedAt = System.currentTimeMillis()
        })
    }

    override suspend fun retrieve(): CrossMultiplier? {
        return currentCrossMultiplierRoomDAO.select()?.toCrossMultiplier()
    }

    override suspend fun update(crossMultiplier: CrossMultiplier) {
        currentCrossMultiplierRoomDAO.update(
            crossMultiplier.toCurrentCrossMultiplierEntity().apply {
                modifiedAt = System.currentTimeMillis()
            }
        )
    }
}