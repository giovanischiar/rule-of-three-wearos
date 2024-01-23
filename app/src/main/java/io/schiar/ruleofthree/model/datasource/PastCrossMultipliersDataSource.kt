package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.database.PastCrossMultipliersDAO
import io.schiar.ruleofthree.model.datasource.util.PastCrossMultipliersLocalDAO
import io.schiar.ruleofthree.model.datasource.util.toCrossMultiplier
import io.schiar.ruleofthree.model.datasource.util.toCrossMultiplierEntity
import io.schiar.ruleofthree.model.datasource.util.toCrossMultipliers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PastCrossMultipliersDataSource(
    private val pastCrossMultipliersDAO: PastCrossMultipliersDAO = PastCrossMultipliersLocalDAO()
): PastCrossMultipliersDataSourceable {
    private var pastCrossMultipliers: List<CrossMultiplier>? = null

    constructor(crossMultipliers: List<CrossMultiplier>) : this(
        pastCrossMultipliersDAO = PastCrossMultipliersLocalDAO(
            pastCrossMultipliers = crossMultipliers.map { crossMultiplier ->
                crossMultiplier.toCrossMultiplierEntity()
            }
        )
    )

    override suspend fun createPastCrossMultiplier(crossMultiplierToBeCreated: CrossMultiplier) {
        val mutablePastCrossMultipliers = retrievePastCrossMultipliers().toMutableList()
        val crossMultiplierInserted = withContext(Dispatchers.IO) {
            pastCrossMultipliersDAO.insertTimestamped(
                crossMultiplierEntity = crossMultiplierToBeCreated.toCrossMultiplierEntity()
            )
        }
        mutablePastCrossMultipliers.add(index = 0, crossMultiplierInserted.toCrossMultiplier())
        pastCrossMultipliers = mutablePastCrossMultipliers
    }

    override suspend fun retrievePastCrossMultiplierAt(index: Int): CrossMultiplier? {
        return retrievePastCrossMultipliers().getOrNull(index = index)
    }

    override suspend fun retrievePastCrossMultipliers(): List<CrossMultiplier> {
        if (pastCrossMultipliers == null) {
            val pastCrossMultipliersFromDataBase = withContext(Dispatchers.IO) {
                pastCrossMultipliersDAO.selectFromPastCrossMultiplierOrderByCreatedAtDesc()
            }.toCrossMultipliers()

            if (pastCrossMultipliersFromDataBase.isNotEmpty()) {
                pastCrossMultipliers = pastCrossMultipliersFromDataBase
            }
        }
        return pastCrossMultipliers ?: emptyList()
    }

    override suspend fun updatePastCrossMultiplier(crossMultiplierUpdated: CrossMultiplier) {
        coroutineScope {
            launch(Dispatchers.IO) {
                pastCrossMultipliersDAO.updateCrossMultiplierEntityTimestamped(
                    crossMultiplierEntityUpdated = crossMultiplierUpdated.toCrossMultiplierEntity()
                )
            }
        }
    }

    override suspend fun deletePastCrossMultiplierAt(index: Int) {
        val mutablePastCrossMultipliers = retrievePastCrossMultipliers().toMutableList()
        mutablePastCrossMultipliers.removeAt(index = index)
        pastCrossMultipliers = mutablePastCrossMultipliers
        val crossMultiplierToBeDeleted = retrievePastCrossMultiplierAt(index = index) ?: return
        coroutineScope {
            launch(Dispatchers.IO) {
                pastCrossMultipliersDAO.delete(
                    crossMultiplierEntity = crossMultiplierToBeDeleted.toCrossMultiplierEntity()
                )
            }
        }
    }

    override suspend fun deletePastCrossMultipliers() {
        pastCrossMultipliers = emptyList()
        coroutineScope {
            launch(Dispatchers.IO) {
                pastCrossMultipliersDAO.deleteFromPastCrossMultipliers()
            }
        }
    }
}