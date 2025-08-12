package com.thejohnsondev.domain

import com.thejohnsondev.domain.repo.SettingsRepository

class GetUserEmailUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(): String {
        return settingsRepository.getUserEmail()
    }
}