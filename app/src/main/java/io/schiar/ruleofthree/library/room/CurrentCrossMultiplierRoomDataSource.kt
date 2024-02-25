package io.schiar.ruleofthree.library.room

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrentCrossMultiplierRoomDataSource @Inject constructor(
    private val currentCrossMultiplierRoomDAO: CurrentCrossMultiplierRoomDAO
): CurrentCrossMultiplierDataSource {
    override suspend fun create(crossMultiplier: CrossMultiplier) {
        val currentCrossMultiplierEntity = crossMultiplier.toCurrentCrossMultiplierEntity()
        currentCrossMultiplierRoomDAO.insert(currentCrossMultiplierEntity.apply {
            createdAt = System.currentTimeMillis()
            modifiedAt = System.currentTimeMillis()
        })
    }

    override fun retrieve(): Flow<CrossMultiplier?> {
        return currentCrossMultiplierRoomDAO.select().map { it?.toCrossMultiplier() }
    }

    override suspend fun update(crossMultiplier: CrossMultiplier) {
        currentCrossMultiplierRoomDAO.insert(
            crossMultiplier.toCurrentCrossMultiplierEntity().apply {
                modifiedAt = System.currentTimeMillis()
            }
        )
    }
}