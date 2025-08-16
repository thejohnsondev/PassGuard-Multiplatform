package com.thejohnsondev.data

import com.thejohnsondev.datastore.PreferencesDataStore
import com.thejohnsondev.domain.repo.EncryptionRepository
import com.thejohnsondev.platform.encryption.EncryptionUtils

class EncryptionRepositoryImpl(
    private val preferencesDataStore: PreferencesDataStore,
    private val encryptionUtils: EncryptionUtils
) : EncryptionRepository {
    override suspend fun saveSecretKey(key: ByteArray) {
        preferencesDataStore.saveSecretKey(key)
    }

    override suspend fun getSecretKey(): ByteArray {
        return preferencesDataStore.getSecretKey()
    }

    override suspend fun encrypt(input: String): String {
        return encryptionUtils.encrypt(
            input,
            getSecretKey()
        )
    }

    override suspend fun encrypt(input: String, customKey: ByteArray, iv: ByteArray): String {
        return encryptionUtils.encrypt(
            input,
            customKey,
            iv
        )
    }

    override suspend fun decrypt(input: String): String {
        return encryptionUtils.decrypt(
            input,
            getSecretKey()
        )
    }

    override suspend fun decrypt(input: String, customKey: ByteArray, iv: ByteArray): String {
        return encryptionUtils.decrypt(
            input,
            customKey,
            iv
        )
    }
}