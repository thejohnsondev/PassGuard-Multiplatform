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