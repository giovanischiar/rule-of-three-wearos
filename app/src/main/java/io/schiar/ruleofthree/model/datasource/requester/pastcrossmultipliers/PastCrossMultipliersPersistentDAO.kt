package io.schiar.ruleofthree.model.datasource.requester.pastcrossmultipliers

import io.schiar.ruleofthree.library.room.PastCrossMultipliersRoomDAO
import io.schiar.ruleofthree.model.CrossMultiplier

class PastCrossMultipliersPersistentDAO(
    private val pastCrossMultipliersRoomDAO: PastCrossMultipliersRoomDAO
): PastCrossMultipliersDAO {
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

    override fun requestPastCrossMultipliers(): List<CrossMultiplier> {
        return pastCrossMultipliersRoomDAO
            .selectFromPastCrossMultiplierOrderByCreatedAtDesc()
            .toCrossMultipliers()
    }

    override fun updateCrossMultiplierTo(crossMultiplierUpdated: CrossMultiplier) {
        val crossMultiplierEntityUpdated = crossMultiplierUpdated.toCrossMultiplierEntity()
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