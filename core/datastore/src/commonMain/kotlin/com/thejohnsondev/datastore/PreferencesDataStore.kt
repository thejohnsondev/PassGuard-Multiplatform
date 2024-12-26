package com.thejohnsondev.datastore

import com.thejohnsondev.model.settings.DarkThemeConfig
import com.thejohnsondev.model.settings.GeneralSettings
import com.thejohnsondev.model.settings.PrivacySettings
import com.thejohnsondev.model.settings.ThemeBrand

interface PreferencesDataStore {

    suspend fun getAuthToken(): String
    suspend fun saveAuthToken(token: String)
    suspend fun isUserLoggedIn(): Boolean
    suspend fun clearUserData()
    suspend fun saveKey(key: ByteArray)
    suspend fun getKey(): ByteArray
    suspend fun saveEmail(email: String)
    suspend fun getEmail(): String

    suspend fun setCustomTheme(theme: ThemeBrand)
    suspend fun getCustomTheme(): ThemeBrand

    suspend fun setUseDynamicColor(useDynamicColor: Boolean)
    suspend fun getUseDynamicColor(): Boolean

    suspend fun setDarkThemeConfig(config: DarkThemeConfig)
    suspend fun getDarkThemeConfig(): DarkThemeConfig

    suspend fun setGeneralSettings(generalSettings: GeneralSettings)
    suspend fun getGeneralSettings(): GeneralSettings

    suspend fun setPrivacySettings(privacySettings: PrivacySettings)
    suspend fun getPrivacySettings(): PrivacySettings

}