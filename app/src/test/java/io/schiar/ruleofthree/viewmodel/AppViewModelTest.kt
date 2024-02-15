package io.schiar.ruleofthree.viewmodel

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSource
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSource
import io.schiar.ruleofthree.model.repository.AppRepository
import io.schiar.ruleofthree.model.repository.HistoryRepository
import io.schiar.ruleofthree.viewmodel.util.toViewData
import io.schiar.ruleofthree.viewmodel.viewdata.CrossMultiplierViewData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class AppViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Add Current Cross Multiplier to Past Cross Multipliers`() = runTest {
        // Given
        val currentCurrentCrossMultiplier = CrossMultiplier(
            valueAt00 = "23.48", valueAt01 = "9",
            valueAt10 = "4.3",   valueAt11 = "${(4.3*9)/23.48}"
        )
        val currentCurrentCrossMultiplierViewData = currentCurrentCrossMultiplier.toViewData()
        val expectedPastCrossMultipliers = listOf(currentCurrentCrossMultiplierViewData)
        val dispatcher = UnconfinedTestDispatcher(testScheduler)
        val currentCrossMultiplierDataSource = CurrentCrossMultiplierDataSource(
            currentCrossMultiplier = currentCurrentCrossMultiplier,
            coroutineDispatcher = dispatcher
        )
        val pastCrossMultipliersDataSource = PastCrossMultipliersDataSource(
            coroutineDispatcher = dispatcher
        )
        val historyRepository = HistoryRepository(
            pastCrossMultipliersDataSource = pastCrossMultipliersDataSource
        )
        val appRepository = AppRepository(
            currentCrossMultiplierDataSource = currentCrossMultiplierDataSource,
            pastCrossMultipliersDataSource = pastCrossMultipliersDataSource,
            pastCrossMultipliersListener = historyRepository
        )
        val appViewModel = AppViewModel(appRepository = appRepository)
        val historyViewModel = HistoryViewModel(historyRepository = historyRepository)
        val pastCrossMultipliersStateFlowEvents = mutableListOf<List<CrossMultiplierViewData>>()
        historyViewModel.pastCrossMultipliers
            .onEach { pastCrossMultipliersStateFlowEvents.add(it) }
            .launchIn(CoroutineScope(dispatcher))

        // When
        appViewModel.addCurrentCrossMultiplierToPastCrossMultipliers()
        advanceUntilIdle()

        // Then
        val actualPastCrossMultipliers = pastCrossMultipliersStateFlowEvents.last()
        assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }
}