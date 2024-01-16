package io.schiar.ruleofthree.model.datasource

import io.schiar.ruleofthree.model.Numbers
import io.schiar.ruleofthree.model.datasource.room.NumbersDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NumbersDBDataSource(private val numbersDAO: NumbersDAO): DataSource {
    private var allPastNumbers: List<Numbers>? = null
    private var currentNumbers: Numbers? = null

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
}