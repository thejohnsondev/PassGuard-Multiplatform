package com.thejohnsondev.domain.passwordgenerator

import kotlin.random.Random
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class PasswordGenerator(private val commonPasswords: Set<String>) {

    private val lowerCase = "abcdefghijklmnopqrstuvwxyz"
    private val upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val digits = "0123456789"
    private val specialChars = "!@#\$%^&*()-_=+[]{}|;:'\",.<>?/"

    fun generatePassword(
        type: PasswordType,
        length: Int = 12,
        includeLower: Boolean = true,
        includeUpper: Boolean = true,
        includeDigits: Boolean = true,
        includeSpecial: Boolean = true
    ): GeneratedPassword {
        val password = when (type) {
            PasswordType.RANDOM -> generateRandomPassword(length, includeLower, includeUpper, includeDigits, includeSpecial)
            PasswordType.HUMAN -> generateHumanReadablePassword(length)
            PasswordType.UUID -> generateUUID()
        }
        val strength = evaluateStrength(password)
        return GeneratedPassword(password, strength.level, strength.suggestion)
    }

    private fun generateRandomPassword(length: Int, includeLower: Boolean, includeUpper: Boolean, includeDigits: Boolean, includeSpecial: Boolean): String {
        val charPool = buildString {
            if (includeLower) append(lowerCase)
            if (includeUpper) append(upperCase)
            if (includeDigits) append(digits)
            if (includeSpecial) append(specialChars)
        }
        if (charPool.isEmpty()) return ""
        return (1..length).map { charPool[Random.nextInt(charPool.length)] }.joinToString("")
    }

    private fun generateHumanReadablePassword(length: Int): String {
        val wordList = HumanPasswordWords.wordList
        val words = mutableListOf<String>()
        while (words.joinToString("").length < length) {
            words.add(wordList[Random.nextInt(wordList.size)])
        }
        return words.joinToString("-").take(length)
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun generateUUID(): String = Uuid.random().toString()

    fun evaluateStrength(password: String): PasswordStrength {
        if (commonPasswords.contains(password))
            return PasswordStrength(1, "This password is very common. Choose a more unique one.")

        val lengthScore = when {
            password.length >= 16 -> 10
            password.length >= 12 -> 8
            password.length >= 10 -> 6
            password.length >= 8 -> 4
            password.length >= 6 -> 3
            password.length >= 4 -> 2
            else -> 1
        }

        val diversityScore = (listOf(
            password.any { it.isLowerCase() },
            password.any { it.isUpperCase() },
            password.any { it.isDigit() },
            password.any { !it.isLetterOrDigit() }
        ).count { it } * 0.75).toInt()

        val score = (lengthScore + diversityScore).coerceAtMost(10)

        val suggestion = when (score) {
            in 0..1 -> "Extremely weak! Use at least 8 characters with mixed cases and symbols."
            in 2..3 -> "Too weak, add more characters and mix uppercase, lowercase, and numbers."
            in 4..5 -> "Weak. Consider adding special symbols and increasing length."
            6 -> "Moderate. Try using a longer password with symbols."
            in 7..8 -> "Strong. Ensure it's not a common phrase."
            9 -> "Very strong! Nearly unbreakable but avoid using personal information."
            10 -> "Extremely strong! This password is highly secure."
            else -> "Unknown strength."
        }

        return PasswordStrength(score, suggestion)
    }
}

data class GeneratedPassword(val password: String, val strengthLevel: Int, val suggestion: String)

data class PasswordStrength(val level: Int, val suggestion: String)

enum class PasswordType {
    RANDOM, HUMAN, UUID
}
