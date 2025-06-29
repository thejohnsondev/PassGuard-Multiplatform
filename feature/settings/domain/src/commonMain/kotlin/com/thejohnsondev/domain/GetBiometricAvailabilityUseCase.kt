package com.thejohnsondev.domain

import com.thejosnsondev.biometric.BiometricAvailability

interface GetBiometricAvailabilityUseCase {
    suspend operator fun invoke(): BiometricAvailability
}