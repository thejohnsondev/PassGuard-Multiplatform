package com.thejohnsondev.domain.repo

import com.thejohnsondev.model.settings.DarkThemeConfig
import com.thejohnsondev.model.settings.GeneralSettings
import com.thejohnsondev.model.settings.PrivacySettings
import com.thejohnsondev.model.settings.SettingsConfig
import com.thejohnsondev.model.settings.ThemeBrand
import com.thejosnsondev.biometric.BiometricAvailability
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    suspend fun getSettingsConfigFlow(): Flow<SettingsConfig>
    suspend fun updateCustomTheme(theme: ThemeBrand)
    suspend fun updateUseDynamicColor(useDynamicColor: Boolean)
    suspend fun updateDarkThemeConfig(config: DarkThemeConfig)
    suspend fun updateGeneralSettings(generalSettings: GeneralSettings)
    suspend fun updatePrivacySettings(privacySettings: PrivacySettings)
    suspend fun getUserEmail(): String
    suspend fun getIsBiometricsAvailability(): BiometricAvailability
    suspend fun getIsDynamicThemeAvailable(): Boolean
    suspend fun getIsBlockScreenshotAvailable(): Boolean

}