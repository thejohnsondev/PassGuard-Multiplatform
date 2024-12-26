package com.thejohnsondev.model.settings

data class SettingsConfig(
    val customTheme: ThemeBrand,
    val useDynamicColor: Boolean,
    val darkThemeConfig: DarkThemeConfig,
    val generalSettings: GeneralSettings,
    val privacySettings: PrivacySettings
)