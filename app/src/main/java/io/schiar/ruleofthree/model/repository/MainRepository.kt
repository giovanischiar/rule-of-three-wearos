package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.DataSource
import io.schiar.ruleofthree.model.datasource.CrossMultiplierDataSource
import io.schiar.ruleofthree.model.datasource.util.CrossMultiplierLocalDAO

class MainRepository(
    private val dataSource: DataSource = CrossMultiplierDataSource(crossMultiplierDAO = CrossMultiplierLocalDAO())
): AppRepository, CrossMultiplierRepository, HistoryRepository {
    private var crossMultipliersCallback = { _: CrossMultiplier -> }
    private var isThereHistoryCallback = { _: Boolean -> }
    private var allPastCrossMultipliersCallback = { _: List<CrossMultiplier> -> }

    // AppRepository

    override suspend fun loadDatabase() {
        crossMultipliersCallback(dataSource.requestCurrentCrossMultipliers())
        val allPastCrossMultipliers = dataSource.requestAllPastCrossMultipliers()
        allPastCrossMultipliersCallback(allPastCrossMultipliers)
        isThereHistoryCallback(allPastCrossMultipliers.isNotEmpty())
    }

    // CrossMultiplierRepository

    override fun subscribeForCrossMultipliers(callback: (crossMultiplier: CrossMultiplier) -> Unit) {
        this.crossMultipliersCallback = callback
    }

    override fun subscribeForIsThereHistories(callback: (value: Boolean) -> Unit) {
        isThereHistoryCallback = callback
    }

    override suspend fun addToInput(value: String, position: Int) {
        val crossMultiplier = dataSource
            .requestCurrentCrossMultipliers()
            .addToInput(value = value, position = position)
            .resultCalculated()
        dataSource.updateCurrentCrossMultiplier(crossMultiplier = crossMultiplier)
        crossMultipliersCallback(crossMultiplier)
    }

    override suspend fun removeFromInput(position: Int) {
        val crossMultiplier = dataSource
            .requestCurrentCrossMultipliers()
            .removeFromInput(position = position)
            .resultCalculated()
        dataSource.updateCurrentCrossMultiplier(crossMultiplier = crossMultiplier)
        crossMultipliersCallback(crossMultiplier)
    }

    override suspend fun clearInput(position: Int) {
        val crossMultiplier = dataSource
            .requestCurrentCrossMultipliers()
            .clear(position = position)
            .resultCalculated()
        dataSource.updateCurrentCrossMultiplier(crossMultiplier = crossMultiplier)
        crossMultipliersCallback(crossMultiplier)
    }

    override suspend fun submitToHistory() {
        val crossMultiplier = dataSource.requestCurrentCrossMultipliers()
        if (crossMultiplier.result != null) {
            dataSource.updateCurrentCrossMultiplier(crossMultiplier)
            dataSource.addToAllPastCrossMultipliers(crossMultiplier)
            val allPastCrossMultipliers = dataSource.requestAllPastCrossMultipliers()
            allPastCrossMultipliersCallback(allPastCrossMultipliers)
            isThereHistoryCallback(allPastCrossMultipliers.isNotEmpty())
        }
    }

    override suspend fun clearAllInputs() {
        val crossMultiplier = dataSource
            .requestCurrentCrossMultipliers()
            .clearAll()
        dataSource.updateCurrentCrossMultiplier(crossMultiplier = crossMultiplier)
        crossMultipliersCallback(crossMultiplier)
    }

    // HistoryRepository

    override fun subscribeForAllPastCrossMultipliers(
        callback: (allPastCrossMultipliers: List<CrossMultiplier>) -> Unit
    ) {
        allPastCrossMultipliersCallback = callback
    }

    override suspend fun deleteHistoryItem(index: Int) {
        dataSource.deleteHistoryItem(index = index)
        val allPastCurrentMultipliers = dataSource.requestAllPastCrossMultipliers()
        allPastCrossMultipliersCallback(allPastCurrentMultipliers)
        isThereHistoryCallback(allPastCurrentMultipliers.isNotEmpty())
    }
}