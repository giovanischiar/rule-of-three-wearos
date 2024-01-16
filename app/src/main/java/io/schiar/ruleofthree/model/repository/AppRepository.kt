package io.schiar.ruleofthree.model.repository

interface AppRepository {
    suspend fun loadDatabase()
}