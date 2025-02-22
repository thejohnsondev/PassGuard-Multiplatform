package com.thejohnsondev.platform.encryption

import io.ktor.utils.io.core.toByteArray
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.test.Test
import kotlin.test.assertEquals

class EncryptionUtilsTest {

    private val encryptionUtils = AndroidEncryptionUtils()
    private val keyGenerator = AndroidKeyGenerator()

    private val expectedKeySize = 32
    private val passwordSimple = "Pass123"
    private val passwordSimpleDerivedKey = byteArrayOf(38, -119, 103, 58, -83, -4, 70, -48, -2, 66, -46, -48, 77, -91, -11, -96, -1, 117, 54, -27, 8, -22, 76, -65, -63, 101, -19, 39, -88, -65, -1, 6) // 32 byte
    private val passwordSimpleEncrypted = "SwH7R3xjoOACBFWD3Nh0VA=="
    private val passwordComplex = "Abcbefghijklmnopqrstuvwxyz1234567890"
    private val passwordComplexDerivedKey = byteArrayOf(74, -50, 21, 88, -84, 94, 4, 19, 39, -49, 15, -104, 1, 45, 100, -20, -73, -90, 46, 17, -13, -112, -124, 114, -51, 19, 102, 31, -41, -41, 99, 98) // 32 byte
    private val passwordComplexEncrypted = "YaiIAg6d8Ut8FDj5PecNWP6IVwcekJguGhP56UMln7zunpW9P1BBkHSxgeiFgdRU"
    private val passwordVeryComplex =
        "Abcbefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_+"
    private val passwordVeryComplexDerivedKey = byteArrayOf(-22, 36, 119, -112, -83, 84, -16, 110, -46, -31, -9, -9, 88, 5, -10, 15, -44, 27, -37, -108, -80, -52, 61, -26, 106, -70, -107, -20, 63, -66, 65, -59) // 32 byte
    private val passwordVeryComplexEncrypted = "CvrA6Qz9Af1g4aaoDZpLQA8B0phd9l2ZiU6FBkYPar7rn7eOcMrFF8Nj6o2JWZrRX2DJ0VGmZpfRCcQ9JZgsFDEKrJ+Oe/AMrCnFvDu4MWA="
    private val testIv = "9ai62kfFO8qIXuB7tRzIGA=="

    @Test
    fun testPbkdfPasswordSimple() {
        val key = keyGenerator.generateKeyWithPBKDF(passwordSimple)
        assertEquals(expectedKeySize, key.toList().size)
        assertEquals(passwordSimpleDerivedKey.toList(), key.toList())
    }

    @Test
    fun testPbkdfPasswordComplex() {
        val key = keyGenerator.generateKeyWithPBKDF(passwordComplex)
        assertEquals(expectedKeySize, key.size)
        assertEquals(passwordComplexDerivedKey.toList(), key.toList())
    }

    @Test
    fun testPbkdfPasswordVeryComplex() {
        val key = keyGenerator.generateKeyWithPBKDF(passwordVeryComplex)
        assertEquals(expectedKeySize, key.size)
        assertEquals(passwordVeryComplexDerivedKey.toList(), key.toList())
    }

    @Test
    fun testGenerateSecretKeySize() {
        val key = keyGenerator.generateSecretKey()
        assertEquals(expectedKeySize, key.size)
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun testEncryptPasswordSimple() {
        val encrypted = encryptionUtils.encrypt(
            passwordSimple,
            passwordSimpleDerivedKey,
            Base64.decode(testIv)
        )
        assertEquals(passwordSimpleEncrypted, encrypted)
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun testEncryptPasswordComplex() {
        val encrypted = encryptionUtils.encrypt(
            passwordComplex,
            passwordComplexDerivedKey,
            Base64.decode(testIv)
        )
        assertEquals(passwordComplexEncrypted, encrypted)
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun testEncryptPasswordVeryComplex() {
        val encrypted = encryptionUtils.encrypt(
            passwordVeryComplex,
            passwordVeryComplexDerivedKey,
            Base64.decode(testIv)
        )
        assertEquals(passwordVeryComplexEncrypted, encrypted)
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun testDecryptPasswordSimple() {
        val decrypted = encryptionUtils.decrypt(
            passwordSimpleEncrypted,
            passwordSimpleDerivedKey,
            Base64.decode(testIv)
        )
        assertEquals(passwordSimple, decrypted)
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun testDecryptPasswordComplex() {
        val decrypted = encryptionUtils.decrypt(
            passwordComplexEncrypted,
            passwordComplexDerivedKey,
            Base64.decode(testIv)
        )
        assertEquals(passwordComplex, decrypted)
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun testDecryptPasswordVeryComplex() {
        val decrypted = encryptionUtils.decrypt(
            passwordVeryComplexEncrypted,
            passwordVeryComplexDerivedKey,
            Base64.decode(testIv.toByteArray())
        )
        assertEquals(passwordVeryComplex, decrypted)
    }

}