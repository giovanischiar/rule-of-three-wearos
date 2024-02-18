package io.schiar.ruleofthree.viewmodel

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierLocalDataSource
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersLocalDataSource
import io.schiar.ruleofthree.model.repository.AppRepository
import io.schiar.ruleofthree.model.repository.HistoryRepository
import io.schiar.ruleofthree.viewmodel.util.toViewData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class AppViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `Add Current Cross Multiplier to Past Cross Multipliers`() = runTest {
        // Given
        val currentCurrentCrossMultiplier = CrossMultiplier(
            valueAt00 = "23.48", valueAt01 = "9",
            valueAt10 = "4.3",   valueAt11 = "${(4.3*9)/23.48}"
        )
        val currentCurrentCrossMultiplierViewData = currentCurrentCrossMultiplier.toViewData()
        val expectedPastCrossMultipliers = listOf(currentCurrentCrossMultiplierViewData)
        val currentCrossMultiplierDataSource = CurrentCrossMultiplierLocalDataSource(
            currentCrossMultiplierToInsert = currentCurrentCrossMultiplier,
        )
        val pastCrossMultipliersLocalDataSource = PastCrossMultipliersLocalDataSource()
        val historyRepository = HistoryRepository(
            pastCrossMultipliersDataSource = pastCrossMultipliersLocalDataSource
        )

        val appRepository = AppRepository(
            currentCrossMultiplierDataSource = currentCrossMultiplierDataSource,
            pastCrossMultipliersDataSource = pastCrossMultipliersLocalDataSource
        )
        val appViewModel = AppViewModel(appRepository = appRepository)
        val historyViewModel = HistoryViewModel(historyRepository = historyRepository)

        // When
        appViewModel.addCurrentCrossMultiplierToPastCrossMultipliers()

        // Then
        val actualPastCrossMultipliers = historyViewModel
            .pastCrossMultipliers
            .drop(count = 1)
            .first()
        assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }
}