package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.service.PastCrossMultipliersService
import io.schiar.ruleofthree.model.datasource.service.local.PastCrossMultipliersLocalService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PastCrossMultipliersDataSource(
    private val pastCrossMultipliersService: PastCrossMultipliersService
        = PastCrossMultipliersLocalService(),
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private var pastCrossMultipliers: List<CrossMultiplier>? = null

    constructor(
        crossMultipliers: List<CrossMultiplier>,
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) : this(
        pastCrossMultipliersService = PastCrossMultipliersLocalService(
            crossMultipliersToInsert = crossMultipliers
        ),
        coroutineDispatcher = coroutineDispatcher
    )

    suspend fun createPastCrossMultiplier(crossMultiplierToBeCreated: CrossMultiplier) {
        val mutablePastCrossMultipliers = retrievePastCrossMultipliers().toMutableList()
        val crossMultiplierInserted = withContext(coroutineDispatcher) {
            pastCrossMultipliersService.create(crossMultiplier = crossMultiplierToBeCreated)
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
                pastCrossMultipliersService.retrieve()
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
                pastCrossMultipliersService.update(
                    crossMultiplier = crossMultiplierUpdated
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
                pastCrossMultipliersService.delete(crossMultiplier = crossMultiplierToBeDeleted)
            }
        }
    }

    suspend fun deletePastCrossMultipliers() {
        pastCrossMultipliers = emptyList()
        coroutineScope {
            launch(coroutineDispatcher) { pastCrossMultipliersService.deleteAll() }
        }
    }
}