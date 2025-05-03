package com.thejohnsondev.domain.vaulthealth

import com.thejohnsondev.common.utils.getCurrentTimeMillis
import com.thejohnsondev.domain.passwordgenerator.PasswordGenerator
import kotlin.test.*
import kotlin.time.Duration.Companion.days

class VaultHealthUtilsTest {

    private lateinit var passwordGenerator: PasswordGenerator
    private lateinit var utils: VaultHealthUtils
    private val now = getCurrentTimeMillis()

    @BeforeTest
    fun setup() {
        val commonPasswords = setOf("123456", "password", "admin")
        passwordGenerator = PasswordGenerator(commonPasswords)
        utils = VaultHealthUtils(passwordGenerator)
    }

    @Test
    fun calculateVaultHealthScore_shouldReturnZero_whenListIsEmpty() {
        val score = utils.calculateVaultHealthScore(emptyList())
        assertEquals(0, score)
    }

    @Test
    fun calculateVaultHealthScore_shouldReturnAverageStrength() {
        val items = listOf(
            vaultItem("1", "pass1", "123456"),
            vaultItem("2", "pass2", "Abc123!@#"),
            vaultItem("3", "pass3", "simple")
        )
        val score = utils.calculateVaultHealthScore(items)
        assertTrue(score in 0..100)
    }

    @Test
    fun classifyPasswords_shouldCorrectlyClassify() {
        val items = listOf(
            vaultItem("w1", "Weak", "1234"), // Weak
            vaultItem("m1", "Medium", "Abcd1234"), // Medium
            vaultItem("s1", "Strong", "Abcd1234!!") // Strong
        )

        val result = utils.classifyPasswords(items)

        assertEquals(1, result.weak.size)
        assertEquals(1, result.medium.size)
        assertEquals(1, result.strong.size)
    }

    @Test
    fun findLeakedPasswords_shouldReturnOnlyCommonPasswords() {
        val items = listOf(
            vaultItem("1", "Leaked", "123456"),
            vaultItem("2", "Safe", "Abc123!@#")
        )
        val leaked = utils.findLeakedPasswords(items)
        assertEquals(1, leaked.size)
        assertEquals("123456", leaked[0].password)
    }

    @Test
    fun findReusedPasswords_shouldDetectReuse() {
        val reusedPass = "samepass"
        val items = listOf(
            vaultItem("1", "one", reusedPass),
            vaultItem("2", "two", reusedPass),
            vaultItem("3", "unique", "anotherpass")
        )

        val result = utils.findReusedPasswords(items)
        assertEquals(listOf(reusedPass), result.reusedPasswords)
        assertEquals(2, result.reusedItems.size)
    }

    @Test
    fun findOldPasswords_shouldReturnItemsOlderThanThreshold() {
        val old = now - 190.days.inWholeMilliseconds
        val recent = now - 10.days.inWholeMilliseconds

        val items = listOf(
            PasswordVaultItem("1", "Old", "pass", old, null),
            PasswordVaultItem("2", "New", "pass", recent, null)
        )

        val result = utils.findOldPasswords(items, thresholdDays = 180)
        assertEquals(1, result.size)
        assertEquals("1", result[0].id)
    }

    @Test
    fun generateReport_shouldIncludeAllMetrics() {
        val reusedPass = "repeat"
        val oldTime = now - 200.days.inWholeMilliseconds

        val items = listOf(
            PasswordVaultItem("1", "Leaked", "123456", oldTime, null),
            PasswordVaultItem("2", "Weak", "abc", now, now),
            PasswordVaultItem("3", "Strong", "Abc12345!", now, now),
            PasswordVaultItem("4", "Reused", reusedPass, now, now),
            PasswordVaultItem("5", "ReusedAgain", reusedPass, now, now)
        )

        val report = utils.generateReport(items)

        assertEquals(5, report.weakPasswords.size + report.mediumPasswords.size + report.strongPasswords.size)
        assertTrue(report.overallScore in 0..100)
        assertEquals(1, report.leakedPasswords.size)
        assertEquals(2, report.reusedPasswords.reusedItems.size)
        assertEquals(1, report.oldPasswords.size)
    }

    private fun vaultItem(id: String, title: String, password: String): PasswordVaultItem {
        return PasswordVaultItem(id, title, password, now, now)
    }
}