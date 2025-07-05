package com.thejohnsondev.presentation.biometric

import androidx.compose.runtime.Composable

actual class BiometricPromptDescriptionProvider {
    @Composable
    actual fun getTitle(): String = "log in with biometrics"
    @Composable
    actual fun getSubtitle(): String? = null
    @Composable
    actual fun getDescription(): String = "Use your biometric credentials to log in securely."
}