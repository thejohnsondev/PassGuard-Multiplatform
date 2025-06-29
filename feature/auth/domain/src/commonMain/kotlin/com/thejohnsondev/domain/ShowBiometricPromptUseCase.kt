package com.thejohnsondev.domain

import com.thejosnsondev.biometric.BiometricAuthResult

interface ShowBiometricPromptUseCase {
    suspend operator fun invoke(
        promptTitle: String,
        promptSubtitle: String? = null,
        promptDescription: String? = null
    ): BiometricAuthResult
}