package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.Numbers
import io.schiar.ruleofthree.model.datasource.room.NumbersDAO
import io.schiar.ruleofthree.model.datasource.util.NumbersLocalDAO
import io.schiar.ruleofthree.model.datasource.util.toEntity
import io.schiar.ruleofthree.model.datasource.util.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NumbersDataSource(private val numbersDAO: NumbersDAO): DataSource {
    private var allPastNumbers: List<Numbers>? = null
    private var currentNumbers: Numbers? = null

    constructor(
        allPastNumbers: List<Numbers> = emptyList(),
        currentNumbers: Numbers = Numbers()
    ) : this(
        numbersDAO = NumbersLocalDAO(
            currentNumbers = currentNumbers,
            allPastNumbers = allPastNumbers
        )
    )

    override suspend fun requestCurrentNumbers(): Numbers {
        if (currentNumbers == null) {
            currentNumbers = withContext(Dispatchers.IO) {
                (numbersDAO.selectCurrentNumbers())?.toModel()
            }

            if (currentNumbers == null) {
                val newNumbers = Numbers()
                coroutineScope {
                    launch(Dispatchers.IO) {
                        numbersDAO.insert(newNumbers.toEntity(id = 1))
                    }
                }
                currentNumbers = newNumbers
            }
        }
        return currentNumbers as Numbers
    }

    override suspend fun updateCurrentNumbers(numbers: Numbers) {
        currentNumbers = numbers
        coroutineScope {
            launch(Dispatchers.IO) {
                numbersDAO.update(numbers.toEntity(id = 1))
            }
        }
    }

    override suspend fun requestAllPastNumbers(): List<Numbers> {
        if (allPastNumbers == null) {
            allPastNumbers = withContext(Dispatchers.IO) {
                numbersDAO.selectAllPastNumbers().map {
                    it.toModel()
                }
            }
        }
        return allPastNumbers as List<Numbers>
    }

    override suspend fun addToAllPastNumbers(numbers: Numbers) {
        allPastNumbers = listOf(numbers) + (allPastNumbers ?: emptyList())
        coroutineScope {
            launch(Dispatchers.IO) {
                numbersDAO.insert(numbers.toEntity())
            }
        }
    }

    override suspend fun deleteHistoryItem(index: Int) {
        val allPastNumbers = requestAllPastNumbers().toMutableList()
        val numbers = allPastNumbers.removeAt(index)
        this.allPastNumbers = allPastNumbers
        val (_, a, b, c, result) = numbers.toEntity()
        coroutineScope {
            launch(Dispatchers.IO) {
                val entityIDToDelete = numbersDAO.selectHistoryItemID(a, b, c, result)
                numbersDAO.delete(
                    numbersEntity = numbers.toEntity(id = entityIDToDelete ?: return@launch)
                )
            }
        }
    }
}