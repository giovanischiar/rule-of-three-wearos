package io.schiar.ruleofthree.model

import org.junit.Assert
import org.junit.Test

class CrossMultiplierTest {
    @Test
    fun `Add Two Inputs on Position 0`() {
        Assert.assertEquals(
            CrossMultiplier(a = "12", b = "", ""),
            CrossMultiplier()
                .addToInput(value = "1", position = Pair(0, 0))
                .addToInput(value = "2", position = Pair(0, 0))
        )
    }

    @Test
    fun `Add Two Inputs on Position 1`() {
        Assert.assertEquals(
            CrossMultiplier(a = "", b = "12", ""),
            CrossMultiplier()
                .addToInput(value = "1", position = Pair(0, 1))
                .addToInput(value = "2", position = Pair(0, 1))
        )
    }

    @Test
    fun `Add Two Inputs on Position 2`() {
        Assert.assertEquals(
            CrossMultiplier(a = "", b = "", "12"),
            CrossMultiplier()
                .addToInput(value = "1", position = Pair(1, 0))
                .addToInput(value = "2", position = Pair(1, 0))
        )
    }

    @Test
    fun `Add Input to All Positions`() {
        Assert.assertEquals(
            CrossMultiplier(a = "34.5", b = "532.3", "53.6"),
            CrossMultiplier()
                .addToInput(value = "3", position = Pair(0, 0))
                .addToInput(value = "4", position = Pair(0, 0))
                .addToInput(value = ".", position = Pair(0, 0))
                .addToInput(value = "5", position = Pair(0, 0))
                .addToInput(value = "5", position = Pair(0, 1))
                .addToInput(value = "3", position = Pair(0, 1))
                .addToInput(value = "2", position = Pair(0, 1))
                .addToInput(value = ".", position = Pair(0, 1))
                .addToInput(value = "3", position = Pair(0, 1))
                .addToInput(value = "5", position = Pair(1, 0))
                .addToInput(value = "3", position = Pair(1, 0))
                .addToInput(value = ".", position = Pair(1, 0))
                .addToInput(value = "6", position = Pair(1, 0))
        )
    }

    @Test
    fun `Add Input and Remove Input on Position 0`() {
        Assert.assertEquals(
            CrossMultiplier(a = "", b = "", ""),
            CrossMultiplier()
                .addToInput(value = "3", position = Pair(0, 0))
                .addToInput(value = "4", position = Pair(0, 0))
                .addToInput(value = ".", position = Pair(0, 0))
                .addToInput(value = "5", position = Pair(0, 0))
                .removeFromInput(position = Pair(0, 0))
                .removeFromInput(position = Pair(0, 0))
                .removeFromInput(position = Pair(0, 0))
                .removeFromInput(position = Pair(0, 0))
        )
    }

    @Test
    fun `Add Input to All Positions And Then Remove All`() {
        Assert.assertEquals(
            CrossMultiplier(a = "", b = "", ""),
            CrossMultiplier()
                .addToInput(value = "3", position = Pair(0, 0))
                .addToInput(value = "4", position = Pair(0, 0))
                .addToInput(value = ".", position = Pair(0, 0))
                .addToInput(value = "5", position = Pair(0, 0))
                .addToInput(value = "5", position = Pair(0, 1))
                .addToInput(value = "3", position = Pair(0, 1))
                .addToInput(value = "2", position = Pair(0, 1))
                .addToInput(value = ".", position = Pair(0, 1))
                .addToInput(value = "3", position = Pair(0, 1))
                .addToInput(value = "5", position = Pair(1, 0))
                .addToInput(value = "3", position = Pair(1, 0))
                .addToInput(value = ".", position = Pair(1, 0))
                .addToInput(value = "6", position = Pair(1, 0))
                .removeFromInput(position = Pair(0, 0))
                .removeFromInput(position = Pair(0, 0))
                .removeFromInput(position = Pair(0, 0))
                .removeFromInput(position = Pair(0, 0))
                .removeFromInput(position = Pair(0, 1))
                .removeFromInput(position = Pair(0, 1))
                .removeFromInput(position = Pair(0, 1))
                .removeFromInput(position = Pair(0, 1))
                .removeFromInput(position = Pair(0, 1))
                .removeFromInput(position = Pair(1, 0))
                .removeFromInput(position = Pair(1, 0))
                .removeFromInput(position = Pair(1, 0))
                .removeFromInput(position = Pair(1, 0))
        )
    }

    @Test
    fun `Add Input and Clear on Position 0`() {
        Assert.assertEquals(
            CrossMultiplier(),
            CrossMultiplier()
                .addToInput(value = "1", position = Pair(0, 0)).clear(position = Pair(0, 0))
        )
    }

    @Test
    fun `Add Input and Clear on Position 1`() {
        Assert.assertEquals(
            CrossMultiplier(),
            CrossMultiplier()
                .addToInput(value = "1", position = Pair(0, 1))
                .clear(position = Pair(0, 1))
        )
    }

    @Test
    fun `Add Input and Clear on Position 2`() {
        Assert.assertEquals(
            CrossMultiplier(),
            CrossMultiplier()
                .addToInput(value = "1", position = Pair(1, 0))
                .clear(position = Pair(1, 0))
        )
    }

    @Test
    fun `Add Inputs and Clear All`() {
        Assert.assertEquals(
            CrossMultiplier(),
            CrossMultiplier()
                .addToInput(value = "1", position = Pair(1, 0))
                .addToInput(value = "2", position = Pair(0, 1))
                .addToInput(value = "3", position = Pair(0, 0))
                .clearAll()
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
                result = null,
                unknownPosition = Pair(0, 1)
            ),
            CrossMultiplier(a = "2.46", b = "83.4", c = "23.4")
                .unknownPositionChangedTo(newPosition = Pair(0, 1))
                .resultCalculated()
        )
    }
}