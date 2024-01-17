package io.schiar.ruleofthree.model.datasource.util

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.room.CrossMultiplierDAO
import io.schiar.ruleofthree.model.datasource.room.CrossMultiplierEntity

class CrossMultiplierLocalDAO(
    private var currentCrossMultiplier: CrossMultiplier = CrossMultiplier(),
    private var allPastCrossMultipliers: List<CrossMultiplier> = emptyList()
): CrossMultiplierDAO {
    override fun insert(crossMultiplierEntity: CrossMultiplierEntity): Long { return 0L }

    override fun update(crossMultiplierEntity: CrossMultiplierEntity) {
        allPastCrossMultipliers = listOf(crossMultiplierEntity.toModel()) + allPastCrossMultipliers
    }

    override fun selectAllPastCrossMultipliers(): List<CrossMultiplierEntity> {
        return allPastCrossMultipliers.map { it.toEntity() }
    }

    override fun selectCurrentCrossMultiplier(): CrossMultiplierEntity {
        return currentCrossMultiplier.toEntity()
    }

    override fun deleteHistoryItem(a: String?, b: String?, c: String?, result: Double?) {
        val allPastCrossMultipliers = allPastCrossMultipliers.toMutableList()
        val crossMultiplierEntity = CrossMultiplierEntity(a = a, b = b, c = c, result = result)
        allPastCrossMultipliers.remove(crossMultiplierEntity.toModel())
        this.allPastCrossMultipliers = allPastCrossMultipliers
    }

    override fun deleteHistory() { allPastCrossMultipliers = emptyList() }
}