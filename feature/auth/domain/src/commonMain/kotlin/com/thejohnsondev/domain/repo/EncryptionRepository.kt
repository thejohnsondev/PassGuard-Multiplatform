package com.thejohnsondev.domain.repo

interface EncryptionRepository {
    suspend fun saveSecretKey(key: ByteArray)
    suspend fun getSecretKey(): ByteArray
    suspend fun encrypt(input: String): String
    suspend fun encrypt(input: String, customKey: ByteArray, iv: ByteArray): String
    suspend fun decrypt(input: String): String
    suspend fun decrypt(input: String, customKey: ByteArray, iv: ByteArray): String
}