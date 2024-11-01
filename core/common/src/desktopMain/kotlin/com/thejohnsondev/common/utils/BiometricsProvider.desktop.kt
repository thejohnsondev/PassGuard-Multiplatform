package com.thejohnsondev.common.utils

actual class BiometricsProvider {
    actual fun isBiometricsAvailable(): Boolean {
        return false
    }

    actual fun showBiometricsPrompt(
        context: Any?,
        title: String,
        subtitle: String,
        description: String,
        onBiometricSuccess: () -> Unit,
        onBiometricError: (String) -> Unit
    ) {
        onBiometricSuccess()
    }
}