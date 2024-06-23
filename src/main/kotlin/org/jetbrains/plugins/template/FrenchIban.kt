package org.jetbrains.plugins.template

import java.math.BigInteger
import kotlin.random.Random

private const val COUNTRY_CODE = "FR"

class FrenchIban {

    private constructor() {
        this.value = generateRandomIban()
    }

    private var value: String

    fun getValue(): String {
        return this.value;
    }

    private fun generateRandomIban(): String {
        val bban = generateRandom()
        val checkDigits = calculateCheckDigits(bban)
        return "$COUNTRY_CODE$checkDigits$bban"
    }

    private fun generateRandom(): String {
        val bankCode = Random.nextInt(10000, 99999).toString().padStart(5, '0')
        val branchCode = Random.nextInt(10000, 99999).toString().padStart(5, '0')
        val accountNumber = Random.nextInt(0, 99999999999.toInt()).toString().padStart(11, '0')
        val checkDigits = Random.nextInt(0, 99).toString().padStart(2, '0')
        return bankCode + branchCode + accountNumber + checkDigits

    }

    private fun calculateCheckDigits(bban: String): String {
        val numericCountryCode = COUNTRY_CODE.map { it.code - 'A'.code + 10 }.joinToString("")
        val checkString = bban + numericCountryCode + "00"
        val checkInt = BigInteger(checkString).mod(BigInteger.valueOf(97))
        val checkDigits = 98 - checkInt.toInt()

        return checkDigits.toString().padStart(2, '0')
    }

    companion object {
        fun create(): FrenchIban {
            return FrenchIban();
        }
    }
}
