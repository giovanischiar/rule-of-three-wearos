package io.schiar.ruleofthree.library.room

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.service.CurrentCrossMultiplierService

class CurrentCrossMultiplierRoomService(
    private val currentCrossMultiplierRoomDAO: CurrentCrossMultiplierRoomDAO
): CurrentCrossMultiplierService {
    override fun create(crossMultiplier: CrossMultiplier) {
        val currentCrossMultiplierEntity = crossMultiplier.toCurrentCrossMultiplierEntity()
        currentCrossMultiplierRoomDAO.insert(currentCrossMultiplierEntity.apply {
            createdAt = System.currentTimeMillis()
            modifiedAt = System.currentTimeMillis()
        })
    }

    override fun retrieve(): CrossMultiplier? {
        return currentCrossMultiplierRoomDAO.select()?.toCrossMultiplier()
    }

    override fun update(crossMultiplier: CrossMultiplier) {
        currentCrossMultiplierRoomDAO.update(
            crossMultiplier.toCurrentCrossMultiplierEntity().apply {
                modifiedAt = System.currentTimeMillis()
            }
        )
    }
}