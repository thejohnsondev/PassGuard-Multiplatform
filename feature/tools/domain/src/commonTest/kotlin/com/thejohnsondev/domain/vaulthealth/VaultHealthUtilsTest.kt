package com.thejohnsondev.domain.vaulthealth

import com.thejohnsondev.common.utils.getCurrentTimeStamp
import kotlin.test.*
import com.thejohnsondev.domain.repo.PasswordGenerationRepository
import com.thejohnsondev.model.tools.PasswordGeneratedResult
import com.thejohnsondev.model.tools.PasswordGenerationType
import com.thejohnsondev.model.tools.PasswordStrength
import com.thejohnsondev.model.vault.PasswordDto

class FakePasswordGenerationRepository : PasswordGenerationRepository {
    override fun generatePassword(
        type: PasswordGenerationType,
        length: Int,
        includeLower: Boolean,
        includeUpper: Boolean,
        includeDigits: Boolean,
        includeSpecial: Boolean
    ): PasswordGeneratedResult {
        return PasswordGeneratedResult(
            password = "password",
            strengthLevel = 1f,
            suggestion = null
        )
    }

    override fun evaluateStrength(password: String) = when (password) {
        "weak" -> PasswordStrength(0.2f, suggestion = null)
        "medium" -> PasswordStrength(0.5f, suggestion = null)
        "strong" -> PasswordStrength(0.9f, suggestion = null)
        else -> PasswordStrength(0.1f, suggestion = null)
    }

    override fun isCommonPassword(password: String) = password == "123456" || password == "password"
}

class VaultHealthUtilsTest {
    private val passwordGenerator = FakePasswordGenerationRepository()
    private val utils = VaultHealthUtils(passwordGenerator)

    private fun passwordDto(
        password: String,
        created: String? = null,
        modified: String? = null
    ) = PasswordDto.demo1.copy(
        password = password,
        createdTimeStamp = created ?: getCurrentTimeStamp(),
        modifiedTimeStamp = modified ?: getCurrentTimeStamp()
    )
    @Test
    fun generateReportWithEmptyListReturnsZeroScore() {
        val report = utils.generateReport(emptyList())
        assertEquals(0f, report.overallScore)
        assertEquals(0, report.totalPasswords)
        assertTrue(report.weakPasswords.isEmpty())
        assertTrue(report.mediumPasswords.isEmpty())
        assertTrue(report.strongPasswords.isEmpty())
        assertTrue(report.leakedPasswords.isEmpty())
        assertTrue(report.reusedPasswords.isEmpty())
        assertTrue(report.oldPasswords.isEmpty())
    }

    @Test
    fun calculateVaultHealthScoreReturnsAverageStrength() {
        val passwords = listOf(
            passwordDto("weak"),
            passwordDto("medium"),
            passwordDto("strong")
        )
        val score = utils.calculateVaultHealthScore(passwords)
        assertEquals((0.2f + 0.5f + 0.9f) / 3, score)
    }

    @Test
    fun classifyPasswordsClassifiesCorrectly() {
        val passwords = listOf(
            passwordDto("weak"),
            passwordDto("medium"),
            passwordDto("strong"),
            passwordDto("other")
        )
        val result = utils.classifyPasswords(passwords)
        assertEquals(2, result.weak.size) // "weak" and "other"
        assertEquals(1, result.medium.size)
        assertEquals(1, result.strong.size)
    }

    @Test
    fun findLeakedPasswordsFindsCommonPasswords() {
        val passwords = listOf(
            passwordDto("123456"),
            passwordDto("password"),
            passwordDto("unique")
        )
        val leaked = utils.findLeakedPasswords(passwords)
        assertEquals(2, leaked.size)
        assertTrue(leaked.any { it.password == "123456" })
        assertTrue(leaked.any { it.password == "password" })
    }

    @Test
    fun findReusedPasswordsFindsDuplicates() {
        val passwords = listOf(
            passwordDto("a"),
            passwordDto("b"),
            passwordDto("a"),
            passwordDto("c"),
            passwordDto("b")
        )
        val reused = utils.findReusedPasswords(passwords)
        assertEquals(4, reused.size)
        assertTrue(reused.all { it.password == "a" || it.password == "b" })
    }

    @Test
    fun findOldPasswordsFindsPasswordsOlderThanThreshold() {
        // TODO fix this case
//        val now = getCurrentTimeStamp()
//        fun daysAgo(days: Int) = now - days * 24 * 60 * 60 * 1000L
//        val passwords = listOf(
//            passwordDto("old", created = daysAgo(200)),
//            passwordDto("recent", created = daysAgo(10)),
//            passwordDto("border", created = daysAgo(180)),
//            passwordDto("noDate")
//        )
//        val old = utils.findOldPasswords(passwords, 180)
//        assertTrue(old.any { it.password == "old" })
//        assertTrue(old.any { it.password == "border" })
//        assertFalse(old.any { it.password == "recent" })
//        assertFalse(old.any { it.password == "noDate" })
    }
}