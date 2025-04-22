package com.thejohnsondev.domain.utils

import com.thejohnsondev.model.tools.PasswordGeneratedResult
import com.thejohnsondev.model.tools.PasswordGenerationType
import com.thejohnsondev.model.tools.PasswordStrength
import kotlin.random.Random
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

internal class PasswordGenerator(private val commonPasswords: Set<String>) {

    private val lowerCase = "abcdefghijklmnopqrstuvwxyz"
    private val upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val digits = "0123456789"
    private val specialChars = "!@#\$%^&*()-_=+[]{}|;:'\",.<>?/"

    private val passwordRankMap: Map<String, Int> = commonPasswords
        .withIndex()
        .associate { it.value to it.index }

    fun generatePassword(
        type: PasswordGenerationType,
        length: Int = 12,
        includeLower: Boolean = true,
        includeUpper: Boolean = true,
        includeDigits: Boolean = true,
        includeSpecial: Boolean = true,
    ): PasswordGeneratedResult {
        val password = when (type) {
            PasswordGenerationType.RANDOM -> generateRandomPassword(
                length,
                includeLower,
                includeUpper,
                includeDigits,
                includeSpecial
            )

            PasswordGenerationType.HUMAN -> generateHumanReadablePassword(length)
            PasswordGenerationType.UUID -> generateUUID()
        }
        val strength = evaluateStrength(password)
        return PasswordGeneratedResult(password, strength.level, strength.suggestion)
    }

    private fun generateRandomPassword(
        length: Int,
        includeLower: Boolean,
        includeUpper: Boolean,
        includeDigits: Boolean,
        includeSpecial: Boolean,
    ): String {
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
        val rank = passwordRankMap[password.lowercase()]

        if (rank != null) {
            val suggestion = when {
                rank < 10 -> "This password is in the TOP 10 most common. Avoid it at all costs."
                rank < 100 -> "This password is in the top 100 most common. It's very unsafe."
                rank < 1000 -> "This password is in the top 1000 most common. It's easy to guess."
                else -> "This password is very common. Choose a more unique one."
            }
            return PasswordStrength(0.1f, suggestion)
        }

        val lengthScore = when {
            password.length >= 16 -> 10
            password.length >= 12 -> 8
            password.length >= 10 -> 6
            password.length >= 8 -> 4
            password.length >= 6 -> 3
            password.length >= 4 -> 2
            else -> 0
        }

        val diversityScore = (listOf(
            password.any { it.isLowerCase() },
            password.any { it.isUpperCase() },
            password.any { it.isDigit() },
            password.any { !it.isLetterOrDigit() }
        ).count { it } * 0.75).toInt()

        val score = (lengthScore + diversityScore).coerceAtMost(10) * 0.1f

        val suggestion = when (score) {
            in 0.0..0.1 -> "Extremely weak! Use at least 8 characters with mixed cases and symbols."
            in 0.2..0.3 -> "Too weak, add more characters and mix uppercase, lowercase, and numbers."
            in 0.4..0.5 -> "Weak. Consider adding special symbols and increasing length."
            0.6f -> "Moderate. Try using a longer password with symbols."
            in 0.7..0.8 -> "Strong. Ensure it's not a common phrase."
            0.9f -> "Very strong! Nearly unbreakable but avoid using personal information."
            1f -> "Extremely strong! This password is highly secure."
            else -> "Moderate. Try using a longer password with symbols."
        }

        return PasswordStrength(score, suggestion)
    }
}




