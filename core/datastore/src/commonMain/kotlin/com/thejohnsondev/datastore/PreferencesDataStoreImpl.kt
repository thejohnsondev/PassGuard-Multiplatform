package com.thejohnsondev.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.thejohnsondev.model.settings.DarkThemeConfig
import com.thejohnsondev.model.settings.GeneralSettings
import com.thejohnsondev.model.settings.PrivacySettings
import com.thejohnsondev.model.settings.ThemeBrand
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

    override suspend fun setCustomTheme(theme: ThemeBrand) {
        dataStore.saveInt(THEME_BRAND, theme.ordinal)
    }

    override suspend fun getCustomTheme(): ThemeBrand {
        val theme = dataStore.getInt(THEME_BRAND, ThemeBrand.DEFAULT.ordinal)
        return when (theme) {
            ThemeBrand.DEFAULT.ordinal -> ThemeBrand.DEFAULT
            ThemeBrand.ANDROID.ordinal -> ThemeBrand.ANDROID
            else -> ThemeBrand.DEFAULT
        }
    }

    override suspend fun setUseDynamicColor(useDynamicColor: Boolean) {
        dataStore.saveBoolean(USE_DYNAMIC_COLOR, useDynamicColor)
    }

    override suspend fun getUseDynamicColor(): Boolean {
        return dataStore.getBoolean(USE_DYNAMIC_COLOR)
    }

    override suspend fun setDarkThemeConfig(config: DarkThemeConfig) {
        dataStore.saveInt(DARK_THEME_CONFIG, config.ordinal)
    }

    override suspend fun getDarkThemeConfig(): DarkThemeConfig {
        val darkThemeConfig = dataStore.getInt(DARK_THEME_CONFIG, DarkThemeConfig.SYSTEM.ordinal)
        return when (darkThemeConfig) {
            DarkThemeConfig.SYSTEM.ordinal -> DarkThemeConfig.SYSTEM
            DarkThemeConfig.LIGHT.ordinal -> DarkThemeConfig.LIGHT
            DarkThemeConfig.DARK.ordinal -> DarkThemeConfig.DARK
            else -> DarkThemeConfig.SYSTEM
        }
    }

    override suspend fun setGeneralSettings(generalSettings: GeneralSettings) {
        dataStore.saveBoolean(USE_DEEP_SEARCH, generalSettings.isDeepSearchEnabled)
    }

    override suspend fun getGeneralSettings(): GeneralSettings {
        val isUseDeepSearch = dataStore.getBoolean(USE_DEEP_SEARCH)
        return GeneralSettings(isDeepSearchEnabled = isUseDeepSearch)
    }

    override suspend fun setPrivacySettings(privacySettings: PrivacySettings) {
        dataStore.saveBoolean(UNLOCK_WITH_BIOMETRICS, privacySettings.isUnlockWithBiometricEnabled)
        dataStore.saveBoolean(BLOCK_SCREENSHOTS, privacySettings.isBlockScreenshotsEnabled)
    }

    override suspend fun getPrivacySettings(): PrivacySettings {
        val isUnlockWithBiometrics = dataStore.getBoolean(UNLOCK_WITH_BIOMETRICS)
        val isBlockScreenshots = dataStore.getBoolean(BLOCK_SCREENSHOTS)
        return PrivacySettings(
            isUnlockWithBiometricEnabled = isUnlockWithBiometrics,
            isBlockScreenshotsEnabled = isBlockScreenshots
        )
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