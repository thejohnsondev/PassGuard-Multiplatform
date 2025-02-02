package com.thejohnsondev.common.encryption

expect object EncryptionUtils {
    fun generateKeyWithPBKDF(password: String): ByteArray
    fun generateSecretKey(): ByteArray
    fun encrypt(input: String, key: ByteArray, iv: ByteArray? = null): String
    fun decrypt(input: String, key: ByteArray, iv: ByteArray? = null): String
}