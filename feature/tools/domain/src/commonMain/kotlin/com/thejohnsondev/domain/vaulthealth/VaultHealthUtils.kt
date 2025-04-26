package com.thejohnsondev.domain.vaulthealth

import com.thejohnsondev.common.utils.getCurrentTimeMillis
import com.thejohnsondev.domain.passwordgenerator.PasswordGenerator

internal class VaultHealthUtils(
    private val passwordGenerator: PasswordGenerator,
) {

    fun generateReport(
        passwords: List<PasswordVaultItem>,
        passwordAgeThresholdDays: Int = 180,
    ): VaultHealthReport {
        val score = calculateVaultHealthScore(passwords)
        val classification = classifyPasswords(passwords)
        val leaked = findLeakedPasswords(passwords)
        val reused = findReusedPasswords(passwords)
        val old = findOldPasswords(passwords, passwordAgeThresholdDays)

        return VaultHealthReport(
            overallScore = score,
            weakPasswords = classification.weak,
            mediumPasswords = classification.medium,
            strongPasswords = classification.strong,
            leakedPasswords = leaked,
            reusedPasswords = reused,
            oldPasswords = old
        )
    }

    fun calculateVaultHealthScore(passwords: List<PasswordVaultItem>): Int {
        if (passwords.isEmpty()) return 0
        val averageStrength = passwords.map {
            passwordGenerator.evaluateStrength(it.password).level
        }.average()
        return (averageStrength * 100).toInt()
    }

    fun classifyPasswords(passwords: List<PasswordVaultItem>): PasswordClassificationResult {
        val weak = mutableListOf<PasswordVaultItem>()
        val medium = mutableListOf<PasswordVaultItem>()
        val strong = mutableListOf<PasswordVaultItem>()

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

    fun findLeakedPasswords(passwords: List<PasswordVaultItem>): List<PasswordVaultItem> {
        return passwords.filter {
            passwordGenerator.isCommonPassword(it.password)
        }
    }

    fun findReusedPasswords(passwords: List<PasswordVaultItem>): PasswordReuseResult {
        val passwordCounts = passwords.groupingBy { it.password }.eachCount()
        val reusedPasswords = passwordCounts.filter { it.value > 1 }.keys

        val reusedItems = passwords.filter { reusedPasswords.contains(it.password) }

        return PasswordReuseResult(
            reusedPasswords = reusedPasswords.toList(),
            reusedItems = reusedItems
        )
    }

    fun findOldPasswords(
        passwords: List<PasswordVaultItem>,
        thresholdDays: Int,
    ): List<PasswordVaultItem> {
        val nowMillis = getCurrentTimeMillis()
        return passwords.filter { item ->
            val lastModified = item.modifiedTimeStamp ?: item.createdTimeStamp
            lastModified?.let {
                val ageDays = (nowMillis - it) / (1000L * 60L * 60L * 24L)
                ageDays >= thresholdDays
            } ?: false
        }
    }
}


data class VaultHealthReport(
    val overallScore: Int,
    val weakPasswords: List<PasswordVaultItem>,
    val mediumPasswords: List<PasswordVaultItem>,
    val strongPasswords: List<PasswordVaultItem>,
    val leakedPasswords: List<PasswordVaultItem>,
    val reusedPasswords: PasswordReuseResult,
    val oldPasswords: List<PasswordVaultItem>,
)

data class PasswordVaultItem(
    val id: String,
    val title: String,
    val password: String,
    val createdTimeStamp: Long?,     // epoch millis
    val modifiedTimeStamp: Long?,     // epoch millis
)

data class PasswordReuseResult(
    val reusedPasswords: List<String>,
    val reusedItems: List<PasswordVaultItem>,
)

data class PasswordClassificationResult(
    val weak: List<PasswordVaultItem>,
    val medium: List<PasswordVaultItem>,
    val strong: List<PasswordVaultItem>,
)