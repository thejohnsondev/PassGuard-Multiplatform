package com.thejohnsondev.domain

import com.thejohnsondev.data.AuthRepository
import com.thejosnsondev.biometric.BiometricAvailability

class GetBiometricAvailabilityUseCaseImpl(
    private val authRepository: AuthRepository
) : GetBiometricAvailabilityUseCase {
    override suspend fun invoke(): BiometricAvailability {
        return authRepository.getBiometricAvailability()
    }
}