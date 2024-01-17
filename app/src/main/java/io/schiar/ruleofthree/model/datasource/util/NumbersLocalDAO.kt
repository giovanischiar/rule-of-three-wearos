package io.schiar.ruleofthree.model.datasource.util

import io.schiar.ruleofthree.model.Numbers
import io.schiar.ruleofthree.model.datasource.room.NumbersDAO
import io.schiar.ruleofthree.model.datasource.room.NumbersEntity

class NumbersLocalDAO(
    private var currentNumbers: Numbers = Numbers(),
    private var allPastNumbers: List<Numbers> = emptyList()
): NumbersDAO {
    override fun insert(numbersEntity: NumbersEntity): Long { return 0L }
    override fun update(numbersEntity: NumbersEntity) {
        allPastNumbers = listOf(numbersEntity.toModel()) + allPastNumbers
    }

    override fun delete(numbersEntity: NumbersEntity) {
        val allPastNumbers = allPastNumbers.toMutableList()
        allPastNumbers.remove(numbersEntity.toModel())
    }

    override fun selectAllPastNumbers(): List<NumbersEntity> {
        return allPastNumbers.map { it.toEntity() }
    }
    override fun selectCurrentNumbers(): NumbersEntity { return currentNumbers.toEntity() }
    override fun selectHistoryItemID(a: String?, b: String?, c: String?, result: Double?): Long {
        return 1L
    }
}