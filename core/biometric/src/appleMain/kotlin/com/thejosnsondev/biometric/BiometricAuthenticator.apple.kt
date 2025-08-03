package com.thejosnsondev.biometric

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

        val availability =  if (canEvaluateBiometrics) {
            when (laContext.biometryType) {
                LABiometryTypeFaceID -> BiometricAvailability.Available(BiometricType.FACE)
                LABiometryTypeTouchID -> BiometricAvailability.Available(BiometricType.FINGERPRINT)
                else -> BiometricAvailability.Unavailable// Future biometry types
            }
        } else {
            BiometricAvailability.Unavailable
        }
        return availability
    }

    actual suspend fun authenticate(
        promptTitle: String,
        promptSubtitle: String?,
        promptDescription: String?
    ): BiometricAuthResult = suspendCoroutine { continuation ->
        val policy = LAPolicyDeviceOwnerAuthentication

        laContext.evaluatePolicy(
            policy,
            promptDescription ?: promptTitle
        ) { success, _ ->
            if (success) {
                continuation.resume(BiometricAuthResult.Success)
            } else {
                val result = BiometricAuthResult.Failed
                continuation.resume(result)
            }
        }
    }

}