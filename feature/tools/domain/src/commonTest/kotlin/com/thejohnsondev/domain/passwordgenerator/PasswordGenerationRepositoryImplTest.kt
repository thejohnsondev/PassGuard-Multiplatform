package com.thejohnsondev.domain.passwordgenerator

import com.thejohnsondev.data.PasswordGeneratorImpl
import com.thejohnsondev.model.tools.PasswordGenerationType

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PasswordGenerationRepositoryImplTest {

    private val passwordGenerator = PasswordGeneratorImpl(HumanPasswordWords.wordList.toSet()) // No common passwords provided

    @Test
    fun `generate random password with default settings`() {
        val generated = passwordGenerator.generatePassword(PasswordGenerationType.RANDOM)
        assertEquals(12, generated.password.length)
    }

    @Test
    fun `generate random password with only lowercase`() {
        val generated = passwordGenerator.generatePassword(
            PasswordGenerationType.RANDOM,
            length = 10,
            includeLower = true,
            includeUpper = false,
            includeDigits = false,
            includeSpecial = false
        )
        assertTrue(generated.password.all { it.isLowerCase() })
    }

    @Test
    fun `generate random password with only uppercase`() {
        val generated = passwordGenerator.generatePassword(
            PasswordGenerationType.RANDOM,
            length = 10,
            includeLower = false,
            includeUpper = true,
            includeDigits = false,
            includeSpecial = false
        )
        assertTrue(generated.password.all { it.isUpperCase() })
    }

    @Test
    fun `generate random password with only digits`() {
        val generated = passwordGenerator.generatePassword(
            PasswordGenerationType.RANDOM,
            length = 10,
            includeLower = false,
            includeUpper = false,
            includeDigits = true,
            includeSpecial = false
        )
        assertTrue(generated.password.all { it.isDigit() })
    }

    @Test
    fun `generate random password with only special characters`() {
        val generated = passwordGenerator.generatePassword(
            PasswordGenerationType.RANDOM,
            length = 10,
            includeLower = false,
            includeUpper = false,
            includeDigits = false,
            includeSpecial = true
        )
        assertTrue(generated.password.all { "!@#\$%^&*()-_=+[]{}|;:'\",.<>?/".contains(it) })
    }

    @Test
    fun `generate human-readable password`() {
        val generated = passwordGenerator.generatePassword(PasswordGenerationType.HUMAN, length = 16)
        assertTrue(generated.password.isNotBlank())
        assertTrue(generated.password.contains("-")) // Expecting words joined by dashes
    }

    @Test
    fun `generate UUID password`() {
        val generated = passwordGenerator.generatePassword(PasswordGenerationType.UUID)
        assertEquals(36, generated.password.length)
        assertTrue(generated.password.count { it == '-' } == 4) // UUID format check
    }

    @Test
    fun `evaluate strength - very weak short password`() {
        val strength = passwordGenerator.evaluateStrength("abc")
        assertEquals(0.0f, strength.level)
        assertEquals(
            "Extremely weak! Use at least 8 characters with mixed cases and symbols.",
            strength.suggestion
        )
    }

    @Test
    fun `evaluate strength - weak password`() {
        val strength = passwordGenerator.evaluateStrength("abcdef")
        assertTrue(strength.level in 0.2f..0.3f)
        assertEquals(
            "Moderate. Try using a longer password with symbols.",
            strength.suggestion
        )
    }

    @Test
    fun `evaluate strength - moderate password`() {
        val strength = passwordGenerator.evaluateStrength("Abc12345")

        assertTrue(strength.level in 0.4f..0.6f)
    }

    @Test
    fun `evaluate strength - good password`() {
        val strength = passwordGenerator.evaluateStrength("Abc12345!")
        assertTrue(strength.level in 0.7f..0.8f)
        assertEquals("Moderate. Try using a longer password with symbols.", strength.suggestion)
    }

    @Test
    fun `evaluate strength - very strong password`() {
        val strength = passwordGenerator.evaluateStrength("A1b2C3d4E5F6G@H!")
        assertEquals(1f, strength.level)
        assertEquals("Extremely strong! This password is highly secure.", strength.suggestion)
    }

    @Test
    fun `empty character set returns empty password`() {
        val generated = passwordGenerator.generatePassword(
            PasswordGenerationType.RANDOM,
            length = 10,
            includeLower = false,
            includeUpper = false,
            includeDigits = false,
            includeSpecial = false
        )
        assertEquals("", generated.password)
    }
}