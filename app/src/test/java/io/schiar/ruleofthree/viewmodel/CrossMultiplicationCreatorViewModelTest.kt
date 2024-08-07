package io.schiar.ruleofthree.viewmodel

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.local.CurrentCrossMultiplierLocalDataSource
import io.schiar.ruleofthree.model.datasource.local.PastCrossMultipliersLocalDataSource
import io.schiar.ruleofthree.model.repository.CrossMultipliersCreatorRepository
import io.schiar.ruleofthree.model.repository.HistoryRepository
import io.schiar.ruleofthree.viewmodel.util.toViewData
import io.schiar.ruleofthree.viewmodel.viewdata.CrossMultiplierViewData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
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

class CrossMultiplicationCreatorViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var dispatcher: TestDispatcher
    private lateinit var crossMultipliersCreatorViewModel: CrossMultipliersCreatorViewModel
    private val currentCrossMultiplier = CrossMultiplier(
                          valueAt01 = 1.3,
        valueAt10 = 34.2, valueAt11 = 45,
        unknownPosition = Pair(0, 0)
    )
    private val crossMultipliersEvents = mutableListOf<CrossMultiplierViewData>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() = runTest {
        dispatcher = UnconfinedTestDispatcher(testScheduler)
        val currentCrossMultiplierDataSource = CurrentCrossMultiplierLocalDataSource(
            currentCrossMultiplierToInsert = currentCrossMultiplier,
        )
        val crossMultipliersCreatorRepository = CrossMultipliersCreatorRepository(
            currentCrossMultiplierDataSource = currentCrossMultiplierDataSource,
            pastCrossMultipliersDataSource = PastCrossMultipliersLocalDataSource()
        )
        crossMultipliersCreatorViewModel = CrossMultipliersCreatorViewModel(
            crossMultipliersCreatorRepository = crossMultipliersCreatorRepository
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Push Character 3 to Input At position (1, 0)`() = runTest {
        // Given
        val expectedCrossMultiplier = CrossMultiplierViewData(
            valueAt00 = "${(34.23*1.3)/45}", valueAt01 = "1.3",
            valueAt10 = "34.23",             valueAt11 = "45",
            unknownPosition = Pair(0, 0)
        )

        // When
        crossMultipliersCreatorViewModel.pushCharacterToInputAt(
            position = Pair(1, 0), character = "3"
        )
        crossMultipliersCreatorViewModel.currentCrossMultiplierUiStateFlow
            .onEach { crossMultipliersEvents.add(it.crossMultiplier) }
            .launchIn(CoroutineScope(dispatcher))
        advanceUntilIdle()

        // Then
        val actualCrossMultiplier = crossMultipliersEvents.last()
        Assert.assertEquals(expectedCrossMultiplier, actualCrossMultiplier)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Pop Character Of Input At position (1, 1)`() = runTest {
        // Given
        val expectedCrossMultiplier = CrossMultiplierViewData(
            valueAt00 = "${(34.2*1.3)/4}", valueAt01 = "1.3",
            valueAt10 = "34.2",            valueAt11 = "4",
            unknownPosition = Pair(0, 0)
        )

        // When
        crossMultipliersCreatorViewModel.currentCrossMultiplierUiStateFlow
            .onEach { crossMultipliersEvents.add(it.crossMultiplier) }
            .launchIn(CoroutineScope(dispatcher))
        crossMultipliersCreatorViewModel.popCharacterOfInputAt(position = Pair(1, 1))
        advanceUntilIdle()

        // Then
        val actualCrossMultiplier = crossMultipliersEvents.last()
        Assert.assertEquals(expectedCrossMultiplier, actualCrossMultiplier)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Change The Unknown Position To position (0, 1)`() = runTest {
        // Given
        val expectedCrossMultiplier = CrossMultiplierViewData(
            valueAt00 = "",      valueAt01 = "",
            valueAt10 = "34.2", valueAt11 = "45",
            unknownPosition = Pair(0, 1)
        )

        // When
        crossMultipliersCreatorViewModel.currentCrossMultiplierUiStateFlow
            .onEach { crossMultipliersEvents.add(it.crossMultiplier) }
            .launchIn(CoroutineScope(dispatcher))
        crossMultipliersCreatorViewModel.changeTheUnknownPositionTo(position = Pair(0, 1))
        advanceUntilIdle()

        // Then
        val actualCrossMultiplier = crossMultipliersEvents.last()
        Assert.assertEquals(expectedCrossMultiplier, actualCrossMultiplier)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Clear Input on Position (1, 0)`() = runTest {
        // Given
        val expectedCrossMultiplier = CrossMultiplierViewData(
            valueAt00 = "", valueAt01 = "1.3",
            valueAt10 = "", valueAt11 = "45",
            unknownPosition = Pair(0, 0)
        )

        // When
        crossMultipliersCreatorViewModel.currentCrossMultiplierUiStateFlow
            .onEach { crossMultipliersEvents.add(it.crossMultiplier) }
            .launchIn(CoroutineScope(dispatcher))
        crossMultipliersCreatorViewModel.clearInputOn(position = Pair(1, 0))
        advanceUntilIdle()

        // Then
        val actualCrossMultiplier = crossMultipliersEvents.last()
        Assert.assertEquals(expectedCrossMultiplier, actualCrossMultiplier)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Clear All Inputs`() = runTest {
        // Given
        val expectedCrossMultiplier = CrossMultiplierViewData(
            valueAt00 = "", valueAt01 = "",
            valueAt10 = "", valueAt11 = "",
            unknownPosition = Pair(0, 0)
        )

        // When
        crossMultipliersCreatorViewModel.currentCrossMultiplierUiStateFlow
            .onEach { crossMultipliersEvents.add(it.crossMultiplier) }
            .launchIn(CoroutineScope(dispatcher))
        crossMultipliersCreatorViewModel.clearAllInputs()
        advanceUntilIdle()

        // Then
        val actualCrossMultiplier = crossMultipliersEvents.last()
        Assert.assertEquals(expectedCrossMultiplier, actualCrossMultiplier)
    }

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

        val crossMultipliersCreatorRepository = CrossMultipliersCreatorRepository(
            currentCrossMultiplierDataSource = currentCrossMultiplierDataSource,
            pastCrossMultipliersDataSource = pastCrossMultipliersLocalDataSource
        )
        val crossMultipliersCreatorViewModel = CrossMultipliersCreatorViewModel(
            crossMultipliersCreatorRepository = crossMultipliersCreatorRepository
        )
        val historyViewModel = HistoryViewModel(historyRepository = historyRepository)

        // When
        crossMultipliersCreatorViewModel.addToPastCrossMultipliers()

        // Then
        val actualPastCrossMultipliers = historyViewModel
            .pastCrossMultipliersUiStateFlow
            .drop(count = 1)
            .first()
            .crossMultipliers
        Assert.assertEquals(expectedPastCrossMultipliers, actualPastCrossMultipliers)
    }
}