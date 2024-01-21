package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CrossMultiplierDataSource
import io.schiar.ruleofthree.model.datasource.DataSource
import io.schiar.ruleofthree.model.datasource.util.CrossMultiplierLocalDAO

class MainRepository(
    private val dataSource: DataSource = CrossMultiplierDataSource(crossMultiplierDAO = CrossMultiplierLocalDAO())
): AppRepository, CrossMultiplierRepository, HistoryRepository {
    private var crossMultipliersCallback = { _: CrossMultiplier -> }
    private var isThereHistoryCallback = { _: Boolean -> }
    private var allPastCrossMultipliersCallback = { _: List<CrossMultiplier> -> }

    // AppRepository

    override suspend fun loadDatabase() {
        crossMultipliersCallback(dataSource.requestCurrentCrossMultiplier())
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

    override suspend fun addToInput(value: String, position: Pair<Int, Int>) {
        val crossMultiplier = dataSource
            .requestCurrentCrossMultiplier()
            .addToInput(value = value, position = position)
            .resultCalculated()
        dataSource.updateCurrentCrossMultiplier(crossMultiplier = crossMultiplier)
        crossMultipliersCallback(crossMultiplier)
    }

    override suspend fun removeFromInput(position: Pair<Int, Int>) {
        val crossMultiplier = dataSource
            .requestCurrentCrossMultiplier()
            .removeFromInput(position = position)
            .resultCalculated()
        dataSource.updateCurrentCrossMultiplier(crossMultiplier = crossMultiplier)
        crossMultipliersCallback(crossMultiplier)
    }

    override suspend fun clearInput(position: Pair<Int, Int>) {
        val crossMultiplier = dataSource
            .requestCurrentCrossMultiplier()
            .clear(position = position)
            .resultCalculated()
        dataSource.updateCurrentCrossMultiplier(crossMultiplier = crossMultiplier)
        crossMultipliersCallback(crossMultiplier)
    }

    override suspend fun changeUnknownPosition(position: Pair<Int, Int>) {
        val crossMultiplier = dataSource
            .requestCurrentCrossMultiplier()
            .unknownPositionChangedTo(newPosition = position)
            .resultCalculated()
        dataSource.updateCurrentCrossMultiplier(crossMultiplier = crossMultiplier)
        crossMultipliersCallback(crossMultiplier)
    }

    override suspend fun submitToHistory() {
        val crossMultiplier = dataSource.requestCurrentCrossMultiplier()
        if (crossMultiplier.result() != null) {
            dataSource.updateCurrentCrossMultiplier(crossMultiplier)
            dataSource.addToAllPastCrossMultipliers(crossMultiplier)
            val allPastCrossMultipliers = dataSource.requestAllPastCrossMultipliers()
            allPastCrossMultipliersCallback(allPastCrossMultipliers)
            isThereHistoryCallback(allPastCrossMultipliers.isNotEmpty())
        }
    }

    override suspend fun clearAllInputs() {
        val crossMultiplier = dataSource
            .requestCurrentCrossMultiplier()
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

    override suspend fun replaceCurrentCrossMultiplier(index: Int) {
        dataSource.replaceCurrentCrossMultiplier(index = index)
        crossMultipliersCallback(dataSource.requestCurrentCrossMultiplier())
    }

    override suspend fun deleteHistoryItem(index: Int) {
        dataSource.deleteHistoryItem(index = index)
        val allPastCurrentMultipliers = dataSource.requestAllPastCrossMultipliers()
        allPastCrossMultipliersCallback(allPastCurrentMultipliers)
        isThereHistoryCallback(allPastCurrentMultipliers.isNotEmpty())
    }

    override suspend fun deleteHistory() {
        dataSource.deleteHistory()
        val allPastCurrentMultipliers = emptyList<CrossMultiplier>()
        allPastCrossMultipliersCallback(allPastCurrentMultipliers)
        isThereHistoryCallback(allPastCurrentMultipliers.isNotEmpty())
    }

    override suspend fun addToInput(index: Int, value: String, position: Pair<Int, Int>) {
        val crossMultiplier = dataSource
            .requestAllPastCrossMultipliers()[index]
            .addToInput(value = value, position = position)
            .resultCalculated()
        dataSource.updateCrossMultiplier(index = index, crossMultiplier = crossMultiplier)
        val allPastCurrentMultipliers = dataSource.requestAllPastCrossMultipliers()
        allPastCrossMultipliersCallback(allPastCurrentMultipliers)
    }

    override suspend fun removeFromInput(index: Int, position: Pair<Int, Int>) {
        val crossMultiplier = dataSource
            .requestAllPastCrossMultipliers()[index]
            .removeFromInput(position = position)
            .resultCalculated()
        dataSource.updateCrossMultiplier(index = index, crossMultiplier = crossMultiplier)
        val allPastCurrentMultipliers = dataSource.requestAllPastCrossMultipliers()
        allPastCrossMultipliersCallback(allPastCurrentMultipliers)
    }

    override suspend fun clearInput(index: Int, position: Pair<Int, Int>) {
        val crossMultiplier = dataSource
            .requestAllPastCrossMultipliers()[index]
            .clear(position = position)
            .resultCalculated()
        dataSource.updateCrossMultiplier(index = index, crossMultiplier = crossMultiplier)
        val allPastCurrentMultipliers = dataSource.requestAllPastCrossMultipliers()
        allPastCrossMultipliersCallback(allPastCurrentMultipliers)
    }

    override suspend fun changeUnknownPosition(index: Int, position: Pair<Int, Int>) {
        val crossMultiplier = dataSource
            .requestAllPastCrossMultipliers()[index]
            .unknownPositionChangedTo(newPosition = position)
            .resultCalculated()
        dataSource.updateCrossMultiplier(index = index, crossMultiplier = crossMultiplier)
        val allPastCurrentMultipliers = dataSource.requestAllPastCrossMultipliers()
        allPastCrossMultipliersCallback(allPastCurrentMultipliers)
    }
}