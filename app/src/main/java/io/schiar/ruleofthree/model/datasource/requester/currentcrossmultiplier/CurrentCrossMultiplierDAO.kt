package io.schiar.ruleofthree.model.datasource.requester.currentcrossmultiplier

import io.schiar.ruleofthree.model.CrossMultiplier

interface CurrentCrossMultiplierDAO {
    fun create(crossMultiplier: CrossMultiplier)
    fun requestCurrentCrossMultiplier(): CrossMultiplier?
    fun updateCurrentCrossMultiplierTo(crossMultiplierUpdated: CrossMultiplier)
}