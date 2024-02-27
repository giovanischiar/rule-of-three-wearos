package io.schiar.ruleofthree.model.datasource.local

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PastCrossMultipliersLocalDataSource(
    crossMultipliersToInsert: List<CrossMultiplier> = emptyList()
) : PastCrossMultipliersDataSource {
    private var currentID: Long = 1L
    private val _pastCrossMultipliers = MutableStateFlow(
        value = crossMultipliersToInsert.map {
            crossMultiplierToInsert -> crossMultiplierToInsert.withIDChangedTo(newID = currentID++)
        }
    )
    private val pastCrossMultipliers: StateFlow<List<CrossMultiplier>> = _pastCrossMultipliers

    override suspend fun create(crossMultiplier: CrossMultiplier): CrossMultiplier {
        val elementToInsert = crossMultiplier.withIDChangedTo(newID = currentID++)
        _pastCrossMultipliers.update {
            it.toMutableList().apply { add(index = 0, element = elementToInsert) }
        }
        return elementToInsert
    }

    override fun retrieve(): Flow<List<CrossMultiplier>> {
        return pastCrossMultipliers
    }

    override suspend fun update(crossMultiplier: CrossMultiplier) {
        _pastCrossMultipliers.update { crossMultipliers ->
            crossMultipliers.map {
                if (it.id == crossMultiplier.id) {
                    crossMultiplier
                } else {
                    it
                }
            }
        }

    }

    override suspend fun delete(crossMultiplier: CrossMultiplier) {
        _pastCrossMultipliers.update {
            it.toMutableList().apply { remove(crossMultiplier) }
        }
    }

    override suspend fun deleteAll() {
        _pastCrossMultipliers.update { emptyList() }
    }
}