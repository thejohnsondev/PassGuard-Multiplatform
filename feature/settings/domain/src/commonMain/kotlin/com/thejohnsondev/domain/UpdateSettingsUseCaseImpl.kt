package com.thejohnsondev.domain

import com.thejohnsondev.data.SettingsRepository
import com.thejohnsondev.model.settings.DarkThemeConfig
import com.thejohnsondev.model.settings.GeneralSettings
import com.thejohnsondev.model.settings.PrivacySettings
import com.thejohnsondev.model.settings.ThemeBrand

class UpdateSettingsUseCaseImpl(
    private val settingsRepository: SettingsRepository
): UpdateSettingsUseCase {
    override suspend fun invoke(
        themeBrand: ThemeBrand?,
        useDynamicColor: Boolean?,
        darkThemeConfig: DarkThemeConfig?,
        generalSettings: GeneralSettings?,
        privacySettings: PrivacySettings?
    ) {
        themeBrand?.let { settingsRepository.updateCustomTheme(it) }
        useDynamicColor?.let { settingsRepository.updateUseDynamicColor(it) }
        darkThemeConfig?.let { settingsRepository.updateDarkThemeConfig(it) }
        generalSettings?.let { settingsRepository.updateGeneralSettings(it) }
        privacySettings?.let { settingsRepository.updatePrivacySettings(it) }
    }
}