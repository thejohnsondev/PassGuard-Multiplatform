package com.thejohnsondev.data

interface EncryptionRepository {
    suspend fun saveKey(key: ByteArray)
    suspend fun getKey(): ByteArray
    suspend fun encrypt(input: String): String
    suspend fun encrypt(input: String, customKey: ByteArray, iv: ByteArray): String
    suspend fun decrypt(input: String): String
    suspend fun decrypt(input: String, customKey: ByteArray, iv: ByteArray): String
}