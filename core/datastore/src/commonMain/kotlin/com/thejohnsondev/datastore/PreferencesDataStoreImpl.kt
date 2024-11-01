package com.thejohnsondev.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class PreferencesDataStoreImpl(
    private val dataStore: DataStore<Preferences>
) : PreferencesDataStore {

    override suspend fun getAuthToken(): String {
        return dataStore.getString(KEY_AUTH_TOKEN)
    }

    override suspend fun saveAuthToken(token: String) {
        dataStore.saveString(KEY_AUTH_TOKEN, token)
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return getAuthToken().isNotEmpty()
    }

    override suspend fun clearUserData() {
        dataStore.clearString(KEY_AUTH_TOKEN)
        dataStore.clearString(KEY_EMAIL)
        dataStore.clearString(KEY_KEY)
    }

    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun saveKey(key: ByteArray) {
        dataStore.saveString(KEY_KEY, Base64.encode(key))
    }

    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun getKey(): ByteArray {
        val keyString = dataStore.getString(KEY_KEY)
        return Base64.decode(keyString)
    }

    override suspend fun saveEmail(email: String) {
        dataStore.saveString(KEY_EMAIL, email)
    }

    override suspend fun getEmail(): String {
        return dataStore.getString(KEY_EMAIL)
    }

    companion object {
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_EMAIL = "email"
        private const val KEY_KEY = "key"
    }

}