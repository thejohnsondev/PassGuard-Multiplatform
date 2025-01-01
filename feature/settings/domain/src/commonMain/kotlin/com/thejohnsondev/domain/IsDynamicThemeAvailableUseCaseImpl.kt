package com.thejohnsondev.domain

import com.thejohnsondev.data.SettingsRepository

class IsDynamicThemeAvailableUseCaseImpl(
    private val settingsRepository: SettingsRepository
) : IsDynamicThemeAvailableUseCase {
    override suspend operator fun invoke(): Boolean {
        return settingsRepository.getIsDynamicThemeAvailable()
    }
}