package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.service.CurrentCrossMultiplierService
import io.schiar.ruleofthree.model.datasource.service.local.CurrentCrossMultiplierLocalService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrentCrossMultiplierDataSource(
    private val currentCrossMultiplierDAO: CurrentCrossMultiplierService
        = CurrentCrossMultiplierLocalService(),
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private var currentCrossMultiplier: CrossMultiplier? = null

    constructor(
        currentCrossMultiplier: CrossMultiplier,
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) : this(
        currentCrossMultiplierDAO = CurrentCrossMultiplierLocalService(
            currentCrossMultiplierToInsert = currentCrossMultiplier
        ),
        coroutineDispatcher = coroutineDispatcher
    )

    suspend fun retrieveCurrentCrossMultiplier(): CrossMultiplier {
        if (currentCrossMultiplier == null) {
            currentCrossMultiplier = withContext(coroutineDispatcher) {
                currentCrossMultiplierDAO.retrieve()
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
                currentCrossMultiplierDAO.update(
                    crossMultiplier = crossMultiplierUpdated
                )
            }
        }
    }
}