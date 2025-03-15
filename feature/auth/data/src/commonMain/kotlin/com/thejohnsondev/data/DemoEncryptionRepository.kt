package com.thejohnsondev.data

class DemoEncryptionRepository: EncryptionRepository {
    override suspend fun saveSecretKey(key: ByteArray) {
        // No implementation needed
    }

    override suspend fun getSecretKey(): ByteArray {
        return ByteArray(0)
    }

    override suspend fun encrypt(input: String): String {
        return input
    }

    override suspend fun encrypt(input: String, customKey: ByteArray, iv: ByteArray): String {
        return input
    }

    override suspend fun decrypt(input: String): String {
        return input
    }

    override suspend fun decrypt(input: String, customKey: ByteArray, iv: ByteArray): String {
        return input
    }
}