package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.requester.currentcrossmultiplier.CurrentCrossMultiplierDAO
import io.schiar.ruleofthree.model.datasource.requester.currentcrossmultiplier.CurrentCrossMultiplierMemoryDAO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrentCrossMultiplierDataSource(
    private val currentCrossMultiplierDAO: CurrentCrossMultiplierDAO
        = CurrentCrossMultiplierMemoryDAO(),
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private var currentCrossMultiplier: CrossMultiplier? = null

    constructor(
        currentCrossMultiplier: CrossMultiplier,
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) : this(
        currentCrossMultiplierDAO = CurrentCrossMultiplierMemoryDAO(
            currentCrossMultiplierToInsert = currentCrossMultiplier
        ),
        coroutineDispatcher = coroutineDispatcher
    )

    suspend fun retrieveCurrentCrossMultiplier(): CrossMultiplier {
        if (currentCrossMultiplier == null) {
            currentCrossMultiplier = withContext(coroutineDispatcher) {
                currentCrossMultiplierDAO.requestCurrentCrossMultiplier()
            }

            if (currentCrossMultiplier == null) {
                val newCurrentCrossMultiplier = CrossMultiplier()

                coroutineScope {
                    launch(coroutineDispatcher) {
                        currentCrossMultiplierDAO.create(
                            crossMultiplier = newCurrentCrossMultiplier
                        )
                    }
                }
            }
        }
        return currentCrossMultiplier ?: CrossMultiplier()
    }

    suspend fun updateCurrentCrossMultiplier(crossMultiplierUpdated: CrossMultiplier) {
        currentCrossMultiplier = crossMultiplierUpdated
        coroutineScope {
            launch(coroutineDispatcher) {
                currentCrossMultiplierDAO.updateCurrentCrossMultiplierTo(
                    crossMultiplierUpdated = crossMultiplierUpdated
                )
            }
        }
    }
}