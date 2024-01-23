package io.schiar.ruleofthree.model

import org.junit.Assert
import org.junit.Test

class InputTest {
    private val emptyInput = Input()

    @Test
    fun `Invalid Character Pushed`() {
        // Given
        val expectedInput = emptyInput

        // When
        val actualInput = emptyInput
            .characterPushed(character = "a")
            .characterPushed(character = "b")

        // Then
        Assert.assertEquals(expectedInput, actualInput)
    }

    @Test
    fun `Trailing Zero Characters Pushed`() {
        // Given
        val expectedInput = Input(value = 1)

        // When
        val actualInput = emptyInput
            .characterPushed(character = "0")
            .characterPushed(character = "1")

        // Then
        Assert.assertEquals(expectedInput, actualInput)
    }

    @Test
    fun `Characters Less Than Zero With Zeroes After Decimal Point Pushed`() {
        // Given
        val expectedInput = Input(value = 0.001)

        // When
        val actualInput = emptyInput
            .characterPushed(character = "0")
            .characterPushed(character = ".")
            .characterPushed(character = "0")
            .characterPushed(character = "0")
            .characterPushed(character = "1")

        // Then
        Assert.assertEquals(expectedInput, actualInput)
    }

    @Test
    fun `Multiple Decimal Points Pushed`() {
        val expectedInput = Input(value = "1.23")

        val actualInput = emptyInput
            .characterPushed(character = "1")
            .characterPushed(character = ".")
            .characterPushed(character = "2")
            .characterPushed(character = ".")
            .characterPushed(character = "3")

        // Then
        Assert.assertEquals(expectedInput, actualInput)
    }

    @Test
    fun `Characters Pushed and Popped`() {
        // Given
        val expectedInput = Input(value = "1.23")

        // When
        val actualInput = emptyInput
            .characterPushed(character = "1")
            .characterPushed(character = ".")
            .characterPopped()
            .characterPushed(character = ".")
            .characterPushed(character = "2")
            .characterPushed(character = "3")
            .characterPushed(character = "5")
            .characterPushed(character = "6")
            .characterPushed(character = "7")
            .characterPopped()
            .characterPopped()
            .characterPopped()

        // Then
        Assert.assertEquals(expectedInput, actualInput)
    }

    @Test
    fun `Characters Popped When Input is Empty`() {
        // Given
        val expectedInput = emptyInput

        // When
        val actualInput = emptyInput.characterPopped().characterPopped()

        // Then
        Assert.assertEquals(expectedInput, actualInput)
    }

    @Test
    fun `Two Inputs Pushed and Then Cleared`() {
        // Given
        val expectedInput = emptyInput

        // When
        val actualInput = emptyInput
            .characterPushed("1")
            .characterPushed("2")
            .cleared()

        // Then
        Assert.assertEquals(expectedInput, actualInput)
    }

    @Test
    fun `Cleared When Input is Empty`() {
        // Given
        val expectedInput = emptyInput

        // When
        val actualInput = emptyInput.cleared()

        // Then
        Assert.assertEquals(expectedInput, actualInput)
    }

    @Test
    fun `Clear and Then Remove When Empty`() {
        // Given
        val expectedInput = emptyInput

        // When
        val actualInput = emptyInput.cleared().characterPopped()

        // Then
        Assert.assertEquals(expectedInput, actualInput)
    }

    @Test
    fun `Check Validity Of Empty Input`() {
        // When
        val actualResult = emptyInput.toDoubleOrNull()

        // Then
        Assert.assertNull(actualResult)
    }

    @Test
    fun `Double valid Value`() {
        // Given
        val input = Input(value = "1.3")
        val expectedDouble = 1.3

        // When
        val actualDouble = input.toDoubleOrNull()

        // Then
        Assert.assertEquals(expectedDouble, actualDouble)
    }

    @Test
    fun `To String`() {
        // Given
        val input = Input(value = "3.45")
        val expectedString = "3.45"

        // When
        val actualString = input.toString()

        // Then
        Assert.assertEquals(expectedString, actualString)
    }

    @Test
    fun `Multiplying Two Inputs`() {
        // Given
        val inputA = Input(value = "3.45")
        val inputB = Input(value = "74.38")
        val expectedInput = Input(value = 3.45 * 74.38)

        // When
        val actualInput = inputA * inputB

        // Then
        Assert.assertEquals(expectedInput, actualInput)
    }

    @Test
    fun `Dividing Two Inputs`() {
        // Given
        val inputA = Input(value = "57.4")
        val inputB = Input(value = "7.45")
        val expectedInput = Input(value = 57.4 / 7.45)

        // When
        val actualInput = inputA / inputB

        // Then
        Assert.assertEquals(expectedInput, actualInput)
    }

    @Test
    fun `Dividing Two Inputs With 0 Denominator`() {
        // Given
        val inputA = Input(value = "903.73")
        val inputB = Input(value = "0")
        val expectedInput = Input(value = "903.73")

        // When
        val actualInput = inputA / inputB

        // Then
        Assert.assertEquals(expectedInput, actualInput)
    }
}