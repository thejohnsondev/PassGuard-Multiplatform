package com.thejosnsondev.biometric

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import com.thejohnsondev.model.DisplayableMessageValue
import com.thejohnsondev.ui.displaymessage.getAsText
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.Executors
import kotlin.coroutines.resume

actual class BiometricAuthenticator(
    private val context: Context
) {
    private val biometricManager = BiometricManager.from(context)

    actual fun getBiometricAvailability(): BiometricAvailability {
        val authenticators = BIOMETRIC_STRONG or BIOMETRIC_WEAK or DEVICE_CREDENTIAL

        return when (biometricManager.canAuthenticate(authenticators)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                val availableAuthenticators = biometricManager.canAuthenticate(BIOMETRIC_STRONG)
                if (availableAuthenticators == BiometricManager.BIOMETRIC_SUCCESS) {
                    BiometricAvailability.Available(BiometricType.FINGERPRINT)
                } else {
                    BiometricAvailability.Available(BiometricType.NONE)
                }
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> BiometricAvailability.HardwareUnavailable
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> BiometricAvailability.HardwareUnavailable
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> BiometricAvailability.NotEnrolled
            else -> BiometricAvailability.Unknown
        }
    }

    actual suspend fun authenticate(
        promptTitle: String,
        promptSubtitle: String?,
        promptDescription: String?
    ): BiometricAuthResult {
        val activity = context as? FragmentActivity
            ?: return BiometricAuthResult.Error(
                -1,
                "Authentication requires a FragmentActivity context."
            )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(promptTitle)
            .apply {
                promptSubtitle?.let { setSubtitle(it) }
                promptDescription?.let { setDescription(it) }
            }
            .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
            .setNegativeButtonText(DisplayableMessageValue.Cancel.getAsText())
            .build()

        return suspendCancellableCoroutine { continuation ->
            val biometricPrompt = BiometricPrompt(
                activity,
                Executors.newSingleThreadExecutor(),
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        if (continuation.isActive) {
                            val result = when (errorCode) {
                                BiometricPrompt.ERROR_USER_CANCELED -> BiometricAuthResult.UserCancelled
                                BiometricPrompt.ERROR_LOCKOUT, BiometricPrompt.ERROR_LOCKOUT_PERMANENT -> BiometricAuthResult.LockedOut
                                BiometricPrompt.ERROR_NO_BIOMETRICS, BiometricPrompt.ERROR_HW_UNAVAILABLE, BiometricPrompt.ERROR_HW_NOT_PRESENT -> BiometricAuthResult.NotAvailable
                                else -> BiometricAuthResult.Error(errorCode, errString.toString())
                            }
                            continuation.resume(result)
                        }
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        if (continuation.isActive) {
                            continuation.resume(BiometricAuthResult.Success)
                        }
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                    }
                }
            )
            biometricPrompt.authenticate(promptInfo)

            continuation.invokeOnCancellation {
                biometricPrompt.cancelAuthentication()
            }
        }
    }

}