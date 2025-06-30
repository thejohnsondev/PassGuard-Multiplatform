package com.thejosnsondev.biometric

import com.thejohnsondev.common.utils.Logger
import kotlinx.cinterop.ExperimentalForeignApi
import platform.LocalAuthentication.LABiometryTypeFaceID
import platform.LocalAuthentication.LABiometryTypeTouchID
import platform.LocalAuthentication.LAContext
import platform.LocalAuthentication.LAPolicyDeviceOwnerAuthentication
import platform.LocalAuthentication.LAPolicyDeviceOwnerAuthenticationWithBiometrics
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class BiometricAuthenticator {

    private val laContext = LAContext()

    @OptIn(ExperimentalForeignApi::class)
    actual fun getBiometricAvailability(): BiometricAvailability {
        val canEvaluateBiometrics = laContext.canEvaluatePolicy(
            LAPolicyDeviceOwnerAuthenticationWithBiometrics,
            null
        )

        val availablility =  if (canEvaluateBiometrics) {
            when (laContext.biometryType) {
                LABiometryTypeFaceID -> BiometricAvailability.Available(BiometricType.FACE)
                LABiometryTypeTouchID -> BiometricAvailability.Available(BiometricType.FINGERPRINT)
                else -> BiometricAvailability.Unavailable// Future biometry types
            }
        } else {
            BiometricAvailability.Unavailable
        }
        Logger.d("BiometricAuthenticator", "Biometric availability: $availablility")
        return availablility
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
    ): BiometricAuthResult = suspendCoroutine { continuation ->
        val policy = LAPolicyDeviceOwnerAuthentication // Use this for automatic passcode fallback

        laContext.evaluatePolicy(
            policy,
            promptDescription ?: promptTitle // Use description if available, otherwise title
        ) { success, error ->
            Logger.d("BiometricAuthenticator", "Authentication result: success=$success, error=$error")
            if (success) {
                continuation.resume(BiometricAuthResult.Success)
            } else {
                val result = BiometricAuthResult.Failed
                continuation.resume(result)
            }
        }
    }

}