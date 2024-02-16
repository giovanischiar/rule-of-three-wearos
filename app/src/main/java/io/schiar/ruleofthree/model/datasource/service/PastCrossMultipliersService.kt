package io.schiar.ruleofthree.model.datasource.service

import io.schiar.ruleofthree.model.CrossMultiplier

interface PastCrossMultipliersService {
    fun create(crossMultiplier: CrossMultiplier): CrossMultiplier
    fun retrieve(): List<CrossMultiplier>
    fun update(crossMultiplier: CrossMultiplier)
    fun delete(crossMultiplier: CrossMultiplier)
    fun deleteAll()
}