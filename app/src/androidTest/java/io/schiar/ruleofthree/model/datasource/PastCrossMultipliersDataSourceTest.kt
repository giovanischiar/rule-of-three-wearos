package io.schiar.ruleofthree.model.datasource

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.schiar.ruleofthree.library.room.RuleOfThreeDatabase
import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.requester.pastcrossmultipliers.PastCrossMultipliersPersistentDAO
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

class PastCrossMultipliersDataSourceTest {
    private lateinit var database: RuleOfThreeDatabase
    private lateinit var pastCrossMultipliersDataSource: PastCrossMultipliersDataSource
    private val crossMultipliers = listOf(
        CrossMultiplier(valueAt00 = 1, valueAt01 = 2.3, valueAt10 = 45.3),
        CrossMultiplier(valueAt00 = 3, valueAt01 = 32.3, valueAt10 = 4.6),
        CrossMultiplier(valueAt00 = 94.5, valueAt01 = 28.4, valueAt10 = 57),
        CrossMultiplier(valueAt00 = 98, valueAt01 = 23, valueAt10 = 4),
    )

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context = context,
            klass = RuleOfThreeDatabase::class.java
        ).build()
        pastCrossMultipliersDataSource = PastCrossMultipliersDataSource(
            pastCrossMultipliersDAO = PastCrossMultipliersPersistentDAO(
                pastCrossMultipliersRoomDAO = database.pastCrossMultipliersDAO()
            ).apply {
                for (crossMultiplier in crossMultipliers) {
                    create(crossMultiplier = crossMultiplier)
                }
            }
        )
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun `Create Cross Multipliers and Check its Reversed Order`() {
        // Given
        val expectedCrossMultipliers = crossMultipliers.reversed()
        val dataSource = PastCrossMultipliersDataSource()

        // When
        for (crossMultiplier in crossMultipliers) {
            runBlocking {
                dataSource.createPastCrossMultiplier(
                    crossMultiplierToBeCreated = crossMultiplier
                )
            }
        }
        val actualCrossMultipliers = runBlocking { dataSource.retrievePastCrossMultipliers()  }

        // Then
        assertEquals(expectedCrossMultipliers, actualCrossMultipliers)
    }

    @Test
    fun `Retrieve Past Cross Multipliers`() {
        // Given
        val expectedCrossMultipliers = crossMultipliers.reversed()

        // When
        val actualCrossMultipliers = runBlocking {
            pastCrossMultipliersDataSource.retrievePastCrossMultipliers()
        }

        // Then
        assertEquals(expectedCrossMultipliers, actualCrossMultipliers)
    }

    @Test
    fun `Retrieve Cross Multiplier at Index 2`() {
        // Given
        val indexToRetrieve = 2
        val expectedCrossMultipliers = crossMultipliers.reversed()[indexToRetrieve]

        // When
        val actualCrossMultipliers = runBlocking {
            pastCrossMultipliersDataSource.retrievePastCrossMultiplierAt(index = indexToRetrieve)
        }

        // Then
        assertEquals(expectedCrossMultipliers, actualCrossMultipliers)
    }

    @Test
    fun `Update Cross Multiplier at Index 2`() {
        // Given
        val indexToUpdate = 2
        val expectedCrossMultiplier = crossMultipliers.reversed()[indexToUpdate]

        // When
        runBlocking {
            pastCrossMultipliersDataSource.updatePastCrossMultiplier(expectedCrossMultiplier)
        }
        val actualCrossMultiplier = runBlocking {
            pastCrossMultipliersDataSource.retrievePastCrossMultiplierAt(index = indexToUpdate)
        }

        // Then
        assertEquals(expectedCrossMultiplier, actualCrossMultiplier)
    }

    @Test
    fun `Delete Cross Multiplier at Index 2`() {
        // Given
        val indexToDelete = 2
        val expectedCrossMultipliers = crossMultipliers.reversed().toMutableList()
        expectedCrossMultipliers.removeAt(index = indexToDelete)

        // When
        runBlocking {
            pastCrossMultipliersDataSource.deletePastCrossMultiplierAt(index = indexToDelete)
        }
        val actualCrossMultipliers = runBlocking {
            pastCrossMultipliersDataSource.retrievePastCrossMultipliers()
        }

        // Then
        assertEquals(expectedCrossMultipliers, actualCrossMultipliers)
    }

    @Test
    fun `Delete Past Cross Multipliers`() {
        // Given
        val expectedCrossMultipliers = emptyList<CrossMultiplier>()
        val pastCrossMultipliersDataSource = PastCrossMultipliersDataSource(
            crossMultipliers = crossMultipliers
        )

        // When
        runBlocking { pastCrossMultipliersDataSource.deletePastCrossMultipliers() }
        val actualCrossMultipliers = runBlocking {
            pastCrossMultipliersDataSource.retrievePastCrossMultipliers()
        }

        // Then
        assertEquals(expectedCrossMultipliers, actualCrossMultipliers)
    }
}