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

actual class BiometricAuthenticator {
    /**
     * Checks the current availability and type of biometric authentication.
     * @return A [BiometricAvailability] status.
     */
    actual fun getBiometricAvailability(): BiometricAvailability {
        TODO("Not yet implemented")
    }

    /**
     * Authenticates the user using available biometrics.
     * @param promptTitle The title displayed on the biometric prompt.
     * @param promptSubtitle The subtitle displayed on the biometric prompt (optional).
     * @param promptDescription The description displayed on the biometric prompt (optional).
     * @return A [BiometricAuthResult] indicating the outcome.
     */
    actual suspend fun authenticate(
        promptTitle: String,
        promptSubtitle: String?,
        promptDescription: String?
    ): BiometricAuthResult {
        TODO("Not yet implemented")
    }

}