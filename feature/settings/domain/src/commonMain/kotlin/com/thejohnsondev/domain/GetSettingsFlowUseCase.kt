package com.thejohnsondev.domain

import com.thejohnsondev.model.settings.SettingsConfig
import kotlinx.coroutines.flow.Flow

interface GetSettingsFlowUseCase {
    suspend operator fun invoke(): Flow<SettingsConfig>
}