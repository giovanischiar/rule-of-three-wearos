package io.schiar.ruleofthree.model.repository.listener

import io.schiar.ruleofthree.model.CrossMultiplier

interface PastCrossMultipliersListener {
    fun onPastCrossMultipliersChangedTo(newPastCrossMultipliers: List<CrossMultiplier>)
}