package com.thejohnsondev.domain.vaulthealth

import com.thejohnsondev.common.utils.toAgeInDays
import com.thejohnsondev.domain.passwordgenerator.PasswordGenerator
import com.thejohnsondev.model.vault.PasswordDto

class VaultHealthUtils(
    private val passwordGenerator: PasswordGenerator,
) {

    fun generateReport(
        passwords: List<PasswordDto>,
        passwordAgeThresholdDays: Int = 180,
    ): VaultHealthReport {
        val score = calculateVaultHealthScore(passwords)
        val classification = classifyPasswords(passwords)
        val leaked = findLeakedPasswords(passwords)
        val reused = findReusedPasswords(passwords)
        val old = findOldPasswords(passwords, passwordAgeThresholdDays)

        return VaultHealthReport(
            overallScore = score,
            totalPasswords = passwords.size,
            weakPasswords = classification.weak,
            mediumPasswords = classification.medium,
            strongPasswords = classification.strong,
            leakedPasswords = leaked,
            reusedPasswords = reused,
            oldPasswords = old
        )
    }

    fun calculateVaultHealthScore(passwords: List<PasswordDto>): Float {
        if (passwords.isEmpty()) return 0f
        val averageStrength = passwords.map {
            passwordGenerator.evaluateStrength(it.password).level
        }.average()
        return averageStrength.toFloat()
    }

    fun classifyPasswords(passwords: List<PasswordDto>): PasswordClassificationResult {
        val weak = mutableListOf<PasswordDto>()
        val medium = mutableListOf<PasswordDto>()
        val strong = mutableListOf<PasswordDto>()

        passwords.forEach { item ->
            val strength = passwordGenerator.evaluateStrength(item.password)
            when (strength.level) {
                in 0.0f..0.3f -> weak.add(item)
                in 0.4f..0.6f -> medium.add(item)
                in 0.7f..1.0f -> strong.add(item)
            }
        }
        return PasswordClassificationResult(weak, medium, strong)
    }

    fun findLeakedPasswords(passwords: List<PasswordDto>): List<PasswordDto> {
        return passwords.filter {
            passwordGenerator.isCommonPassword(it.password)
        }
    }

    fun findReusedPasswords(passwords: List<PasswordDto>): List<PasswordDto> {
        val passwordCounts = passwords.groupingBy { it.password }.eachCount()
        val reusedPasswords = passwordCounts.filter { it.value > 1 }.keys

        val reusedItems = passwords.filter { reusedPasswords.contains(it.password) }

        return reusedItems
    }

    fun findOldPasswords(
        passwords: List<PasswordDto>,
        thresholdDays: Int,
    ): List<PasswordDto> {
        return passwords.filter { item ->
            val lastModified = item.modifiedTimeStamp ?: item.createdTimeStamp
            lastModified?.let {
                val ageDays = it.toLong().toAgeInDays()
                return@let ageDays >= thresholdDays
            } ?: false
        }
    }
}


data class VaultHealthReport(
    val overallScore: Float,
    val totalPasswords: Int = 0,
    val weakPasswords: List<PasswordDto>,
    val mediumPasswords: List<PasswordDto>,
    val strongPasswords: List<PasswordDto>,
    val leakedPasswords: List<PasswordDto>,
    val reusedPasswords: List<PasswordDto>,
    val oldPasswords: List<PasswordDto>,
)

data class PasswordClassificationResult(
    val weak: List<PasswordDto>,
    val medium: List<PasswordDto>,
    val strong: List<PasswordDto>,
)