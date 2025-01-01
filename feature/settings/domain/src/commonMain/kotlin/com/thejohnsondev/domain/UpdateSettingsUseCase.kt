package com.thejohnsondev.domain

import com.thejohnsondev.model.settings.DarkThemeConfig
import com.thejohnsondev.model.settings.GeneralSettings
import com.thejohnsondev.model.settings.PrivacySettings
import com.thejohnsondev.model.settings.ThemeBrand

interface UpdateSettingsUseCase {
    suspend operator fun invoke(
        themeBrand: ThemeBrand? = null,
        useDynamicColor: Boolean? = null,
        darkThemeConfig: DarkThemeConfig? = null,
        generalSettings: GeneralSettings? = null,
        privacySettings: PrivacySettings? = null
    )
}