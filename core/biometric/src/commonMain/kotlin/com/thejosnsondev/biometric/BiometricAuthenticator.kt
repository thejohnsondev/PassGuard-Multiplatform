package com.thejosnsondev.biometric

enum class BiometricType {
    NONE,
    FINGERPRINT,
    FACE,
    DEVICE_PASSWORD_FALLBACK
}

sealed class BiometricAvailability {
    data class Available(var type: BiometricType = BiometricType.NONE) : BiometricAvailability()
    data object Unavailable : BiometricAvailability()
}

/**
 * Represents the result of a biometric authentication attempt.
 */
sealed class BiometricAuthResult {
    data object Success : BiometricAuthResult()
    data object Failed : BiometricAuthResult()
    data class Error(val code: Int, val message: String) : BiometricAuthResult()
}

expect class BiometricAuthenticator {

    /**
     * Checks the current availability and type of biometric authentication.
     * @return A [BiometricAvailability] status.
     */
    fun getBiometricAvailability(): BiometricAvailability

    /**
     * Authenticates the user using available biometrics.
     * @param promptTitle The title displayed on the biometric prompt.
     * @param promptSubtitle The subtitle displayed on the biometric prompt (optional).
     * @param promptDescription The description displayed on the biometric prompt (optional).
     * @return A [BiometricAuthResult] indicating the outcome.
     */
    suspend fun authenticate(
        promptTitle: String,
        promptSubtitle: String? = null,
        promptDescription: String? = null
    ): BiometricAuthResult
}