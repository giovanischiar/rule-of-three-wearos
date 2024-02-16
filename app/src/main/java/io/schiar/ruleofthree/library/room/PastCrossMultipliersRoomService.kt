package io.schiar.ruleofthree.library.room

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.service.PastCrossMultipliersService

class PastCrossMultipliersRoomService(
    private val pastCrossMultipliersRoomDAO: PastCrossMultipliersRoomDAO
): PastCrossMultipliersService {
    override fun create(crossMultiplier: CrossMultiplier): CrossMultiplier {
        val crossMultiplierEntity = crossMultiplier.toCrossMultiplierEntity()
        val crossMultiplierEntityToInsert = crossMultiplierEntity.timestamped(
            createdAt = System.currentTimeMillis(),
            modifiedAt = System.currentTimeMillis()
        )
        val crossMultiplierEntityToInsertID = pastCrossMultipliersRoomDAO.insert(
            crossMultiplierEntity = crossMultiplierEntityToInsert
        )
        return crossMultiplier.withIDChangedTo(newID = crossMultiplierEntityToInsertID)
    }

    override fun retrieve(): List<CrossMultiplier> {
        return pastCrossMultipliersRoomDAO
            .selectFromPastCrossMultiplierOrderByCreatedAtDesc()
            .toCrossMultipliers()
    }

    override fun update(crossMultiplier: CrossMultiplier) {
        val crossMultiplierEntityUpdated = crossMultiplier.toCrossMultiplierEntity()
        pastCrossMultipliersRoomDAO.update(
            crossMultiplierEntity = crossMultiplierEntityUpdated.timestamped(
                modifiedAt = System.currentTimeMillis()
            )
        )
    }

    override fun delete(crossMultiplier: CrossMultiplier) {
        val crossMultiplierEntityToDelete = crossMultiplier.toCrossMultiplierEntity()
        pastCrossMultipliersRoomDAO.delete(crossMultiplierEntity = crossMultiplierEntityToDelete)
    }

    override fun deleteAll() {
        pastCrossMultipliersRoomDAO.deleteFromPastCrossMultipliers()
    }
}