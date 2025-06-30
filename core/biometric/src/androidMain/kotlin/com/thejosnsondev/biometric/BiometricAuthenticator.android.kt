package com.thejosnsondev.biometric

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import com.thejohnsondev.platform.filemanager.AndroidActivityProvider
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.Executors
import kotlin.coroutines.resume

actual class BiometricAuthenticator {

    private val activityContext by lazy {
        AndroidActivityProvider.currentActivity?.get()
    }
    private val biometricManager by lazy {
        BiometricManager.from(activityContext!!)
    }

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

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE,
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE,
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> BiometricAvailability.Unavailable

            else -> BiometricAvailability.Unavailable
        }
    }

    actual suspend fun authenticate(
        promptTitle: String,
        promptSubtitle: String?,
        promptDescription: String?
    ): BiometricAuthResult {
        val activity = activityContext
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
            .build()

        return suspendCancellableCoroutine { continuation ->
            val biometricPrompt = BiometricPrompt(
                activity,
                Executors.newSingleThreadExecutor(),
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        if (continuation.isActive) {
                            val result = BiometricAuthResult.Error(errorCode, errString.toString())
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