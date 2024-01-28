package io.schiar.ruleofthree.viewmodel

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSource
import io.schiar.ruleofthree.model.repository.HistoryRepository
import io.schiar.ruleofthree.model.repository.MainRepository
import io.schiar.ruleofthree.view.viewdata.CrossMultiplierViewData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HistoryViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var dispatcher: TestDispatcher
    private lateinit var historyViewModel: HistoryViewModel
    private val pastCrossMultipliers = listOf(
        CrossMultiplier(
            valueAt00 = 67,           valueAt01 = 3.65,
            valueAt10 = (67*45/3.65), valueAt11 = 45,
            unknownPosition = Pair(1, 0)
        ),
        CrossMultiplier(
            valueAt00 = (90.6*63.59)/12.57, valueAt01 = 63.59,
            valueAt10 = 90.6,               valueAt11 = 12.57,
            unknownPosition = Pair(0, 0)
        ),
        CrossMultiplier(
            valueAt00 = 16,  valueAt01 = (16*9)/8.3,
            valueAt10 = 8.3, valueAt11 = 9,
            unknownPosition = Pair(0, 1)
        ),
        CrossMultiplier(
            valueAt00 = 16, valueAt01 = 35.6,
            valueAt10 = 8.3, valueAt11 = (8.3*35.6)/16,
            unknownPosition = Pair(1, 1)
        )
    )
    private val pastCrossMultipliersEvents = mutableListOf<List<CrossMultiplierViewData>>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() = runTest {
        dispatcher = UnconfinedTestDispatcher(testScheduler)
        val pastCrossMultipliersDataSource = PastCrossMultipliersDataSource(
            crossMultipliers = pastCrossMultipliers,
            coroutineDispatcher = dispatcher
        )
        val historyRepository: HistoryRepository = MainRepository(
            pastCrossMultipliersDataSourceable = pastCrossMultipliersDataSource
        )
        historyViewModel = HistoryViewModel(
            historyRepository = historyRepository
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Create Past Cross Multipliers in the Constructor`() = runTest {
        // Given
        val expectedPastCrossMultipliers = listOf(
            CrossMultiplierViewData(
                valueAt00 = "${(6*45)/100.54}", valueAt01 = "6",
                valueAt10 = "45",               valueAt11 = "100.54",
                unknownPosition = Pair(0, 0)
            ),
            CrossMultiplierViewData(
                valueAt00 = "64.2",             valueAt01 = "92.5",
                valueAt10 = "${(64.2*2)/92.5}", valueAt11 = "2",
                unknownPosition = Pair(1, 0)
            )
        )
        val historyViewModel = HistoryViewModel(
            pastCrossMultipliers = expectedPastCrossMultipliers
        )

        historyViewModel.pastCrossMultipliers
            .onEach { pastCrossMultipliersEvents.add(it) }
            .launchIn(CoroutineScope(dispatcher))

        // When
        advanceUntilIdle()

        // Then
        val actualPastCrossMultipliers = pastCrossMultipliersEvents.last()
        Assert.assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }
}