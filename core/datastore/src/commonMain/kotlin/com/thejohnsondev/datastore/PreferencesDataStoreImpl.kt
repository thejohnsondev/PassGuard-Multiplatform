package com.thejohnsondev.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.thejohnsondev.common.empty
import com.thejohnsondev.common.utils.combine
import com.thejohnsondev.model.settings.DarkThemeConfig
import com.thejohnsondev.model.settings.GeneralSettings
import com.thejohnsondev.model.settings.PrivacySettings
import com.thejohnsondev.model.settings.SettingsConfig
import com.thejohnsondev.model.settings.ThemeBrand
import kotlinx.coroutines.flow.Flow
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class PreferencesDataStoreImpl(
    private val dataStore: DataStore<Preferences>
) : PreferencesDataStore {

    override suspend fun getSettingsConfigFlow(): Flow<SettingsConfig> = combine(
        dataStore.getIntFlow(THEME_BRAND, ThemeBrand.DEFAULT.ordinal),
        dataStore.getBooleanFlow(USE_DYNAMIC_COLOR, false),
        dataStore.getIntFlow(DARK_THEME_CONFIG, DarkThemeConfig.SYSTEM.ordinal),
        dataStore.getBooleanFlow(USE_DEEP_SEARCH, false),
        dataStore.getBooleanFlow(UNLOCK_WITH_BIOMETRICS, false),
        dataStore.getBooleanFlow(BLOCK_SCREENSHOTS, false),
        ::mergeSources
    )

    private fun mergeSources(
        themeBrand: Int,
        useDynamicColor: Boolean,
        darkThemeConfig: Int,
        isDeepSearchEnabled: Boolean,
        isUnlockWithBiometrics: Boolean,
        isBlockScreenshots: Boolean
    ): SettingsConfig {
        val themeBrandMapped = when (themeBrand) {
            ThemeBrand.DEFAULT.ordinal -> ThemeBrand.DEFAULT
            ThemeBrand.BLUE_SKY.ordinal -> ThemeBrand.BLUE_SKY
            ThemeBrand.GREEN_FOREST.ordinal -> ThemeBrand.GREEN_FOREST
            ThemeBrand.RED_ALGAE.ordinal -> ThemeBrand.RED_ALGAE
            ThemeBrand.SUNNY.ordinal -> ThemeBrand.SUNNY
            else -> ThemeBrand.DEFAULT
        }
        val darkThemeConfigMapped = when (darkThemeConfig) {
            DarkThemeConfig.SYSTEM.ordinal -> DarkThemeConfig.SYSTEM
            DarkThemeConfig.LIGHT.ordinal -> DarkThemeConfig.LIGHT
            DarkThemeConfig.DARK.ordinal -> DarkThemeConfig.DARK
            else -> DarkThemeConfig.SYSTEM
        }
        val generalSettings = GeneralSettings(isDeepSearchEnabled = isDeepSearchEnabled)
        val privacySettings = PrivacySettings(
            isUnlockWithBiometricEnabled = isUnlockWithBiometrics,
            isBlockScreenshotsEnabled = isBlockScreenshots
        )
        return SettingsConfig(
            customTheme = themeBrandMapped,
            useDynamicColor = useDynamicColor,
            darkThemeConfig = darkThemeConfigMapped,
            generalSettings = generalSettings,
            privacySettings = privacySettings
        )
    }

    override suspend fun getAuthToken(): String {
        return dataStore.getString(KEY_AUTH_TOKEN, String.Companion.empty)
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
        val keyString = dataStore.getString(KEY_KEY, String.Companion.empty)
        return Base64.decode(keyString)
    }

    override suspend fun saveEmail(email: String) {
        dataStore.saveString(KEY_EMAIL, email)
    }

    override suspend fun getEmail(): String {
        return dataStore.getString(KEY_EMAIL, String.Companion.empty)
    }

    override suspend fun setCustomTheme(theme: ThemeBrand) {
        dataStore.saveInt(THEME_BRAND, theme.ordinal)
    }

    override suspend fun setUseDynamicColor(useDynamicColor: Boolean) {
        dataStore.saveBoolean(USE_DYNAMIC_COLOR, useDynamicColor)
    }

    override suspend fun setDarkThemeConfig(config: DarkThemeConfig) {
        dataStore.saveInt(DARK_THEME_CONFIG, config.ordinal)
    }

    override suspend fun setGeneralSettings(generalSettings: GeneralSettings) {
        dataStore.saveBoolean(USE_DEEP_SEARCH, generalSettings.isDeepSearchEnabled)
    }

    override suspend fun setPrivacySettings(privacySettings: PrivacySettings) {
        dataStore.saveBoolean(UNLOCK_WITH_BIOMETRICS, privacySettings.isUnlockWithBiometricEnabled)
        dataStore.saveBoolean(BLOCK_SCREENSHOTS, privacySettings.isBlockScreenshotsEnabled)
    }

    companion object {
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_EMAIL = "email"
        private const val KEY_KEY = "key"
        private const val THEME_BRAND = "theme-brand"
        private const val USE_DYNAMIC_COLOR = "use-dynamic-color"
        private const val DARK_THEME_CONFIG = "dark-theme-config"
        private const val USE_DEEP_SEARCH = "use-deep-search"
        private const val UNLOCK_WITH_BIOMETRICS = "unlock-with-biometrics"
        private const val BLOCK_SCREENSHOTS = "block-screenshots"
    }

}