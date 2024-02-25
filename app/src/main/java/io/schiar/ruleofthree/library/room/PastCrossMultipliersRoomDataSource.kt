package io.schiar.ruleofthree.library.room

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PastCrossMultipliersRoomDataSource @Inject constructor(
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

    override fun retrieve(): Flow<List<CrossMultiplier>> {
        return pastCrossMultipliersRoomDAO
            .selectFromPastCrossMultiplierOrderByCreatedAtDesc()
            .map { it.toCrossMultipliers() }
    }

    override suspend fun update(crossMultiplier: CrossMultiplier) {
        val crossMultiplierFromDatabase = pastCrossMultipliersRoomDAO.select(
            id = crossMultiplier.id
        ) ?: return
        val crossMultiplierEntityUpdated = crossMultiplier.toCrossMultiplierEntity().timestamped(
            createdAt = crossMultiplierFromDatabase.createdAt,
            modifiedAt = System.currentTimeMillis()
        )
        pastCrossMultipliersRoomDAO.update(crossMultiplierEntity = crossMultiplierEntityUpdated)
    }

    override suspend fun delete(crossMultiplier: CrossMultiplier) {
        val crossMultiplierEntityToDelete = crossMultiplier.toCrossMultiplierEntity()
        pastCrossMultipliersRoomDAO.delete(crossMultiplierEntity = crossMultiplierEntityToDelete)
    }

    override suspend fun deleteAll() {
        pastCrossMultipliersRoomDAO.deleteFromPastCrossMultipliers()
    }
}