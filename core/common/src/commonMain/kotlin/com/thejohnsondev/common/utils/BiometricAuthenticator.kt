package com.thejohnsondev.common.utils

// Legacy, TBR
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

enum class BiometricType {
    NONE,
    FINGERPRINT,
    FACE,
    TOUCH_ID,
    FACE_ID,
    WINDOWS_HELLO,
    DEVICE_PASSWORD_FALLBACK
}

sealed class BiometricAvailability {
    data class Available(val type: BiometricType) : BiometricAvailability()
    object NotEnrolled : BiometricAvailability()
    object HardwareUnavailable : BiometricAvailability()
    object FeatureNotSupported : BiometricAvailability()
    object Unknown : BiometricAvailability()
}

/**
 * Represents the result of a biometric authentication attempt.
 */
sealed class BiometricAuthResult {
    object Success : BiometricAuthResult()
    object UserCancelled : BiometricAuthResult()
    object Failed : BiometricAuthResult()
    object LockedOut : BiometricAuthResult()
    data class Error(val code: Int, val message: String) : BiometricAuthResult()
    object NotAvailable : BiometricAuthResult()
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