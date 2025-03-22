package com.thejohnsondev.platform.storage

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences

class AndroidSecureStorage(context: Context): SecureStorage {
    private val sharedPreferences = EncryptedSharedPreferences.create(
        "vault_secrets",
        "vault_secrets_key",
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun save(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun read(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

}