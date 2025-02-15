package com.thejohnsondev.platform

interface KeyGenerator {
    fun generateKeyWithPBKDF(password: String): ByteArray
    fun generateSecretKey(): ByteArray
}