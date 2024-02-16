package io.schiar.ruleofthree.model.datasource.service.local

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.service.PastCrossMultipliersService

class PastCrossMultipliersLocalService(
    crossMultipliersToInsert: List<CrossMultiplier> = emptyList()
) : PastCrossMultipliersService {
    private var currentID: Long = 1L
    private val _pastCrossMultipliers: MutableList<CrossMultiplier> =
        crossMultipliersToInsert.map { crossMultiplierToInsert ->
            crossMultiplierToInsert.withIDChangedTo(newID = currentID++)
    }.toMutableList()

    override fun create(crossMultiplier: CrossMultiplier): CrossMultiplier {
        val elementToInsert = crossMultiplier.withIDChangedTo(newID = currentID++)
        _pastCrossMultipliers.add(index = 0, element = elementToInsert)
        return elementToInsert
    }

    override fun retrieve(): List<CrossMultiplier> {
        return _pastCrossMultipliers
    }

    override fun update(crossMultiplier: CrossMultiplier) {
        val entityToUpdate = _pastCrossMultipliers.filter {
            it.id == crossMultiplier.id
        }.getOrNull(index = 0) ?: return
        val entityToUpdateIndex = _pastCrossMultipliers.indexOf(element = entityToUpdate)
        if (entityToUpdateIndex == -1) return
        _pastCrossMultipliers[entityToUpdateIndex] = crossMultiplier
    }

    override fun delete(crossMultiplier: CrossMultiplier) {
        val elementToDelete = _pastCrossMultipliers.filter { pastCrossMultiplier ->
            pastCrossMultiplier.id == crossMultiplier.id
        }.getOrNull(index = 0) ?: return
        val elementToDeleteIndex = _pastCrossMultipliers.indexOf(element = elementToDelete)
        if (elementToDeleteIndex == -1) return
        _pastCrossMultipliers.removeAt(index = elementToDeleteIndex)
    }

    override fun deleteAll() {
        _pastCrossMultipliers.clear()
    }
}