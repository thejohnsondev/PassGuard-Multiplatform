package com.thejosnsondev.biometric

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