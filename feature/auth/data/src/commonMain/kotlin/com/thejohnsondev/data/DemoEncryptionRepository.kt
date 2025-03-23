package com.thejohnsondev.data

import com.thejohnsondev.datastore.PreferencesDataStore

class DemoEncryptionRepository(
    private val preferencesDataStore: PreferencesDataStore,
): EncryptionRepository {
    override suspend fun saveSecretKey(key: ByteArray) {
        preferencesDataStore.saveSecretKey(key)
    }

    override suspend fun getSecretKey(): ByteArray {
        return preferencesDataStore.getSecretKey()
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