package com.thejohnsondev.domain

import com.thejohnsondev.data.SettingsRepository

class GetUserEmailUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(): String {
        return settingsRepository.getUserEmail()
    }
}