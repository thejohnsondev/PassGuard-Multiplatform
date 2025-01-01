package com.thejohnsondev.domain

import com.thejohnsondev.data.SettingsRepository

class GetUserEmailUseCaseImpl(
    private val settingsRepository: SettingsRepository
): GetUserEmailUseCase {
    override suspend fun invoke(): String {
        return settingsRepository.getUserEmail()
    }
}