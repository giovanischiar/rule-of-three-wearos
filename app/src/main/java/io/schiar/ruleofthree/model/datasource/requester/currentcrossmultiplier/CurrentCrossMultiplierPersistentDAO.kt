package io.schiar.ruleofthree.model.datasource.requester.currentcrossmultiplier

import io.schiar.ruleofthree.library.room.CurrentCrossMultiplierRoomDAO
import io.schiar.ruleofthree.model.CrossMultiplier

class CurrentCrossMultiplierPersistentDAO(
    private val currentCrossMultiplierRoomDAO: CurrentCrossMultiplierRoomDAO
): CurrentCrossMultiplierDAO {
    override fun create(crossMultiplier: CrossMultiplier) {
        val currentCrossMultiplierEntity = crossMultiplier.toCurrentCrossMultiplierEntity()
        currentCrossMultiplierRoomDAO.insert(currentCrossMultiplierEntity.apply {
            createdAt = System.currentTimeMillis()
            modifiedAt = System.currentTimeMillis()
        })
    }

    override fun requestCurrentCrossMultiplier(): CrossMultiplier? {
        return currentCrossMultiplierRoomDAO.select()?.toCrossMultiplier()
    }

    override fun updateCurrentCrossMultiplierTo(crossMultiplierUpdated: CrossMultiplier) {
        currentCrossMultiplierRoomDAO.update(
            crossMultiplierUpdated.toCurrentCrossMultiplierEntity().apply {
                modifiedAt = System.currentTimeMillis()
            }
        )
    }
}