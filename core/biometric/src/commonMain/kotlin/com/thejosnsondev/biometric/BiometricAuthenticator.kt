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


sealed class BiometricAuthResult {
    data object Success : BiometricAuthResult()
    data object Failed : BiometricAuthResult()
    data class Error(val code: Int, val message: String) : BiometricAuthResult()
}

expect class BiometricAuthenticator {

    fun getBiometricAvailability(): BiometricAvailability

    suspend fun authenticate(
        promptTitle: String,
        promptSubtitle: String? = null,
        promptDescription: String? = null
    ): BiometricAuthResult
}