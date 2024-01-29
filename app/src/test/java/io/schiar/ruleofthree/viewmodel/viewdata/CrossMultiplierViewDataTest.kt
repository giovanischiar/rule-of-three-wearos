package io.schiar.ruleofthree.viewmodel.viewdata

import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test

class CrossMultiplierViewDataTest {
    @Test
    fun `Get Empty Values`() {
        // Given
        val crossMultiplierViewData = CrossMultiplierViewData(
            valueAt00 = "", valueAt01 = "",
            valueAt10 = "", valueAt11 = ""
        )
        val expectedValuesMatrix = arrayOf(
            arrayOf("", ""),
            arrayOf("", "")
        )

        // When
        val actualValuesMatrix = crossMultiplierViewData.values

        // Then
        assertArrayEquals(expectedValuesMatrix, actualValuesMatrix)
    }

    @Test
    fun `Get Values`() {
        // Given
        val crossMultiplierViewData = CrossMultiplierViewData(
            valueAt00 = "12.4", valueAt01 = "56.4",
            valueAt10 = "34",   valueAt11 = "94"
        )
        val expectedValuesMatrix = arrayOf(
            arrayOf("12.4", "56.4"),
            arrayOf("34", "94")
        )

        // When
        val actualValuesMatrix = crossMultiplierViewData.values

        // Then
        assertArrayEquals(expectedValuesMatrix, actualValuesMatrix)
    }

    @Test
    fun `Get Empty Result View Data`() {
        // Given
        val crossMultiplierViewData = CrossMultiplierViewData(
            valueAt00 = "",  valueAt01 = "56.4",
            valueAt10 = "34", valueAt11 = "94",
            unknownPosition = Pair(0, 0)
        )
        val expectedResult = ResultViewData(
            result = "?",
            _result = 0.0
        )

        // When
        val actualResult = crossMultiplierViewData.result

        // Then
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `Get Result View Data`() {
        // Given
        val crossMultiplierViewData = CrossMultiplierViewData(
            valueAt00 = "${(34*56.4)/94}", valueAt01 = "56.4",
            valueAt10 = "34",              valueAt11 = "94",
            unknownPosition = Pair(0, 0)
        )
        val expectedResult = ResultViewData(
            result = "20,4",
            _result = (34*56.4)/94
        )

        // When
        val actualResult = crossMultiplierViewData.result

        // Then
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `Check if is Not Empty`() {
        // Given
        val crossMultiplierViewData = CrossMultiplierViewData(
            valueAt00 = "${(34*56.4)/94}", valueAt01 = "56.4",
            valueAt10 = "34",              valueAt11 = "94",
            unknownPosition = Pair(0, 0)
        )
        val expectedIsNotEmpty = true

        // When
        val actualIsNotEmpty = crossMultiplierViewData.isNotEmpty()

        // Then
        assertEquals(expectedIsNotEmpty, actualIsNotEmpty)
    }

    @Test
    fun `Check if is Empty With Cross Multiplier Cleared and With Result`() {
        // Given
        val crossMultiplierViewData = CrossMultiplierViewData(
            valueAt00 = "${(34*56.4)/94}", valueAt01 = "",
            valueAt10 = "",                valueAt11 = "",
            unknownPosition = Pair(0, 0)
        )
        val expectedIsNotEmpty = false

        // When
        val actualIsNotEmpty = crossMultiplierViewData.isNotEmpty()

        // Then
        assertEquals(expectedIsNotEmpty, actualIsNotEmpty)
    }

    @Test
    fun `Check if is Empty With Cross Multiplier Cleared and Without Result`() {
        // Given
        val crossMultiplierViewData = CrossMultiplierViewData(
            valueAt00 = "", valueAt01 = "",
            valueAt10 = "", valueAt11 = "",
            unknownPosition = Pair(0, 0)
        )
        val expectedIsNotEmpty = false

        // When
        val actualIsNotEmpty = crossMultiplierViewData.isNotEmpty()

        // Then
        assertEquals(expectedIsNotEmpty, actualIsNotEmpty)
    }

    @Test
    fun `Check if is Empty With Cross Multiplier Cleared and Value 3 at (1, 0)`() {
        // Given
        val crossMultiplierViewData = CrossMultiplierViewData(
            valueAt00 = "", valueAt01 = "3",
            valueAt10 = "", valueAt11 = "",
            unknownPosition = Pair(0, 0)
        )
        val expectedIsNotEmpty = true

        // When
        val actualIsNotEmpty = crossMultiplierViewData.isNotEmpty()

        // Then
        assertEquals(expectedIsNotEmpty, actualIsNotEmpty)
    }

    @Test
    fun `Check if is Empty With Cross Multiplier Cleared and Value 3 at (0, 1)`() {
        // Given
        val crossMultiplierViewData = CrossMultiplierViewData(
            valueAt00 = "", valueAt01 = "3",
            valueAt10 = "", valueAt11 = "",
            unknownPosition = Pair(0, 0)
        )
        val expectedIsNotEmpty = true

        // When
        val actualIsNotEmpty = crossMultiplierViewData.isNotEmpty()

        // Then
        assertEquals(expectedIsNotEmpty, actualIsNotEmpty)
    }

    @Test
    fun `Check if is Empty With Cross Multiplier Cleared and Value 7 at (1, 0)`() {
        // Given
        val crossMultiplierViewData = CrossMultiplierViewData(
            valueAt00 = "",  valueAt01 = "",
            valueAt10 = "7", valueAt11 = "",
            unknownPosition = Pair(0, 0)
        )
        val expectedIsNotEmpty = true

        // When
        val actualIsNotEmpty = crossMultiplierViewData.isNotEmpty()

        // Then
        assertEquals(expectedIsNotEmpty, actualIsNotEmpty)
    }

    @Test
    fun `Check if is Empty With Cross Multiplier Cleared and Value 9 at (1, 1)`() {
        // Given
        val crossMultiplierViewData = CrossMultiplierViewData(
            valueAt00 = "", valueAt01 = "",
            valueAt10 = "", valueAt11 = "9",
            unknownPosition = Pair(0, 0)
        )
        val expectedIsNotEmpty = true

        // When
        val actualIsNotEmpty = crossMultiplierViewData.isNotEmpty()

        // Then
        assertEquals(expectedIsNotEmpty, actualIsNotEmpty)
    }
}