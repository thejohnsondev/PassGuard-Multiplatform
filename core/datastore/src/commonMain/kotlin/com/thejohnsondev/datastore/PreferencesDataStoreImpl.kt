package com.thejohnsondev.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.thejohnsondev.common.SORT_TIME_NEW
import com.thejohnsondev.common.empty
import com.thejohnsondev.common.utils.combine
import com.thejohnsondev.model.settings.DarkThemeConfig
import com.thejohnsondev.model.settings.GeneralSettings
import com.thejohnsondev.model.settings.PrivacySettings
import com.thejohnsondev.model.settings.SettingsConfig
import com.thejohnsondev.model.settings.ThemeBrand
import com.thejohnsondev.model.tools.PASSWORD_GENERATOR_DEFAULT_LENGTH
import com.thejohnsondev.model.tools.PasswordGeneratorConfig
import com.thejohnsondev.model.tools.PasswordGenerationType
import com.thejohnsondev.platform.storage.SecureStorage
import kotlinx.coroutines.flow.Flow
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class PreferencesDataStoreImpl(
    private val dataStore: DataStore<Preferences>,
    private val secureStorage: SecureStorage
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
        isBlockScreenshots: Boolean,
    ): SettingsConfig {
        val themeBrandMapped = when (themeBrand) {
            ThemeBrand.DEFAULT.ordinal -> ThemeBrand.DEFAULT
            ThemeBrand.TEAL.ordinal -> ThemeBrand.TEAL
            ThemeBrand.DEEP_FOREST.ordinal -> ThemeBrand.DEEP_FOREST
            ThemeBrand.RED_ALGAE.ordinal -> ThemeBrand.RED_ALGAE
            ThemeBrand.SUNNY.ordinal -> ThemeBrand.SUNNY
            ThemeBrand.VIOLET.ordinal -> ThemeBrand.VIOLET
            ThemeBrand.MONOCHROME.ordinal -> ThemeBrand.MONOCHROME
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
        val token = secureStorage.read(KEY_AUTH_TOKEN).orEmpty()
        return token
    }

    override suspend fun saveAuthToken(token: String) {
        secureStorage.save(KEY_AUTH_TOKEN, token)
    }

    override suspend fun getRefreshAuthToken(): String {
        return secureStorage.read(KEY_REFRESH_AUTH_TOKEN).orEmpty()
    }

    override suspend fun saveRefreshAuthToken(token: String) {
        secureStorage.save(KEY_REFRESH_AUTH_TOKEN, token)
    }

    override suspend fun isVaultInitialized(): Boolean {
        return getSecretKey().isNotEmpty()
    }

    override suspend fun isVaultLocal(): Boolean {
        return getEmail().isEmpty()
    }

    override suspend fun clearUserData() {
        secureStorage.remove(KEY_AUTH_TOKEN)
        secureStorage.remove(KEY_SECRET_KEY)
        secureStorage.remove(KEY_REFRESH_AUTH_TOKEN)
        secureStorage.remove(KEY_EMAIL)
        dataStore.clearAll()
    }

    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun saveSecretKey(key: ByteArray) {
        secureStorage.save(KEY_SECRET_KEY, Base64.encode(key))
    }

    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun getSecretKey(): ByteArray {
        val keyString = secureStorage.read(KEY_SECRET_KEY).orEmpty()
        return Base64.decode(keyString)
    }

    override suspend fun saveEmail(email: String) {
        secureStorage.save(KEY_EMAIL, email)
    }

    override suspend fun getEmail(): String {
        return secureStorage.read(KEY_EMAIL).orEmpty()
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

    override suspend fun updateOpenedFilters(opened: Boolean) {
        dataStore.saveBoolean(KEY_OPENED_FILTERS, opened)
    }

    override suspend fun getIsOpenedFilters(): Boolean {
        return dataStore.getBoolean(KEY_OPENED_FILTERS, true)
    }

    override suspend fun updateAppliedItemTypeFilters(itemTypeFilters: List<String>) {
        dataStore.saveString(
            KEY_APPLIED_ITEM_TYPE_FILTERS, itemTypeFilters.joinToString(
                IDS_SEPARATOR
            )
        )
    }

    override suspend fun getAppliedItemTypeFilters(): List<String> {
        val itemTypeFilters =
            dataStore.getString(KEY_APPLIED_ITEM_TYPE_FILTERS, String.Companion.empty)
        return itemTypeFilters.split(IDS_SEPARATOR)
    }

    override suspend fun updateAppliedCategoryFilters(categoryFilters: List<String>) {
        dataStore.saveString(
            KEY_APPLIED_CATEGORY_FILTERS, categoryFilters.joinToString(
                IDS_SEPARATOR
            )
        )
    }

    override suspend fun getAppliedCategoryFilters(): List<String> {
        val categoryFilters =
            dataStore.getString(KEY_APPLIED_CATEGORY_FILTERS, String.Companion.empty)
        return categoryFilters.split(IDS_SEPARATOR)
    }

    override suspend fun updateAppliedSortOrder(sortOrder: String) {
        dataStore.saveString(KEY_APPLIED_SORT_ORDER, sortOrder)
    }

    override suspend fun getAppliedSortOrder(): String {
        return dataStore.getString(KEY_APPLIED_SORT_ORDER, SORT_TIME_NEW)
    }

    override suspend fun updateAppliedShowFavoritesAtTop(showFavoritesAtTop: Boolean) {
        dataStore.saveBoolean(KEY_APPLIED_FAVORITES_AT_TOP, showFavoritesAtTop)
    }

    override suspend fun getAppliedShowFavoritesAtTop(): Boolean {
        return dataStore.getBoolean(KEY_APPLIED_FAVORITES_AT_TOP, true)
    }

    override suspend fun updatePasswordGeneratorConfig(config: PasswordGeneratorConfig) {
        dataStore.saveInt(KEY_PASSWORD_GENERATOR_CONFIG_TYPE, config.type.ordinal)
        dataStore.saveInt(KEY_PASSWORD_GENERATOR_CONFIG_LENGTH, config.length)
        dataStore.saveBoolean(
            KEY_PASSWORD_GENERATOR_CONFIG_INCLUDE_LOWER,
            config.includeLower
        )
        dataStore.saveBoolean(
            KEY_PASSWORD_GENERATOR_CONFIG_INCLUDE_UPPER,
            config.includeUpper
        )
        dataStore.saveBoolean(
            KEY_PASSWORD_GENERATOR_CONFIG_INCLUDE_DIGITS,
            config.includeDigits
        )
        dataStore.saveBoolean(
            KEY_PASSWORD_GENERATOR_CONFIG_INCLUDE_SPECIAL,
            config.includeSpecial
        )
    }

    override suspend fun getPasswordGeneratorConfig(): PasswordGeneratorConfig {
        val type = dataStore.getInt(
            KEY_PASSWORD_GENERATOR_CONFIG_TYPE,
            PasswordGenerationType.RANDOM.ordinal
        )
        val length = dataStore.getInt(
            KEY_PASSWORD_GENERATOR_CONFIG_LENGTH,
            PASSWORD_GENERATOR_DEFAULT_LENGTH
        )
        val includeLower = dataStore.getBoolean(
            KEY_PASSWORD_GENERATOR_CONFIG_INCLUDE_LOWER,
            true
        )
        val includeUpper = dataStore.getBoolean(
            KEY_PASSWORD_GENERATOR_CONFIG_INCLUDE_UPPER,
            true
        )
        val includeDigits = dataStore.getBoolean(
            KEY_PASSWORD_GENERATOR_CONFIG_INCLUDE_DIGITS,
            true
        )
        val includeSpecial = dataStore.getBoolean(
            KEY_PASSWORD_GENERATOR_CONFIG_INCLUDE_SPECIAL,
            true
        )
        return PasswordGeneratorConfig(
            type = PasswordGenerationType.entries[type],
            length = length,
            includeLower = includeLower,
            includeUpper = includeUpper,
            includeDigits = includeDigits,
            includeSpecial = includeSpecial
        )
    }

    override suspend fun updateSelectedLanguage(language: String) {
        secureStorage.save(KEY_SELECTED_LANGUAGE, language)
    }

    override suspend fun getSelectedLanguage(): String {
        return secureStorage.read(KEY_SELECTED_LANGUAGE).orEmpty()
    }

    companion object {
        private const val IDS_SEPARATOR = ","
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_REFRESH_AUTH_TOKEN = "refresh_auth_token"
        private const val KEY_EMAIL = "email"
        private const val KEY_SECRET_KEY = "secret-key"
        private const val THEME_BRAND = "theme-brand"
        private const val USE_DYNAMIC_COLOR = "use-dynamic-color"
        private const val DARK_THEME_CONFIG = "dark-theme-config"
        private const val USE_DEEP_SEARCH = "use-deep-search"
        private const val UNLOCK_WITH_BIOMETRICS = "unlock-with-biometrics"
        private const val BLOCK_SCREENSHOTS = "block-screenshots"
        private const val KEY_OPENED_FILTERS = "opened-filters"
        private const val KEY_APPLIED_ITEM_TYPE_FILTERS = "applied-item-type-filters"
        private const val KEY_APPLIED_CATEGORY_FILTERS = "applied-category-filters"
        private const val KEY_APPLIED_SORT_ORDER = "applied-sort-order"
        private const val KEY_APPLIED_FAVORITES_AT_TOP = "applied-favorites-at-top"
        private const val KEY_PASSWORD_GENERATOR_CONFIG_TYPE = "password-generator-config-type"
        private const val KEY_PASSWORD_GENERATOR_CONFIG_LENGTH = "password-generator-config-length"
        private const val KEY_PASSWORD_GENERATOR_CONFIG_INCLUDE_LOWER = "password-generator-config-include-lower"
        private const val KEY_PASSWORD_GENERATOR_CONFIG_INCLUDE_UPPER = "password-generator-config-include-upper"
        private const val KEY_PASSWORD_GENERATOR_CONFIG_INCLUDE_DIGITS = "password-generator-config-include-digits"
        private const val KEY_PASSWORD_GENERATOR_CONFIG_INCLUDE_SPECIAL = "password-generator-config-include-special"
        private const val KEY_SELECTED_LANGUAGE = "selected-language"
    }

}