package com.thejohnsondev.datastore

import com.thejohnsondev.model.settings.DarkThemeConfig
import com.thejohnsondev.model.settings.GeneralSettings
import com.thejohnsondev.model.settings.PrivacySettings
import com.thejohnsondev.model.settings.SettingsConfig
import com.thejohnsondev.model.settings.ThemeBrand
import kotlinx.coroutines.flow.Flow

interface PreferencesDataStore {

    suspend fun getSettingsConfigFlow(): Flow<SettingsConfig>
    suspend fun getAuthToken(): String
    suspend fun saveAuthToken(token: String)
    suspend fun isUserLoggedIn(): Boolean
    suspend fun clearUserData()
    suspend fun saveKey(key: ByteArray)
    suspend fun getKey(): ByteArray
    suspend fun saveEmail(email: String)
    suspend fun getEmail(): String
    suspend fun setCustomTheme(theme: ThemeBrand)
    suspend fun setUseDynamicColor(useDynamicColor: Boolean)
    suspend fun setDarkThemeConfig(config: DarkThemeConfig)
    suspend fun setGeneralSettings(generalSettings: GeneralSettings)
    suspend fun setPrivacySettings(privacySettings: PrivacySettings)

}