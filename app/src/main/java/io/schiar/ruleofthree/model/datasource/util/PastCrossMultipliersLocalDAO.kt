package io.schiar.ruleofthree.model.datasource.util

import io.schiar.ruleofthree.model.datasource.database.CrossMultiplierEntity
import io.schiar.ruleofthree.model.datasource.database.PastCrossMultipliersDAO

class PastCrossMultipliersLocalDAO(
    pastCrossMultipliers: List<CrossMultiplierEntity> = emptyList(),
    private var _pastCrossMultipliers: List<CrossMultiplierEntity> = emptyList()
): PastCrossMultipliersDAO() {
    private var currentID: Long = 1L

    init {
        for (pastCrossMultiplier in pastCrossMultipliers) {
            insertTimestamped(crossMultiplierEntity = pastCrossMultiplier)
        }
    }

    override fun insert(crossMultiplierEntity: CrossMultiplierEntity): Long {
        val mutablePastCrossMultipliers = selectFromPastCrossMultiplierOrderByCreatedAtDesc()
            .toMutableList()
        val elementToInsert = crossMultiplierEntity.setID(id = currentID++)
        mutablePastCrossMultipliers.add(index = 0, element = elementToInsert)
        this._pastCrossMultipliers = mutablePastCrossMultipliers
        return elementToInsert.id
    }

    override fun selectFromPastCrossMultiplierOrderByCreatedAtDesc(): List<CrossMultiplierEntity> {
        return _pastCrossMultipliers.sortedByDescending { it.createdAt }
    }

    override fun update(crossMultiplierEntity: CrossMultiplierEntity) {
        val pastCrossMultipliers = selectFromPastCrossMultiplierOrderByCreatedAtDesc()
        val entityToUpdate = pastCrossMultipliers.filter {
            it.id == crossMultiplierEntity.id
        }.getOrNull(index = 0) ?: return
        val mutableCrossMultipliers = pastCrossMultipliers.toMutableList()
        val entityToUpdateIndex = pastCrossMultipliers.indexOf(element = entityToUpdate)
        if (entityToUpdateIndex == -1) return
        mutableCrossMultipliers[entityToUpdateIndex] = crossMultiplierEntity
            .timestamped(modifiedAt = System.currentTimeMillis())
        _pastCrossMultipliers = mutableCrossMultipliers
    }

    override fun delete(crossMultiplierEntity: CrossMultiplierEntity) {
        val pastCrossMultipliers = selectFromPastCrossMultiplierOrderByCreatedAtDesc()
        val entityToDelete = pastCrossMultipliers.filter { pastCrossMultiplier ->
                pastCrossMultiplier.id == crossMultiplierEntity.id
            }.getOrNull(index = 0) ?: return
        val entityToDeleteIndex = pastCrossMultipliers.indexOf(element = entityToDelete)
        if (entityToDeleteIndex == -1) return
        val mutableCrossMultipliers = pastCrossMultipliers.toMutableList()
        mutableCrossMultipliers.removeAt(index = entityToDeleteIndex)
        _pastCrossMultipliers = mutableCrossMultipliers
    }

    override fun deleteFromPastCrossMultipliers() {
        _pastCrossMultipliers = emptyList()
    }
}