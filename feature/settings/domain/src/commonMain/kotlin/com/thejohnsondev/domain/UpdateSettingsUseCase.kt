package com.thejohnsondev.domain

import com.thejohnsondev.domain.repo.SettingsRepository
import com.thejohnsondev.model.settings.DarkThemeConfig
import com.thejohnsondev.model.settings.GeneralSettings
import com.thejohnsondev.model.settings.PrivacySettings
import com.thejohnsondev.model.settings.ThemeBrand

class UpdateSettingsUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(
        themeBrand: ThemeBrand? = null,
        useDynamicColor: Boolean? = null,
        darkThemeConfig: DarkThemeConfig? = null,
        generalSettings: GeneralSettings? = null,
        privacySettings: PrivacySettings? = null
    ) {
        themeBrand?.let { settingsRepository.updateCustomTheme(it) }
        useDynamicColor?.let { settingsRepository.updateUseDynamicColor(it) }
        darkThemeConfig?.let { settingsRepository.updateDarkThemeConfig(it) }
        generalSettings?.let { settingsRepository.updateGeneralSettings(it) }
        privacySettings?.let { settingsRepository.updatePrivacySettings(it) }
    }
}