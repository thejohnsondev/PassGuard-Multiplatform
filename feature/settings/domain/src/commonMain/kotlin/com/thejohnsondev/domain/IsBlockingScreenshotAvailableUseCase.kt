package com.thejohnsondev.domain

import com.thejohnsondev.domain.repo.SettingsRepository

class IsBlockingScreenshotAvailableUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(): Boolean {
        return settingsRepository.getIsBlockScreenshotAvailable()
    }
}