package io.schiar.ruleofthree.model.repository

interface AppRepository {
    suspend fun loadPastCrossMultipliers()
    fun subscribeForIsTherePastCrossMultipliers(callback: (value: Boolean) -> Unit)
    suspend fun addCurrentCrossMultiplierToPastCrossMultipliers()
}