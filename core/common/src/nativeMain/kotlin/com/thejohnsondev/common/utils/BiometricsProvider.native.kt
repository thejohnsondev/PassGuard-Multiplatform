package com.thejohnsondev.common.utils

actual class BiometricsProvider {
    actual fun isBiometricsAvailable(): Boolean {
        return false // TODO implement for iOS
    }

    actual fun showBiometricsPrompt(
        context: Any?,
        title: String,
        subtitle: String,
        description: String,
        onBiometricSuccess: () -> Unit,
        onBiometricError: (String) -> Unit
    ) {
        // todo implement for iOS
    }
}