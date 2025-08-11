package com.thejohnsondev.domain

import com.thejohnsondev.domain.repo.AuthRepository
import com.thejosnsondev.biometric.BiometricAvailability

class GetBiometricAvailabilityUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): BiometricAvailability {
        return authRepository.getBiometricAvailability()
    }
}