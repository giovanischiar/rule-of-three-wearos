package io.schiar.ruleofthree.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CrossMultiplierTest {
    private val emptyCrossMultiplier = CrossMultiplier()

    @Test
    fun `Inputs 1 and 2 Pushed at Position (0, 0)`() {
        // Given
        val expectedCrossMultiplier = CrossMultiplier(valueAt00 = 12)
        val position = Pair(0, 0)

        // When
        val actualCrossMultiplier = CrossMultiplier()
            .characterPushedAt(position = position, character = "1")
            .characterPushedAt(position = position, character = "2")

        // Then
        assertEquals(expectedCrossMultiplier, actualCrossMultiplier)
    }

    @Test
    fun `Inputs 1 and 2 Pushed at Position (0, 1)`() {
        // Given
        val expectedCrossMultiplier = CrossMultiplier(valueAt01 = 12)
        val position = Pair(0, 1)

        // When
        val actualCrossMultiplier = CrossMultiplier()
            .characterPushedAt(position = position, character = "1")
            .characterPushedAt(position = position, character = "2")

        // Then
        assertEquals(expectedCrossMultiplier, actualCrossMultiplier)
    }

    @Test
    fun `Inputs 1 and 2 Pushed at Position (1, 0)`() {
        // Given
        val expectedCrossMultiplier = CrossMultiplier(valueAt10 = 12)
        val position = Pair(1, 0)

        // When
        val actualCrossMultiplier = CrossMultiplier()
            .characterPushedAt(position = position, character = "1")
            .characterPushedAt(position = position, character = "2")

        // Then
        assertEquals(expectedCrossMultiplier, actualCrossMultiplier)
    }

    @Test
    fun `Input of All Positions Pushed`() {
        // Given
        val expectedCrossMultiplier = CrossMultiplier(
            valueAt00 = 34.5,
            valueAt01 = 532.3,
            valueAt10 = 53.6
        )

        // When
        val actualCrossMultiplier = CrossMultiplier()
            .characterPushedAt(position = Pair(0, 0), character = "3")
            .characterPushedAt(position = Pair(0, 0), character = "4")
            .characterPushedAt(position = Pair(0, 0), character = ".")
            .characterPushedAt(position = Pair(0, 0), character = "5")
            .characterPushedAt(position = Pair(0, 1), character = "5")
            .characterPushedAt(position = Pair(0, 1), character = "3")
            .characterPushedAt(position = Pair(0, 1), character = "2")
            .characterPushedAt(position = Pair(0, 1), character = ".")
            .characterPushedAt(position = Pair(0, 1), character = "3")
            .characterPushedAt(position = Pair(1, 0), character = "5")
            .characterPushedAt(position = Pair(1, 0), character = "3")
            .characterPushedAt(position = Pair(1, 0), character = ".")
            .characterPushedAt(position = Pair(1, 0), character = "6")

        // Then
        assertEquals(expectedCrossMultiplier, actualCrossMultiplier)
    }

    @Test
    fun `Characters Pushed and Popped at Position on Position (0, 0)`() {
        // Given
        val expectedCrossMultiplier = emptyCrossMultiplier

        // When
        val actualCrossMultiplier = emptyCrossMultiplier
            .characterPushedAt(position = Pair(0, 0), character = "3")
            .characterPushedAt(position = Pair(0, 0), character = "4")
            .characterPushedAt(position = Pair(0, 0), character = ".")
            .characterPushedAt(position = Pair(0, 0), character = "5")
            .characterPoppedAt(position = Pair(0, 0))
            .characterPoppedAt(position = Pair(0, 0))
            .characterPoppedAt(position = Pair(0, 0))
            .characterPoppedAt(position = Pair(0, 0))

        // Then
        assertEquals(expectedCrossMultiplier, actualCrossMultiplier)
    }

    @Test
    fun `Characters Pushed at All Positions and Then All Popped`() {
        // Given
        val expectedCrossMultiplier = emptyCrossMultiplier

        // When
        val actualCrossMultiplier = emptyCrossMultiplier
            .characterPushedAt(position = Pair(0, 0), character = "3")
            .characterPushedAt(position = Pair(0, 0), character = "4")
            .characterPushedAt(position = Pair(0, 0), character = ".")
            .characterPushedAt(position = Pair(0, 0), character = "5")
            .characterPushedAt(position = Pair(0, 1), character = "5")
            .characterPushedAt(position = Pair(0, 1), character = "3")
            .characterPushedAt(position = Pair(0, 1), character = "2")
            .characterPushedAt(position = Pair(0, 1), character = ".")
            .characterPushedAt(position = Pair(0, 1), character = "3")
            .characterPushedAt(position = Pair(1, 0), character = "5")
            .characterPushedAt(position = Pair(1, 0), character = "3")
            .characterPushedAt(position = Pair(1, 0), character = ".")
            .characterPushedAt(position = Pair(1, 0), character = "6")
            .characterPoppedAt(position = Pair(0, 0))
            .characterPoppedAt(position = Pair(0, 0))
            .characterPoppedAt(position = Pair(0, 0))
            .characterPoppedAt(position = Pair(0, 0))
            .characterPoppedAt(position = Pair(0, 1))
            .characterPoppedAt(position = Pair(0, 1))
            .characterPoppedAt(position = Pair(0, 1))
            .characterPoppedAt(position = Pair(0, 1))
            .characterPoppedAt(position = Pair(0, 1))
            .characterPoppedAt(position = Pair(1, 0))
            .characterPoppedAt(position = Pair(1, 0))
            .characterPoppedAt(position = Pair(1, 0))
            .characterPoppedAt(position = Pair(1, 0))

        // Then
        assertEquals(expectedCrossMultiplier, actualCrossMultiplier)
    }

    @Test
    fun `Character Pushed and Cleared on Position (0, 0)`() {
        // Given
        val expectedCrossMultiplier = emptyCrossMultiplier

        // When
        val actualCrossMultiplier = emptyCrossMultiplier
            .characterPushedAt(character = "1", position = Pair(0, 0))
            .inputClearedAt(position = Pair(0, 0))

        // Then
        assertEquals(expectedCrossMultiplier, actualCrossMultiplier)
    }

    @Test
    fun `Character Pushed and Cleared on Position (0, 1)`() {
        // Given
        val expectedCrossMultiplier = emptyCrossMultiplier

        // When
        val actualCrossMultiplier = emptyCrossMultiplier
            .characterPushedAt(character = "1", position = Pair(0, 1))
            .inputClearedAt(position = Pair(0, 1))

        // Then
        assertEquals(expectedCrossMultiplier, actualCrossMultiplier)
    }

    @Test
    fun `Character Pushed and Cleared on Position (1, 0)`() {
        // Given
        val expectedCrossMultiplier = emptyCrossMultiplier

        // When
        val actualCrossMultiplier = emptyCrossMultiplier
            .characterPushedAt(character = "1", position = Pair(1, 0))
            .inputClearedAt(position = Pair(1, 0))

        // Then
        assertEquals(expectedCrossMultiplier, actualCrossMultiplier)
    }

    @Test
    fun `Characters Pushed and Then All Inputs Cleared`() {
        // Given
        val expectedCrossMultiplier = emptyCrossMultiplier

        // When
        val actualCrossMultiplier = emptyCrossMultiplier
            .characterPushedAt(character = "1", position = Pair(1, 0))
            .characterPushedAt(character = "2", position = Pair(0, 1))
            .characterPushedAt(character = "3", position = Pair(0, 0))
            .allInputsCleared()

        // Then
        assertEquals(expectedCrossMultiplier, actualCrossMultiplier)
    }

    @Test
    fun `Result Calculated`() {
        // Given
        val inputAt00 = 12.3
        val inputAt01 = 45
        val inputAt10 = 4.56
        val expectedResult = inputAt10 * inputAt01 / inputAt00

        // When
        val actualResult = CrossMultiplier(
            valueAt00 = inputAt00,
            valueAt01 = inputAt01,
            valueAt10 = inputAt10,
        ).resultCalculated().unknownValue

        // Then
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `Calculate Result With Zero Division`() {
        // Given
        val inputAt00 = 0
        val inputAt01 = 1.3
        val inputAt10 = 23.4

        // When
        val actualResult = CrossMultiplier(
            valueAt00 = inputAt00,
            valueAt01 = inputAt01,
            valueAt10 = inputAt10,
        ).resultCalculated().unknownValue

        // Then
        assertNull(actualResult)
    }

    @Test
    fun `Change The Unknown Position`() {
        // Given
         val expectedCrossMultiplier = CrossMultiplier(
             valueAt00 = 2.46,
             valueAt10 = 4,
             valueAt11 = 23.4,
             unknownPosition = Pair(0, 1)
        )

        // When
        val actualCrossMultiplier = CrossMultiplier(
            valueAt00 = 2.46,
            valueAt01 = 32,
            valueAt10 = 4,
            valueAt11 = 23.4
        ).unknownPositionChangedTo(position = Pair(0, 1))

        // Then
        assertEquals(expectedCrossMultiplier, actualCrossMultiplier)
    }

    @Test
    fun `To String With Unknown Position At (0, 0)`() {
        // Given
        val crossMultiplier = CrossMultiplier(
            valueAt01 = 7,
            valueAt10 = 3,
            valueAt11 = 6,
            unknownPosition =  Pair(0, 0)
        )
        val expectedString = "\n" +
                "? 7\n" +
                "3 6\n"

        // When
        val actualString = crossMultiplier.toString()

        // Then
        assertEquals(expectedString, actualString)
    }

    @Test
    fun `To String With Unknown Position At (0, 1)`() {
        // Given
        val crossMultiplier = CrossMultiplier(
            valueAt00 = 4,
            valueAt10 = 6,
            valueAt11 = 8,
            unknownPosition =  Pair(0, 1)
        )
        val expectedString = "\n" +
                "4 ?\n" +
                "6 8\n"

        // When
        val actualString = crossMultiplier.toString()

        // Then
        assertEquals(expectedString, actualString)
    }

    @Test
    fun `To String With Unknown Position At (1, 0)`() {
        // Given
        val crossMultiplier = CrossMultiplier(
            valueAt00 = 6,
            valueAt01 = 1,
            valueAt11 = 9,
            unknownPosition =  Pair(1, 0)
        )
        val expectedString = "\n" +
                "6 1\n" +
                "? 9\n"

        // When
        val actualString = crossMultiplier.toString()

        // Then
        assertEquals(expectedString, actualString)
    }

    @Test
    fun `To String With Unknown Position At (1, 1)`() {
        // Given
        val crossMultiplier = CrossMultiplier(
            valueAt00 = 2,
            valueAt01 = 5,
            valueAt10 = 7
        )
        val expectedString = "\n" +
                "2 5\n" +
                "7 ?\n"

        // When
        val actualString = crossMultiplier.toString()

        // Then
        assertEquals(expectedString, actualString)
    }

    @Test
    fun `To String Full With Unknown Position At (0, 1)`() {
        // Given
        val crossMultiplier = CrossMultiplier(
            valueAt00 = 4,
            valueAt01 = 6,
            valueAt10 = 7,
            valueAt11 = 8,
            unknownPosition =  Pair(1, 0)
        )
        val expectedString = "\n" +
                "4 6\n" +
                "7 8\n"

        // When
        val actualString = crossMultiplier.toString()

        // Then
        assertEquals(expectedString, actualString)
    }

    @Test
    fun `Equals Symmetry`() {
        // Given
        val crossMultiplierA = CrossMultiplier(
            valueAt00 = 1,
            valueAt01 = 2.354,
            valueAt10 = 5,
            valueAt11 = 45.3
        )

        val crossMultiplierB = CrossMultiplier(
            valueAt00 = 1,
            valueAt01 = 2.354,
            valueAt10 = 5,
            valueAt11 = 45.3
        )

        // Then
        assertTrue(
            crossMultiplierA == crossMultiplierB && crossMultiplierA == crossMultiplierB
        )
    }

    @Test
    fun `HashCode Symmetry`() {
        // Given
        val crossMultiplierA = CrossMultiplier(
            valueAt00 = 1,
            valueAt01 = 2.354,
            valueAt10 = 5,
            valueAt11 = 45.3
        )

        val crossMultiplierB = CrossMultiplier(
            valueAt00 = 1,
            valueAt01 = 2.354,
            valueAt10 = 5,
            valueAt11 = 45.3
        )

        // Then
        assertTrue(crossMultiplierA.hashCode() == crossMultiplierB.hashCode())
    }
}