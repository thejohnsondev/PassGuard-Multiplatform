package com.thejohnsondev.domain

import com.thejohnsondev.data.SettingsRepository
import com.thejosnsondev.biometric.BiometricAvailability

class GetBiometricAvailabilityUseCaseImpl(
    private val settingsRepository: SettingsRepository
) : GetBiometricAvailabilityUseCase {
    override suspend fun invoke(): BiometricAvailability {
        return settingsRepository.getIsBiometricsAvailability()
    }
}