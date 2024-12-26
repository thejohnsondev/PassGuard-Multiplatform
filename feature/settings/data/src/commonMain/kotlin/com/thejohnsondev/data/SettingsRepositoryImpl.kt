package com.thejohnsondev.data

import com.thejohnsondev.datastore.PreferencesDataStore
import com.thejohnsondev.model.settings.DarkThemeConfig
import com.thejohnsondev.model.settings.GeneralSettings
import com.thejohnsondev.model.settings.PrivacySettings
import com.thejohnsondev.model.settings.ThemeBrand

class SettingsRepositoryImpl(
    private val preferencesDataStore: PreferencesDataStore
): SettingsRepository {
    override suspend fun updateCustomTheme(theme: ThemeBrand) {
        preferencesDataStore.setCustomTheme(theme)
    }

    override suspend fun updateUseDynamicColor(useDynamicColor: Boolean) {
        preferencesDataStore.setUseDynamicColor(useDynamicColor)
    }

    override suspend fun updateDarkThemeConfig(config: DarkThemeConfig) {
        preferencesDataStore.setDarkThemeConfig(config)
    }

    override suspend fun updateGeneralSettings(generalSettings: GeneralSettings) {
        preferencesDataStore.setGeneralSettings(generalSettings)
    }

    override suspend fun updatePrivacySettings(privacySettings: PrivacySettings) {
        preferencesDataStore.setPrivacySettings(privacySettings)
    }

    override suspend fun getUserEmail(): String {
        return preferencesDataStore.getEmail()
    }
}