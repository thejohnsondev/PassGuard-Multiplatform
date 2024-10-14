package com.thejohnsondev.datastore

interface PreferencesDataStore {

    suspend fun getAuthToken(): String
    suspend fun saveAuthToken(token: String)
    suspend fun isUserLoggedIn(): Boolean
    suspend fun clearUserData()
    suspend fun saveKey(key: ByteArray)
    suspend fun getKey(): ByteArray
    suspend fun saveEmail(email: String)
    suspend fun getEmail(): String

}