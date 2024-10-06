package com.thejohnsondev.common.utils

expect class BiometricsProvider {
    fun isBiometricsAvailable(): Boolean
    fun showBiometricsPrompt(
        context: Any?,
        title: String,
        subtitle: String,
        description: String,
        onBiometricSuccess: () -> Unit,
        onBiometricError: (String) -> Unit
    )
}