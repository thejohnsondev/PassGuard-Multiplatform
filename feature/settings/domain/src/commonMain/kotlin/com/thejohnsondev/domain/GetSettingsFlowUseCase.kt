package com.thejohnsondev.domain

import com.thejohnsondev.domain.repo.SettingsRepository
import com.thejohnsondev.model.settings.SettingsConfig
import kotlinx.coroutines.flow.Flow

class GetSettingsFlowUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(): Flow<SettingsConfig> {
        return settingsRepository.getSettingsConfigFlow()
    }
}