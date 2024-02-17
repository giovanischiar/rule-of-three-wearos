package io.schiar.ruleofthree.library.room

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSource

class PastCrossMultipliersRoomDataSource(
    private val pastCrossMultipliersRoomDAO: PastCrossMultipliersRoomDAO
): PastCrossMultipliersDataSource {
    override suspend fun create(crossMultiplier: CrossMultiplier): CrossMultiplier {
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

    override suspend fun retrieve(): List<CrossMultiplier> {
        return pastCrossMultipliersRoomDAO
            .selectFromPastCrossMultiplierOrderByCreatedAtDesc()
            .toCrossMultipliers()
    }

    override suspend fun update(crossMultiplier: CrossMultiplier) {
        val crossMultiplierFromDatabase = pastCrossMultipliersRoomDAO.select(
            id = crossMultiplier.id
        ) ?: return
        val crossMultiplierEntityUpdated = crossMultiplier.toCrossMultiplierEntity().timestamped(
            createdAt = crossMultiplierFromDatabase.createdAt,
            modifiedAt = System.currentTimeMillis()
        )
        pastCrossMultipliersRoomDAO.update(
            crossMultiplierEntity = crossMultiplierEntityUpdated
        )
    }

    override suspend fun delete(crossMultiplier: CrossMultiplier) {
        val crossMultiplierEntityToDelete = crossMultiplier.toCrossMultiplierEntity()
        pastCrossMultipliersRoomDAO.delete(crossMultiplierEntity = crossMultiplierEntityToDelete)
    }

    override suspend fun deleteAll() {
        pastCrossMultipliersRoomDAO.deleteFromPastCrossMultipliers()
    }
}