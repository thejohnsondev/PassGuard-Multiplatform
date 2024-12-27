package com.thejohnsondev.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

internal suspend fun DataStore<Preferences>.saveInt(key: String, value: Int) {
    edit { preferences ->
        preferences[intPreferencesKey(key)] = value
    }
}

internal suspend fun DataStore<Preferences>.getInt(key: String, defaultValue: Int): Int {
    return data.first()[intPreferencesKey(key)] ?: defaultValue
}

internal suspend fun DataStore<Preferences>.getIntFlow(key: String, defaultValue: Int): Flow<Int> =
    flow {
        data.collect { preferences ->
            emit(preferences[intPreferencesKey(key)] ?: defaultValue)
        }
    }

internal suspend fun DataStore<Preferences>.saveString(key: String, value: String) {
    edit { preferences ->
        preferences[stringPreferencesKey(key)] = value
    }
}

internal suspend fun DataStore<Preferences>.getString(key: String, defaultValue: String): String {
    return data.first()[stringPreferencesKey(key)] ?: defaultValue
}

internal suspend fun DataStore<Preferences>.getStringsFlow(
    key: String,
    defaultValue: String
): Flow<String> =
    flow {
        data.collect { preferences ->
            emit(preferences[stringPreferencesKey(key)] ?: defaultValue)
        }
    }

internal suspend fun DataStore<Preferences>.clearString(key: String) {
    edit { preferences ->
        preferences.remove(stringPreferencesKey(key))
    }
}

internal suspend fun DataStore<Preferences>.saveBoolean(key: String, value: Boolean) {
    edit { preferences ->
        preferences[booleanPreferencesKey(key)] = value
    }
}

internal suspend fun DataStore<Preferences>.getBoolean(key: String, defaultValue: Boolean): Boolean {
    return data.first()[booleanPreferencesKey(key)] ?: defaultValue
}

internal suspend fun DataStore<Preferences>.getBooleanFlow(
    key: String,
    defaultValue: Boolean
): Flow<Boolean> =
    flow {
        data.collect { preferences ->
            emit(preferences[booleanPreferencesKey(key)] ?: defaultValue)
        }
    }

internal suspend fun DataStore<Preferences>.clearBoolean(key: String) {
    edit { preferences ->
        preferences.remove(booleanPreferencesKey(key))
    }
}