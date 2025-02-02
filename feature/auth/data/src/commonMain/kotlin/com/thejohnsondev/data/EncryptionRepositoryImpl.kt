package com.thejohnsondev.data

import com.thejohnsondev.common.encryption.EncryptionUtils
import com.thejohnsondev.datastore.PreferencesDataStore

class EncryptionRepositoryImpl(
    private val preferencesDataStore: PreferencesDataStore,
) : EncryptionRepository {
    override suspend fun saveSecretKey(key: ByteArray) {
        preferencesDataStore.saveSecretKey(key)
    }

    override suspend fun getSecretKey(): ByteArray {
        return preferencesDataStore.getSecretKey()
    }

    override suspend fun encrypt(input: String): String {
        return EncryptionUtils.encrypt(
            input,
            getSecretKey()
        )
    }

    override suspend fun encrypt(input: String, customKey: ByteArray, iv: ByteArray): String {
        return EncryptionUtils.encrypt(
            input,
            customKey,
            iv
        )
    }

    override suspend fun decrypt(input: String): String {
        return EncryptionUtils.decrypt(
            input,
            getSecretKey()
        )
    }

    override suspend fun decrypt(input: String, customKey: ByteArray, iv: ByteArray): String {
        return EncryptionUtils.decrypt(
            input,
            customKey,
            iv
        )
    }

}