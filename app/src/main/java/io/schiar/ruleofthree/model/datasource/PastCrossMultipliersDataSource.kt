package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.database.PastCrossMultipliersDAO
import io.schiar.ruleofthree.model.datasource.util.PastCrossMultipliersLocalDAO
import io.schiar.ruleofthree.model.datasource.util.toCrossMultiplier
import io.schiar.ruleofthree.model.datasource.util.toCrossMultiplierEntity
import io.schiar.ruleofthree.model.datasource.util.toCrossMultipliers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PastCrossMultipliersDataSource(
    private val pastCrossMultipliersDAO: PastCrossMultipliersDAO = PastCrossMultipliersLocalDAO(),
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
): PastCrossMultipliersDataSourceable {
    private var pastCrossMultipliers: List<CrossMultiplier>? = null

    constructor(
        crossMultipliers: List<CrossMultiplier>,
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) : this(
        pastCrossMultipliersDAO = PastCrossMultipliersLocalDAO(
            pastCrossMultipliers = crossMultipliers.map { crossMultiplier ->
                crossMultiplier.toCrossMultiplierEntity()
            }.reversed()
        ),
        coroutineDispatcher = coroutineDispatcher
    )

    override suspend fun createPastCrossMultiplier(crossMultiplierToBeCreated: CrossMultiplier) {
        val mutablePastCrossMultipliers = retrievePastCrossMultipliers().toMutableList()
        val crossMultiplierInserted = withContext(coroutineDispatcher) {
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
            val pastCrossMultipliersFromDataBase = withContext(coroutineDispatcher) {
                pastCrossMultipliersDAO.selectFromPastCrossMultiplierOrderByCreatedAtDesc()
            }.toCrossMultipliers()

            if (pastCrossMultipliersFromDataBase.isNotEmpty()) {
                pastCrossMultipliers = pastCrossMultipliersFromDataBase
            }
        }
        return pastCrossMultipliers ?: emptyList()
    }

    override suspend fun updatePastCrossMultiplier(crossMultiplierUpdated: CrossMultiplier) {
        val mutablePastCrossMultipliers = retrievePastCrossMultipliers().toMutableList()
        for (i in mutablePastCrossMultipliers.indices) {
            if (mutablePastCrossMultipliers[i].id == crossMultiplierUpdated.id) {
                mutablePastCrossMultipliers[i] = crossMultiplierUpdated
                break
            }
        }
        pastCrossMultipliers = mutablePastCrossMultipliers
        coroutineScope {
            launch(coroutineDispatcher) {
                pastCrossMultipliersDAO.updateCrossMultiplierEntityTimestamped(
                    crossMultiplierEntityUpdated = crossMultiplierUpdated.toCrossMultiplierEntity()
                )
            }
        }
    }

    override suspend fun deletePastCrossMultiplierAt(index: Int) {
        val mutablePastCrossMultipliers = retrievePastCrossMultipliers().toMutableList()
        val crossMultiplierToBeDeleted = mutablePastCrossMultipliers.removeAt(index = index)
        pastCrossMultipliers = mutablePastCrossMultipliers
        coroutineScope {
            launch(coroutineDispatcher) {
                pastCrossMultipliersDAO.delete(
                    crossMultiplierEntity = crossMultiplierToBeDeleted.toCrossMultiplierEntity()
                )
            }
        }
    }

    override suspend fun deletePastCrossMultipliers() {
        pastCrossMultipliers = emptyList()
        coroutineScope {
            launch(coroutineDispatcher) {
                pastCrossMultipliersDAO.deleteFromPastCrossMultipliers()
            }
        }
    }
}