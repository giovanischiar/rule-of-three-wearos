package io.schiar.ruleofthree.model

import org.junit.Assert
import org.junit.Test

class CrossMultiplierTest {
    @Test
    fun `Add Two Inputs on Position 0`() {
        Assert.assertEquals(
            CrossMultiplier(a = "12", b = "", ""),
            CrossMultiplier()
                .characterPushedAt(character = "1", position = Pair(0, 0))
                .characterPushedAt(character = "2", position = Pair(0, 0))
        )
    }

    @Test
    fun `Add Two Inputs on Position 1`() {
        Assert.assertEquals(
            CrossMultiplier(a = "", b = "12", ""),
            CrossMultiplier()
                .characterPushedAt(character = "1", position = Pair(0, 1))
                .characterPushedAt(character = "2", position = Pair(0, 1))
        )
    }

    @Test
    fun `Add Two Inputs on Position 2`() {
        Assert.assertEquals(
            CrossMultiplier(a = "", b = "", "12"),
            CrossMultiplier()
                .characterPushedAt(character = "1", position = Pair(1, 0))
                .characterPushedAt(character = "2", position = Pair(1, 0))
        )
    }

    @Test
    fun `Add Input to All Positions`() {
        Assert.assertEquals(
            CrossMultiplier(a = "34.5", b = "532.3", "53.6"),
            CrossMultiplier()
                .characterPushedAt(character = "3", position = Pair(0, 0))
                .characterPushedAt(character = "4", position = Pair(0, 0))
                .characterPushedAt(character = ".", position = Pair(0, 0))
                .characterPushedAt(character = "5", position = Pair(0, 0))
                .characterPushedAt(character = "5", position = Pair(0, 1))
                .characterPushedAt(character = "3", position = Pair(0, 1))
                .characterPushedAt(character = "2", position = Pair(0, 1))
                .characterPushedAt(character = ".", position = Pair(0, 1))
                .characterPushedAt(character = "3", position = Pair(0, 1))
                .characterPushedAt(character = "5", position = Pair(1, 0))
                .characterPushedAt(character = "3", position = Pair(1, 0))
                .characterPushedAt(character = ".", position = Pair(1, 0))
                .characterPushedAt(character = "6", position = Pair(1, 0))
        )
    }

    @Test
    fun `Add Input and Remove Input on Position 0`() {
        Assert.assertEquals(
            CrossMultiplier(a = "", b = "", ""),
            CrossMultiplier()
                .characterPushedAt(character = "3", position = Pair(0, 0))
                .characterPushedAt(character = "4", position = Pair(0, 0))
                .characterPushedAt(character = ".", position = Pair(0, 0))
                .characterPushedAt(character = "5", position = Pair(0, 0))
                .characterPoppedAt(position = Pair(0, 0))
                .characterPoppedAt(position = Pair(0, 0))
                .characterPoppedAt(position = Pair(0, 0))
                .characterPoppedAt(position = Pair(0, 0))
        )
    }

    @Test
    fun `Add Input to All Positions And Then Remove All`() {
        Assert.assertEquals(
            CrossMultiplier(a = "", b = "", ""),
            CrossMultiplier()
                .characterPushedAt(character = "3", position = Pair(0, 0))
                .characterPushedAt(character = "4", position = Pair(0, 0))
                .characterPushedAt(character = ".", position = Pair(0, 0))
                .characterPushedAt(character = "5", position = Pair(0, 0))
                .characterPushedAt(character = "5", position = Pair(0, 1))
                .characterPushedAt(character = "3", position = Pair(0, 1))
                .characterPushedAt(character = "2", position = Pair(0, 1))
                .characterPushedAt(character = ".", position = Pair(0, 1))
                .characterPushedAt(character = "3", position = Pair(0, 1))
                .characterPushedAt(character = "5", position = Pair(1, 0))
                .characterPushedAt(character = "3", position = Pair(1, 0))
                .characterPushedAt(character = ".", position = Pair(1, 0))
                .characterPushedAt(character = "6", position = Pair(1, 0))
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
        )
    }

    @Test
    fun `Add Input and Clear on Position 0`() {
        Assert.assertEquals(
            CrossMultiplier(),
            CrossMultiplier()
                .characterPushedAt(character = "1", position = Pair(0, 0)).inputClearedAt(position = Pair(0, 0))
        )
    }

    @Test
    fun `Add Input and Clear on Position 1`() {
        Assert.assertEquals(
            CrossMultiplier(),
            CrossMultiplier()
                .characterPushedAt(character = "1", position = Pair(0, 1))
                .inputClearedAt(position = Pair(0, 1))
        )
    }

    @Test
    fun `Add Input and Clear on Position 2`() {
        Assert.assertEquals(
            CrossMultiplier(),
            CrossMultiplier()
                .characterPushedAt(character = "1", position = Pair(1, 0))
                .inputClearedAt(position = Pair(1, 0))
        )
    }

    @Test
    fun `Add Inputs and Clear All`() {
        Assert.assertEquals(
            CrossMultiplier(),
            CrossMultiplier()
                .characterPushedAt(character = "1", position = Pair(1, 0))
                .characterPushedAt(character = "2", position = Pair(0, 1))
                .characterPushedAt(character = "3", position = Pair(0, 0))
                .allInputsCleared()
        )
    }

    @Test
    fun `Calculate Result`() {
        val a = 12.3
        val b = 45
        val c = 4.56
        Assert.assertEquals(c * b / a,
            CrossMultiplier(
                a = a.toString(),
                b = b.toString(),
                c = c.toString(),
            ).resultCalculated()
            .result()
        )
    }

    @Test
    fun `Calculate Result With Zero Division`() {
        Assert.assertEquals(
            null,
            CrossMultiplier(a = "0", b = "1.3", c = "23.4").resultCalculated().result()
        )
    }

    @Test
    fun `Calculate Result With Input Invalid`() {
        Assert.assertEquals(
            null,
            CrossMultiplier(a = "ere34", b = "e324", c = "jghn").resultCalculated().result()
        )
    }

    @Test
    fun `Change The Unknown Position`() {
        Assert.assertEquals(
            CrossMultiplier(
                a = "2.46",
                b = "",
                c = "23.4",
                unknownPosition = Pair(0, 1)
            ).resultCalculated(),
            CrossMultiplier(a = "2.46", b = "83.4", c = "23.4")
                .unknownPositionChangedTo(position = Pair(0, 1))
                .resultCalculated()
        )
    }
}