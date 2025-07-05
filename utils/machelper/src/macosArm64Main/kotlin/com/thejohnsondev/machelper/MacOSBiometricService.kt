package com.thejohnsondev.machelper

import com.thejosnsondev.biometric.BiometricAuthResult
import com.thejosnsondev.biometric.BiometricAvailability
import com.thejosnsondev.biometric.BiometricType
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSError
import platform.LocalAuthentication.LABiometryTypeFaceID
import platform.LocalAuthentication.LABiometryTypeNone
import platform.LocalAuthentication.LABiometryTypeTouchID
import platform.LocalAuthentication.LAContext
import platform.LocalAuthentication.LAErrorAuthenticationFailed
import platform.LocalAuthentication.LAErrorBiometryLockout
import platform.LocalAuthentication.LAErrorBiometryNotAvailable
import platform.LocalAuthentication.LAErrorBiometryNotEnrolled
import platform.LocalAuthentication.LAErrorPasscodeNotSet
import platform.LocalAuthentication.LAErrorUserCancel
import platform.LocalAuthentication.LAErrorUserFallback
import platform.LocalAuthentication.LAPolicyDeviceOwnerAuthentication
import platform.LocalAuthentication.LAPolicyDeviceOwnerAuthenticationWithBiometrics
import kotlin.coroutines.resume

class MacOSBiometricService {
    @OptIn(ExperimentalForeignApi::class)
    fun getBiometricAvailability(): BiometricAvailability {
        val context = LAContext()

        return memScoped {

            val errorPtr = alloc<ObjCObjectVar<NSError?>>()

            val canEvaluate = context.canEvaluatePolicy(
                LAPolicyDeviceOwnerAuthenticationWithBiometrics,
                errorPtr.ptr
            )

            if (canEvaluate) {
                return BiometricAvailability.Available().apply {
                    type = when (context.biometryType) {
                        LABiometryTypeFaceID -> BiometricType.FACE
                        LABiometryTypeTouchID -> BiometricType.FINGERPRINT
                        LABiometryTypeNone -> BiometricType.NONE
                        else -> BiometricType.NONE
                    }
                }
            } else {

                val nsError: NSError? = errorPtr.value

                return if (nsError != null) {
                    when (nsError.code.toLong()) {
                        LAErrorBiometryNotEnrolled -> BiometricAvailability.Unavailable
                        LAErrorBiometryNotAvailable -> BiometricAvailability.Unavailable
                        LAErrorPasscodeNotSet -> BiometricAvailability.Unavailable
                        else -> {

                            println("LAContext.canEvaluatePolicy failed with code: ${nsError.code}, message: ${nsError.localizedDescription}")
                            BiometricAvailability.Unavailable
                        }
                    }
                } else {
                    BiometricAvailability.Unavailable
                }
            }
        }
    }

    suspend fun authenticate(
        promptTitle: String,
        promptSubtitle: String? = null,
        promptDescription: String? = null
    ): BiometricAuthResult = suspendCancellableCoroutine { continuation ->
        val context = LAContext()
        val reason = promptTitle

        val policy =
            LAPolicyDeviceOwnerAuthentication

        context.evaluatePolicy(policy, reason) { success, error ->
            if (success) {
                continuation.resume(BiometricAuthResult.Success)
            } else {
                val nsError = error as NSError
                val errorCode = nsError.code.toLong()
                val errorMessage = nsError.localizedDescription ?: "Unknown error"

                when (errorCode) {
                    LAErrorUserCancel -> continuation.resume(BiometricAuthResult.Failed)
                    LAErrorUserFallback -> continuation.resume(BiometricAuthResult.Failed)
                    LAErrorAuthenticationFailed -> continuation.resume(BiometricAuthResult.Failed)
                    LAErrorBiometryLockout -> continuation.resume(
                        BiometricAuthResult.Failed
                    )

                    LAErrorBiometryNotAvailable, LAErrorPasscodeNotSet, LAErrorBiometryNotEnrolled -> continuation.resume(
                        BiometricAuthResult.Failed
                    )

                    else -> continuation.resume(
                        BiometricAuthResult.Error(
                            errorCode.toInt(),
                            errorMessage
                        )
                    )
                }
            }
        }
    }
}