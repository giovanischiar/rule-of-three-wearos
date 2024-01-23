package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.database.CurrentCrossMultiplierDAO
import io.schiar.ruleofthree.model.datasource.util.CurrentCrossMultiplierLocalDAO
import io.schiar.ruleofthree.model.datasource.util.toCrossMultiplier
import io.schiar.ruleofthree.model.datasource.util.toCurrentCrossMultiplierEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias LocalDAO = CurrentCrossMultiplierLocalDAO

class CurrentCrossMultiplierDataSource(
    private val currentCrossMultiplierDAO: CurrentCrossMultiplierDAO = LocalDAO()
): CurrentCrossMultiplierDataSourceable {
    private var currentCrossMultiplier: CrossMultiplier? = null

    constructor(currentCrossMultiplier: CrossMultiplier) : this(
        currentCrossMultiplierDAO = LocalDAO(
            currentCrossMultiplier = currentCrossMultiplier.toCurrentCrossMultiplierEntity()
        )
    )

    override suspend fun retrieveCurrentCrossMultiplier(): CrossMultiplier {
        if (currentCrossMultiplier == null) {
            currentCrossMultiplier = withContext(Dispatchers.IO) {
                currentCrossMultiplierDAO.select()?.toCrossMultiplier()
            }

            if (currentCrossMultiplier == null) {
                val newCurrentCrossMultiplier = CrossMultiplier()

                coroutineScope {
                    launch(Dispatchers.IO) {
                        currentCrossMultiplierDAO.insertWithTimestamp(
                            newCurrentCrossMultiplier.toCurrentCrossMultiplierEntity()
                        )
                    }
                }
            }
        }
        return currentCrossMultiplier ?: CrossMultiplier()
    }

    override suspend fun updateCurrentCrossMultiplier(crossMultiplierUpdated: CrossMultiplier) {
        currentCrossMultiplier = crossMultiplierUpdated
        coroutineScope {
            launch(Dispatchers.IO) {
                currentCrossMultiplierDAO.updateWithTimestamp(
                    crossMultiplierUpdated.toCurrentCrossMultiplierEntity()
                )
            }
        }
    }
}