package com.thejohnsondev.datastore

import com.thejohnsondev.model.settings.DarkThemeConfig
import com.thejohnsondev.model.settings.GeneralSettings
import com.thejohnsondev.model.settings.PrivacySettings
import com.thejohnsondev.model.settings.SettingsConfig
import com.thejohnsondev.model.settings.ThemeBrand
import com.thejohnsondev.model.tools.PasswordGeneratorConfig
import kotlinx.coroutines.flow.Flow

interface PreferencesDataStore {

    suspend fun getSettingsConfigFlow(): Flow<SettingsConfig>
    suspend fun getAuthToken(): String
    suspend fun saveAuthToken(token: String)
    suspend fun getRefreshAuthToken(): String
    suspend fun saveRefreshAuthToken(token: String)
    suspend fun isVaultInitialized(): Boolean
    suspend fun isVaultLocal(): Boolean
    suspend fun clearUserData()
    suspend fun saveSecretKey(key: ByteArray)
    suspend fun getSecretKey(): ByteArray
    suspend fun saveEmail(email: String)
    suspend fun getEmail(): String
    suspend fun setCustomTheme(theme: ThemeBrand)
    suspend fun setUseDynamicColor(useDynamicColor: Boolean)
    suspend fun setDarkThemeConfig(config: DarkThemeConfig)
    suspend fun setGeneralSettings(generalSettings: GeneralSettings)
    suspend fun setPrivacySettings(privacySettings: PrivacySettings)
    suspend fun updateOpenedFilters(opened: Boolean)
    suspend fun getIsOpenedFilters(): Boolean
    suspend fun updateAppliedItemTypeFilters(itemTypeFilters: List<String>)
    suspend fun getAppliedItemTypeFilters(): List<String>
    suspend fun updateAppliedCategoryFilters(categoryFilters: List<String>)
    suspend fun getAppliedCategoryFilters(): List<String>
    suspend fun updateAppliedSortOrder(sortOrder: String)
    suspend fun getAppliedSortOrder(): String
    suspend fun updateAppliedShowFavoritesAtTop(showFavoritesAtTop: Boolean)
    suspend fun getAppliedShowFavoritesAtTop(): Boolean
    suspend fun updatePasswordGeneratorConfig(config: PasswordGeneratorConfig)
    suspend fun getPasswordGeneratorConfig(): PasswordGeneratorConfig
}