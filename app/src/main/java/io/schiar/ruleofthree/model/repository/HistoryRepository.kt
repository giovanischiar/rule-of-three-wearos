package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.Numbers

interface HistoryRepository {
    fun subscribeForAllPastNumbers(callback: (allPastNumbers: List<Numbers>) -> Unit)
    suspend fun deleteHistoryItem(index: Int)
}