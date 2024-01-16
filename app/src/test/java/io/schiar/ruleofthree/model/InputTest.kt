package io.schiar.ruleofthree.model

import org.junit.Assert
import org.junit.Test

class InputTest {
    @Test
    fun `Add Invalid String`() {
        Assert.assertEquals(
            Input(value = ""),
            Input()
                .add(newValue = "a")
                .add(newValue = "b")
        )
    }

    @Test
    fun `Add Trailing Zeroes`() {
        Assert.assertEquals(Input(value = 1), Input().add(newValue = "0").add(newValue = "1"))
    }

    @Test
    fun `Add Number Less Than Zero With Zeroes After Decimal Point`() {
        Assert.assertEquals(
            Input(value = 0.001),
            Input()
                .add(newValue = "0")
                .add(newValue = ".")
                .add(newValue = "0")
                .add(newValue = "0")
                .add(newValue = "1")
        )
    }

    @Test
    fun `Add Multiple Decimal Points`() {
        Assert.assertEquals(
            Input(value = "1.23"),
            Input()
                .add(newValue = "1")
                .add(newValue = ".")
                .add(newValue = "2")
                .add(newValue = ".")
                .add(newValue = "3")
        )
    }

    @Test
    fun `Add and Remove Values`() {
        Assert.assertEquals(
            Input(value = "1.23"),
            Input()
                .add(newValue = "1")
                .add(newValue = ".")
                .remove()
                .add(newValue = ".")
                .add(newValue = "2")
                .add(newValue = "3")
                .add(newValue = "5")
                .add(newValue = "6")
                .add(newValue = "7")
                .remove()
                .remove()
                .remove()
        )
    }

    @Test
    fun `Remove Values When Empty`() {
        Assert.assertEquals(Input(value = ""), Input().remove().remove())
    }

    @Test
    fun `Add Two Inputs and Then Clear`() {
        Assert.assertEquals(Input(value = ""), Input().add("1").add("2").clear())
    }

    @Test
    fun `Clear When Empty`() {
        Assert.assertEquals(Input(value = ""), Input().clear())
    }

    @Test
    fun `Clear and Then Remove`() {
        Assert.assertEquals(Input(value = ""), Input().clear().remove())
    }

    @Test
    fun `Double Invalid Value`() {
        Assert.assertEquals(null, Input().toDoubleOrNull())
    }

    @Test
    fun `Double valid Value`() {
        Assert.assertEquals(1.3, Input("1.3").toDoubleOrNull())
    }
}