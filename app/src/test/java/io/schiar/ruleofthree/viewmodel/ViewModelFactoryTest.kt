package io.schiar.ruleofthree.viewmodel

import androidx.lifecycle.ViewModel
import io.schiar.ruleofthree.model.repository.CrossMultipliersCreatorRepository
import io.schiar.ruleofthree.model.repository.MainRepository
import io.schiar.ruleofthree.viewmodel.util.ViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThrows
import org.junit.Rule
import org.junit.Test

class ViewModelFactoryTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `Initialize AppViewModel`() {
        val mainRepository = MainRepository()
        val crossMultipliersCreatorRepository = CrossMultipliersCreatorRepository()
        val viewModelFactory = ViewModelFactory(
            mainRepository = mainRepository,
            crossMultipliersCreatorRepository = crossMultipliersCreatorRepository
        )

        assertNotNull(viewModelFactory.create(AppViewModel::class.java))
    }

    @Test
    fun `Initialize CrossMultipliersCreatorViewModel`() {
        val mainRepository = MainRepository()
        val crossMultipliersCreatorRepository = CrossMultipliersCreatorRepository()
        val viewModelFactory = ViewModelFactory(
            mainRepository = mainRepository,
            crossMultipliersCreatorRepository = crossMultipliersCreatorRepository
        )

        assertNotNull(viewModelFactory.create(CrossMultipliersCreatorViewModel::class.java))
    }

    @Test
    fun `Initialize HistoryViewModel`() {
        val mainRepository = MainRepository()
        val crossMultipliersCreatorRepository = CrossMultipliersCreatorRepository()
        val viewModelFactory = ViewModelFactory(
            mainRepository = mainRepository,
            crossMultipliersCreatorRepository = crossMultipliersCreatorRepository
        )
        assertNotNull(viewModelFactory.create(HistoryViewModel::class.java))
    }

    @Test
    fun `Initialize a Unknown ViewModel`() {
        // Given
        val viewModelFactory = ViewModelFactory(
            mainRepository = MainRepository(),
            crossMultipliersCreatorRepository = CrossMultipliersCreatorRepository()
        )
        val unknownViewModel = object : ViewModel() {}

        // When
        val exception = assertThrows(IllegalArgumentException::class.java) {
            viewModelFactory.create(unknownViewModel::class.java)
        }
        val expectedMessage = "Unknown view model class: ${unknownViewModel::class.java.name}"
        val actualMessage = exception.message

        // Then
        assertEquals(expectedMessage, actualMessage)
    }
}