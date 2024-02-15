package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.database.CurrentCrossMultiplierDAO
import io.schiar.ruleofthree.model.datasource.util.CurrentCrossMultiplierLocalDAO
import io.schiar.ruleofthree.model.datasource.util.toCrossMultiplier
import io.schiar.ruleofthree.model.datasource.util.toCurrentCrossMultiplierEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias LocalDAO = CurrentCrossMultiplierLocalDAO

class CurrentCrossMultiplierDataSource(
    private val currentCrossMultiplierDAO: CurrentCrossMultiplierDAO = LocalDAO(),
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private var currentCrossMultiplier: CrossMultiplier? = null

    constructor(
        currentCrossMultiplier: CrossMultiplier,
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) : this(
        currentCrossMultiplierDAO = LocalDAO(
            currentCrossMultiplier = currentCrossMultiplier.toCurrentCrossMultiplierEntity()
        ),

        coroutineDispatcher = coroutineDispatcher
    )

    suspend fun retrieveCurrentCrossMultiplier(): CrossMultiplier {
        if (currentCrossMultiplier == null) {
            currentCrossMultiplier = withContext(coroutineDispatcher) {
                currentCrossMultiplierDAO.select()?.toCrossMultiplier()
            }

            if (currentCrossMultiplier == null) {
                val newCurrentCrossMultiplier = CrossMultiplier()

                coroutineScope {
                    launch(coroutineDispatcher) {
                        currentCrossMultiplierDAO.insertWithTimestamp(
                            newCurrentCrossMultiplier.toCurrentCrossMultiplierEntity()
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
                currentCrossMultiplierDAO.updateWithTimestamp(
                    crossMultiplierUpdated.toCurrentCrossMultiplierEntity()
                )
            }
        }
    }
}