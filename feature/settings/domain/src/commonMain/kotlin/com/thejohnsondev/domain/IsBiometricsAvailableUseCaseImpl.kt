package com.thejohnsondev.domain

import com.thejohnsondev.data.SettingsRepository

class IsBiometricsAvailableUseCaseImpl(
    private val settingsRepository: SettingsRepository
) : IsBiometricsAvailableUseCase {
    override suspend fun invoke(): Boolean {
        return settingsRepository.getIsBiometricsAvailable()
    }
}