package com.thejohnsondev.common.encryption

expect object EncryptionUtils {
    fun encrypt(input: String, key: ByteArray, iv: ByteArray? = null): String
    fun decrypt(input: String, key: ByteArray, iv: ByteArray? = null): String
}