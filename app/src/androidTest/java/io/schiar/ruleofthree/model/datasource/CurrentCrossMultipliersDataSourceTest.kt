package io.schiar.ruleofthree.model.datasource

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.schiar.ruleofthree.library.room.RuleOfThreeRoomDatabase
import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.library.room.CurrentCrossMultiplierRoomService
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

class CurrentCrossMultipliersDataSourceTest {
    private lateinit var database: RuleOfThreeRoomDatabase
    private lateinit var currentCrossMultiplierDataSource: CurrentCrossMultiplierDataSource
    private val crossMultiplier = CrossMultiplier(valueAt00 = 3, valueAt01 = 4.3)

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context = context,
            klass = RuleOfThreeRoomDatabase::class.java
        ).build()
        currentCrossMultiplierDataSource = CurrentCrossMultiplierDataSource(
            currentCrossMultiplierDAO = CurrentCrossMultiplierRoomService(
                currentCrossMultiplierRoomDAO = database.currentCrossMultiplierDAO()
            ).apply {
                create(crossMultiplier)
            }
        )
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun `Retrieve Current Cross Multiplier`() = runBlocking {
        // Given
        val expectedCurrentCrossMultiplier = crossMultiplier

        //When
        val actualCurrentCrossMultiplier = currentCrossMultiplierDataSource
            .retrieveCurrentCrossMultiplier()

        // Then
        assertEquals(expectedCurrentCrossMultiplier, actualCurrentCrossMultiplier)
    }

    @Test
    fun `Update Current Cross Multiplier`() = runBlocking {
        // Given
        val expectedCurrentCrossMultiplier = CrossMultiplier(
            valueAt00 = 3,
            valueAt01 = 4.3,
            valueAt10 = 3.4
        )

        //When
        currentCrossMultiplierDataSource
            .updateCurrentCrossMultiplier(crossMultiplierUpdated = expectedCurrentCrossMultiplier)

        // Then
        val actualCurrentCrossMultiplier = currentCrossMultiplierDataSource
            .retrieveCurrentCrossMultiplier()
        assertEquals(expectedCurrentCrossMultiplier, actualCurrentCrossMultiplier)
    }
}