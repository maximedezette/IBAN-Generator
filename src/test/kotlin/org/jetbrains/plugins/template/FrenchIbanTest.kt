package org.jetbrains.plugins.template

import org.junit.Assert.assertEquals
import org.junit.Test

class FrenchIbanTest {

    @Test
    fun testShouldBuildValidIban() {
        val iban = FrenchIban.create()

        val frenchCountryCode = "FR";
        assertEquals(iban.getValue().length, 27)
        assertEquals(iban.getValue().substring(0, 2), frenchCountryCode)
    }
}