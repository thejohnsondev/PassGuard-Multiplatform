package com.thejohnsondev.platform.encryption

interface EncryptionUtils {
    fun encrypt(input: String, key: ByteArray, iv: ByteArray? = null): String
    fun decrypt(input: String, key: ByteArray, iv: ByteArray? = null): String
}