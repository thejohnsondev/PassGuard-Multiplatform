package com.thejohnsondev.domain

import com.thejohnsondev.model.settings.SettingsConfig
import kotlinx.coroutines.flow.Flow

interface GetSettingsFlowUseCase {
    suspend fun getSettingsConfigFlow(): Flow<SettingsConfig>
}