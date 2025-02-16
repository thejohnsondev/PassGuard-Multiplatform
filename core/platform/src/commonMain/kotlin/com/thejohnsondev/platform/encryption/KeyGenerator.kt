package com.thejohnsondev.platform.encryption

interface KeyGenerator {
    fun generateKeyWithPBKDF(password: String): ByteArray
    fun generateSecretKey(): ByteArray
}