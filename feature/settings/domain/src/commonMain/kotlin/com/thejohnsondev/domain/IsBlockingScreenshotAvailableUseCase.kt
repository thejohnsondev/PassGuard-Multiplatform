package com.thejohnsondev.domain

import com.thejohnsondev.data.SettingsRepository

class IsBlockingScreenshotAvailableUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(): Boolean {
        return settingsRepository.getIsBlockScreenshotAvailable()
    }
}