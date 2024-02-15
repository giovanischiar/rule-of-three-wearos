package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.requester.pastcrossmultipliers.PastCrossMultipliersDAO
import io.schiar.ruleofthree.model.datasource.requester.pastcrossmultipliers.PastCrossMultipliersMemoryDAO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PastCrossMultipliersDataSource(
    private val pastCrossMultipliersDAO: PastCrossMultipliersDAO = PastCrossMultipliersMemoryDAO(),
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private var pastCrossMultipliers: List<CrossMultiplier>? = null

    constructor(
        crossMultipliers: List<CrossMultiplier>,
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) : this(
        pastCrossMultipliersDAO = PastCrossMultipliersMemoryDAO(
            crossMultipliersToInsert = crossMultipliers
        ),
        coroutineDispatcher = coroutineDispatcher
    )

    suspend fun createPastCrossMultiplier(crossMultiplierToBeCreated: CrossMultiplier) {
        val mutablePastCrossMultipliers = retrievePastCrossMultipliers().toMutableList()
        val crossMultiplierInserted = withContext(coroutineDispatcher) {
            pastCrossMultipliersDAO.create(crossMultiplier = crossMultiplierToBeCreated)
        }
        mutablePastCrossMultipliers.add(index = 0, crossMultiplierInserted)
        pastCrossMultipliers = mutablePastCrossMultipliers
    }

    suspend fun retrievePastCrossMultiplierAt(index: Int): CrossMultiplier? {
        return retrievePastCrossMultipliers().getOrNull(index = index)
    }

    suspend fun retrievePastCrossMultipliers(): List<CrossMultiplier> {
        if (pastCrossMultipliers == null) {
            val pastCrossMultipliersFromDataBase = withContext(coroutineDispatcher) {
                pastCrossMultipliersDAO.requestPastCrossMultipliers()
            }

            if (pastCrossMultipliersFromDataBase.isNotEmpty()) {
                pastCrossMultipliers = pastCrossMultipliersFromDataBase
            }
        }
        return pastCrossMultipliers ?: emptyList()
    }

    suspend fun updatePastCrossMultiplier(crossMultiplierUpdated: CrossMultiplier) {
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
                pastCrossMultipliersDAO.updateCrossMultiplierTo(
                    crossMultiplierUpdated = crossMultiplierUpdated
                )
            }
        }
    }

    suspend fun deletePastCrossMultiplierAt(index: Int) {
        val mutablePastCrossMultipliers = retrievePastCrossMultipliers().toMutableList()
        val crossMultiplierToBeDeleted = mutablePastCrossMultipliers.removeAt(index = index)
        pastCrossMultipliers = mutablePastCrossMultipliers
        coroutineScope {
            launch(coroutineDispatcher) {
                pastCrossMultipliersDAO.delete(crossMultiplier = crossMultiplierToBeDeleted)
            }
        }
    }

    suspend fun deletePastCrossMultipliers() {
        pastCrossMultipliers = emptyList()
        coroutineScope {
            launch(coroutineDispatcher) { pastCrossMultipliersDAO.deleteAll() }
        }
    }
}