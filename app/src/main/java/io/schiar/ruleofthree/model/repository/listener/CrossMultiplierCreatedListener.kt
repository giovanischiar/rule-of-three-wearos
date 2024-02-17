package io.schiar.ruleofthree.model.repository.listener

import io.schiar.ruleofthree.model.CrossMultiplier

interface CrossMultiplierCreatedListener {
    fun crossMultiplierCreated(crossMultiplier: CrossMultiplier)
}