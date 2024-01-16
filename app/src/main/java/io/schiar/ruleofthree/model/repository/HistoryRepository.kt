package io.schiar.ruleofthree.model.repository

import io.schiar.ruleofthree.model.Numbers

interface HistoryRepository {
    fun requestAllPastNumbers(): List<Numbers>
    fun subscribeForAllPastNumbers(callback: (allPastNumbers: List<Numbers>) -> Unit)
}