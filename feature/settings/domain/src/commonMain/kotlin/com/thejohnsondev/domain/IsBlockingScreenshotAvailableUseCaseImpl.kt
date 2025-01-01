package com.thejohnsondev.domain

import com.thejohnsondev.data.SettingsRepository

class IsBlockingScreenshotAvailableUseCaseImpl(
    private val settingsRepository: SettingsRepository
) : IsBlockingScreenshotAvailableUseCase {
    override suspend fun invoke(): Boolean {
        return settingsRepository.getIsBlockScreenshotAvailable()
    }
}