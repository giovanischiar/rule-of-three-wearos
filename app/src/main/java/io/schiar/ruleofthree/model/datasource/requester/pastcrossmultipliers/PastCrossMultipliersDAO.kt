package io.schiar.ruleofthree.model.datasource.requester.pastcrossmultipliers

import io.schiar.ruleofthree.model.CrossMultiplier

interface PastCrossMultipliersDAO {
    fun create(crossMultiplier: CrossMultiplier): CrossMultiplier
    fun requestPastCrossMultipliers(): List<CrossMultiplier>
    fun updateCrossMultiplierTo(crossMultiplierUpdated: CrossMultiplier)
    fun delete(crossMultiplier: CrossMultiplier)
    fun deleteAll()
}