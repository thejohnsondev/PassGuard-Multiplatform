package com.thejohnsondev.common.encryption

import io.ktor.utils.io.core.toByteArray
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.test.Test
import kotlin.test.assertEquals

class EncryptionUtilsTest {

    private val expectedKeySize = 16
    private val passwordSimple = "Pass123"
    private val passwordSimpleDerivedKey =
        byteArrayOf(38, -119, 103, 58, -83, -4, 70, -48, -2, 66, -46, -48, 77, -91, -11, -96)
    private val passwordSimpleEncrypted = "78mQZh5yujYWlGOf+x/UlA=="
    private val passwordComplex = "Abcbefghijklmnopqrstuvwxyz1234567890"
    private val passwordComplexDerivedKey =
        byteArrayOf(74, -50, 21, 88, -84, 94, 4, 19, 39, -49, 15, -104, 1, 45, 100, -20)
    private val passwordComplexEncrypted = "/X6UvQnAcuulXaG4rbJQv5z/GPhkzLShBy8lvOeg6/gcR0cCIiLmFR/Bub6/zA/K"
    private val passwordVeryComplex =
        "Abcbefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_+"
    private val passwordVeryComplexDerivedKey =
        byteArrayOf(-22, 36, 119, -112, -83, 84, -16, 110, -46, -31, -9, -9, 88, 5, -10, 15)
    private val passwordVeryComplexEncrypted = "OU8JTIAVnHwQmpEdDD7zSB1DokS1Rpz9JKpVWWhxyPZaNQgXPeLo6CDbX8dTEpDHTPxdSXAVcv2bp29xYbPvswJ+cjppRlmiCMG8TCty47E="
    private val testIv = "9ai62kfFO8qIXuB7tRzIGA=="

    @Test
    fun testPbkdfPasswordSimple() {
        val key = EncryptionUtils.generateKeyWithPBKDF(passwordSimple)
        assertEquals(expectedKeySize, key.toList().size)
        assertEquals(passwordSimpleDerivedKey.toList(), key.toList())
    }

    @Test
    fun testPbkdfPasswordComplex() {
        val key = EncryptionUtils.generateKeyWithPBKDF(passwordComplex)
        assertEquals(16, key.size)
        assertEquals(passwordComplexDerivedKey.toList(), key.toList())
    }

    @Test
    fun testPbkdfPasswordVeryComplex() {
        val key = EncryptionUtils.generateKeyWithPBKDF(passwordVeryComplex)
        assertEquals(16, key.size)
        assertEquals(passwordVeryComplexDerivedKey.toList(), key.toList())
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun testEncryptPasswordSimple() {
        val encrypted = EncryptionUtils.encrypt(
            passwordSimple,
            passwordSimpleDerivedKey,
            Base64.decode(testIv.toByteArray())
        )
        assertEquals(passwordSimpleEncrypted, encrypted)
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun testEncryptPasswordComplex() {
        val encrypted = EncryptionUtils.encrypt(
            passwordComplex,
            passwordComplexDerivedKey,
            Base64.decode(testIv.toByteArray())
        )
        assertEquals(passwordComplexEncrypted, encrypted)
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun testEncryptPasswordVeryComplex() {
        val encrypted = EncryptionUtils.encrypt(
            passwordVeryComplex,
            passwordVeryComplexDerivedKey,
            Base64.decode(testIv.toByteArray())
        )
        assertEquals(passwordVeryComplexEncrypted, encrypted)
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun testDecryptPasswordSimple() {
        val decrypted = EncryptionUtils.decrypt(
            passwordSimpleEncrypted,
            passwordSimpleDerivedKey,
            Base64.decode(testIv.toByteArray())
        )
        assertEquals(passwordSimple, decrypted)
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun testDecryptPasswordComplex() {
        val decrypted = EncryptionUtils.decrypt(
            passwordComplexEncrypted,
            passwordComplexDerivedKey,
            Base64.decode(testIv.toByteArray())
        )
        assertEquals(passwordComplex, decrypted)
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun testDecryptPasswordVeryComplex() {
        val decrypted = EncryptionUtils.decrypt(
            passwordVeryComplexEncrypted,
            passwordVeryComplexDerivedKey,
            Base64.decode(testIv.toByteArray())
        )
        assertEquals(passwordVeryComplex, decrypted)
    }

}