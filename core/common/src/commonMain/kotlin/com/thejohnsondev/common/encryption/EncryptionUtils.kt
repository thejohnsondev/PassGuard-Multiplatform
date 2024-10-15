package com.thejohnsondev.common.encryption

expect object EncryptionUtils {
    fun generateKey(password: String): ByteArray
    fun encrypt(input: String, key: ByteArray, iv: ByteArray?): String
    fun decrypt(input: String, key: ByteArray, iv: ByteArray?): String
}