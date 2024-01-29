package io.schiar.ruleofthree.viewmodel.viewdata

import org.junit.Assert.assertEquals
import org.junit.Test

class ResultViewDataTest {
    @Test
    fun `Longer Result With Default Decimals`() {
        // Given
        val result = ResultViewData(
            result = "${1/7.0}",
            _result = 1/7.0
        )
        val expectedLongerResult = "0,14"

        // When
        val actualLongerResult = result.longerResult()

        // Then
        assertEquals(expectedLongerResult, actualLongerResult)
    }

    @Test
    fun `Longer Result With Decimals 3`() {
        // Given
        val result = ResultViewData(
            result = "${1/7.0}",
            _result = 1/7.0
        )
        val expectedLongerResult = "0,143"

        // When
        val actualLongerResult = result.longerResult(decimals = 3)

        // Then
        assertEquals(expectedLongerResult, actualLongerResult)
    }

    @Test
    fun `Longer Result With Decimals 4`() {
        // Given
        val result = ResultViewData(
            result = "${1/7.0}",
            _result = 1/7.0
        )
        val expectedLongerResult = "0,1429"

        // When
        val actualLongerResult = result.longerResult(decimals = 4)

        // Then
        assertEquals(expectedLongerResult, actualLongerResult)
    }

    @Test
    fun `Longer Result With Decimals 10`() {
        // Given
        val result = ResultViewData(
            result = "${1/7.0}",
            _result = 1/7.0
        )
        val expectedLongerResult = "0,1428571429"

        // When
        val actualLongerResult = result.longerResult(decimals = 10)

        // Then
        assertEquals(expectedLongerResult, actualLongerResult)
    }

    @Test
    fun `Longer Result With More Decimals That is Needed to represent`() {
        // Given
        val result = ResultViewData(
            result = "${1/4.0}",
            _result = 1/4.0
        )
        val expectedLongerResult = "0,25"

        // When
        val actualLongerResult = result.longerResult(decimals = 11)

        // Then
        assertEquals(expectedLongerResult, actualLongerResult)
    }
}