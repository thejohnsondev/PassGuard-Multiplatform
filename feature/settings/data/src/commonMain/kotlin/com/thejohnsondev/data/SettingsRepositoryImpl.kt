package com.thejohnsondev.data

import com.thejohnsondev.datastore.PreferencesDataStore
import com.thejohnsondev.domain.repo.SettingsRepository
import com.thejohnsondev.model.settings.DarkThemeConfig
import com.thejohnsondev.model.settings.GeneralSettings
import com.thejohnsondev.model.settings.PrivacySettings
import com.thejohnsondev.model.settings.SettingsConfig
import com.thejohnsondev.model.settings.ThemeBrand
import com.thejohnsondev.ui.designsystem.DeviceThemeConfig
import com.thejosnsondev.biometric.BiometricAuthenticator
import com.thejosnsondev.biometric.BiometricAvailability
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val preferencesDataStore: PreferencesDataStore,
    private val biometricAuthenticator: BiometricAuthenticator,
    private val deviceThemeConfig: DeviceThemeConfig
): SettingsRepository {
    override suspend fun getSettingsConfigFlow(): Flow<SettingsConfig> {
        return preferencesDataStore.getSettingsConfigFlow()
    }

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

    override suspend fun getUserEmail(): String? {
        return preferencesDataStore.getEmail()
    }

    override suspend fun getIsBiometricsAvailability(): BiometricAvailability {
        return biometricAuthenticator.getBiometricAvailability()
    }

    override suspend fun getIsDynamicThemeAvailable(): Boolean {
        return deviceThemeConfig.supportsDynamicTheming()
    }

    override suspend fun getIsBlockScreenshotAvailable(): Boolean {
        return deviceThemeConfig.supportsBlockingScreenshots()
    }
}