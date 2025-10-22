package com.thejohnsondev.data

import com.thejohnsondev.domain.repo.PasswordGenerationRepository
import com.thejohnsondev.common.model.DisplayableMessageValue
import com.thejohnsondev.common.model.tools.PasswordGeneratedResult
import com.thejohnsondev.common.model.tools.PasswordGenerationType
import com.thejohnsondev.common.model.tools.PasswordStrength
import kotlin.random.Random
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

internal class PasswordGeneratorRepositoryImpl(private val commonPasswords: Set<String>) :
    PasswordGenerationRepository {

    private val lowerCase = "abcdefghijklmnopqrstuvwxyz"
    private val upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val digits = "0123456789"
    private val specialChars = "!@\$%-_?/"

    private val passwordRankMap: Map<String, Int> = commonPasswords
        .withIndex()
        .associate { it.value to it.index }

    override fun generatePassword(
        type: PasswordGenerationType,
        length: Int,
        includeLower: Boolean,
        includeUpper: Boolean,
        includeDigits: Boolean,
        includeSpecial: Boolean,
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

    override fun evaluateStrength(password: String): PasswordStrength {
        val rank = passwordRankMap[password.lowercase()]

        if (rank != null) {
            val suggestion = when {
                rank < 10 -> DisplayableMessageValue.Top10Common
                rank < 100 -> DisplayableMessageValue.Top100Common
                rank < 1000 -> DisplayableMessageValue.Top1000Common
                else -> DisplayableMessageValue.VeryCommon
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
            in 0.0..0.1 -> DisplayableMessageValue.StrengthExtremelyWeak
            in 0.2..0.3 -> DisplayableMessageValue.StrengthTooWeak
            in 0.4..0.5 -> DisplayableMessageValue.StrengthWeak
            0.6f -> DisplayableMessageValue.StrengthModerate
            in 0.7..0.8 -> DisplayableMessageValue.StrengthStrong
            0.9f -> DisplayableMessageValue.StrengthVeryStrong
            1f -> DisplayableMessageValue.StrengthExtremelyStrong
            else -> DisplayableMessageValue.StrengthModerate
        }

        return PasswordStrength(score, suggestion)
    }

    override fun isCommonPassword(password: String): Boolean {
        return commonPasswords.contains(password)
    }
}




