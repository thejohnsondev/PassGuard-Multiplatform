package com.thejohnsondev.domain

import com.thejohnsondev.data.SettingsRepository
import com.thejohnsondev.model.settings.SettingsConfig
import kotlinx.coroutines.flow.Flow

class GetSettingsFlowUseCaseImpl(
    private val settingsRepository: SettingsRepository
): GetSettingsFlowUseCase {
    override suspend fun getSettingsConfigFlow(): Flow<SettingsConfig> {
        return settingsRepository.getSettingsConfigFlow()
    }
}