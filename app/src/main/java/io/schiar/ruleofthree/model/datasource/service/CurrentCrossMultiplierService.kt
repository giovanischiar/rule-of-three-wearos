package io.schiar.ruleofthree.model.datasource.service

import io.schiar.ruleofthree.model.CrossMultiplier

interface CurrentCrossMultiplierService {
    fun create(crossMultiplier: CrossMultiplier)
    fun retrieve(): CrossMultiplier?
    fun update(crossMultiplier: CrossMultiplier)
}