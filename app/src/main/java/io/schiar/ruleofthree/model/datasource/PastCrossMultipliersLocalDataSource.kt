package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.CrossMultiplier

class PastCrossMultipliersLocalDataSource(
    crossMultipliersToInsert: List<CrossMultiplier> = emptyList()
) : PastCrossMultipliersDataSource {
    private var currentID: Long = 1L
    private val _pastCrossMultipliers: MutableList<CrossMultiplier> =
        crossMultipliersToInsert.map { crossMultiplierToInsert ->
            crossMultiplierToInsert.withIDChangedTo(newID = currentID++)
    }.toMutableList()

    override suspend fun create(crossMultiplier: CrossMultiplier): CrossMultiplier {
        val elementToInsert = crossMultiplier.withIDChangedTo(newID = currentID++)
        _pastCrossMultipliers.add(index = 0, element = elementToInsert)
        return elementToInsert
    }

    override suspend fun retrieve(): List<CrossMultiplier> {
        return _pastCrossMultipliers
    }

    override suspend fun update(crossMultiplier: CrossMultiplier) {
        val entityToUpdate = _pastCrossMultipliers.filter {
            it.id == crossMultiplier.id
        }.getOrNull(index = 0) ?: return
        val entityToUpdateIndex = _pastCrossMultipliers.indexOf(element = entityToUpdate)
        if (entityToUpdateIndex == -1) return
        _pastCrossMultipliers[entityToUpdateIndex] = crossMultiplier
    }

    override suspend fun delete(crossMultiplier: CrossMultiplier) {
        val elementToDelete = _pastCrossMultipliers.filter { pastCrossMultiplier ->
            pastCrossMultiplier.id == crossMultiplier.id
        }.getOrNull(index = 0) ?: return
        val elementToDeleteIndex = _pastCrossMultipliers.indexOf(element = elementToDelete)
        if (elementToDeleteIndex == -1) return
        _pastCrossMultipliers.removeAt(index = elementToDeleteIndex)
    }

    override suspend fun deleteAll() {
        _pastCrossMultipliers.clear()
    }
}