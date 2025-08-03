package com.thejohnsondev.domain

import com.thejohnsondev.data.SettingsRepository

class IsDynamicThemeAvailableUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(): Boolean {
        return settingsRepository.getIsDynamicThemeAvailable()
    }
}