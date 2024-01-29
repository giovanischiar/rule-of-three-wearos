package io.schiar.ruleofthree.model.repository

interface AppRepository {
    suspend fun loadPastCrossMultipliers()
    fun subscribeForAreTherePastCrossMultipliers(callback: (value: Boolean) -> Unit)
    suspend fun addCurrentCrossMultiplierToPastCrossMultipliers()
}