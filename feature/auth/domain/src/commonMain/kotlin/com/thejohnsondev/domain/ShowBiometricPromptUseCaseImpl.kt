package com.thejohnsondev.domain

import com.thejohnsondev.data.AuthRepository
import com.thejosnsondev.biometric.BiometricAuthResult

class ShowBiometricPromptUseCaseImpl(
    private val authRepository: AuthRepository
): ShowBiometricPromptUseCase {
    override suspend fun invoke(
        promptTitle: String,
        promptSubtitle: String?,
        promptDescription: String?
    ): BiometricAuthResult {
        return authRepository.showBiometricPrompt(
            promptTitle = promptTitle,
            promptSubtitle = promptSubtitle,
            promptDescription = promptDescription
        )
    }
}