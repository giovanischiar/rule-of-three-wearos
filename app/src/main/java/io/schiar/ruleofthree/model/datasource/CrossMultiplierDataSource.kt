package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.room.CrossMultiplierDAO
import io.schiar.ruleofthree.model.datasource.util.CrossMultiplierLocalDAO
import io.schiar.ruleofthree.model.datasource.util.toEntity
import io.schiar.ruleofthree.model.datasource.util.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CrossMultiplierDataSource(private val crossMultiplierDAO: CrossMultiplierDAO): DataSource {
    private var allPastCrossMultipliers: List<CrossMultiplier>? = null
    private var currentCrossMultiplier: CrossMultiplier? = null

    constructor(
        allPastCrossMultipliers: List<CrossMultiplier> = emptyList(),
        currentCrossMultiplier: CrossMultiplier = CrossMultiplier()
    ) : this(
        crossMultiplierDAO = CrossMultiplierLocalDAO(
            currentCrossMultiplier = currentCrossMultiplier,
            allPastCrossMultipliers = allPastCrossMultipliers
        )
    )

    override suspend fun requestCurrentCrossMultiplier(): CrossMultiplier {
        if (currentCrossMultiplier == null) {
            currentCrossMultiplier = withContext(Dispatchers.IO) {
                (crossMultiplierDAO.selectCurrentCrossMultiplier())?.toModel()
            }

            if (currentCrossMultiplier == null) {
                val newCrossMultiplier = CrossMultiplier()
                coroutineScope {
                    launch(Dispatchers.IO) {
                        crossMultiplierDAO.insert(newCrossMultiplier.toEntity(id = 1))
                    }
                }
                currentCrossMultiplier = newCrossMultiplier
            }
        }
        return currentCrossMultiplier as CrossMultiplier
    }

    override suspend fun updateCurrentCrossMultiplier(crossMultiplier: CrossMultiplier) {
        currentCrossMultiplier = crossMultiplier
        coroutineScope {
            launch(Dispatchers.IO) {
                crossMultiplierDAO.update(crossMultiplier.toEntity(id = 1))
            }
        }
    }

    override suspend fun requestAllPastCrossMultipliers(): List<CrossMultiplier> {
        if (allPastCrossMultipliers == null) {
            allPastCrossMultipliers = withContext(Dispatchers.IO) {
                crossMultiplierDAO.selectAllPastCrossMultipliers().map {
                    it.toModel()
                }
            }
        }
        return allPastCrossMultipliers as List<CrossMultiplier>
    }

    override suspend fun addToAllPastCrossMultipliers(crossMultiplier: CrossMultiplier) {
        allPastCrossMultipliers = listOf(crossMultiplier) + (allPastCrossMultipliers ?: emptyList())
        coroutineScope {
            launch(Dispatchers.IO) {
                crossMultiplierDAO.insert(crossMultiplier.toEntity())
            }
        }
    }

    override suspend fun replaceCurrentCrossMultiplier(index: Int) {
        val allPastCrossMultipliers = requestAllPastCrossMultipliers().toMutableList()
        val crossMultiplierToReplace = allPastCrossMultipliers.getOrNull(index) ?: return
        updateCurrentCrossMultiplier(crossMultiplier = crossMultiplierToReplace)
    }

    override suspend fun deleteHistoryItem(index: Int) {
        val allPastCrossMultipliers = requestAllPastCrossMultipliers().toMutableList()
        val crossMultiplierDeleted = allPastCrossMultipliers.removeAt(index)
        this.allPastCrossMultipliers = allPastCrossMultipliers
        val (_, a, b, c, d, unknownPosition) = crossMultiplierDeleted.toEntity()
        coroutineScope {
            launch(Dispatchers.IO) {
                crossMultiplierDAO.deleteHistoryItem(
                    a = a, b = b, c = c, d = d, unknownPosition = unknownPosition
                )
            }
        }
    }

    override suspend fun deleteHistory() {
        allPastCrossMultipliers = emptyList()
        coroutineScope {
            launch(Dispatchers.IO) {
                crossMultiplierDAO.deleteHistory()
            }
        }
    }
}