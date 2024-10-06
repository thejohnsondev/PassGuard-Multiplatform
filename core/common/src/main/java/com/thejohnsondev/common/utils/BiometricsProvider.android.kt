package com.thejohnsondev.common.utils

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.thejohnsondev.common.UNEXPECTED_ERROR_HAPPENED_CODE

actual class BiometricsProvider(
    private val appContext: Context
) {
    actual fun isBiometricsAvailable(): Boolean {
        val biometricManager = BiometricManager.from(appContext)
        return when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> false
        }
    }

    actual fun showBiometricsPrompt(
        context: Any?,
        title: String,
        subtitle: String,
        description: String,
        onBiometricSuccess: () -> Unit,
        onBiometricError: (String) -> Unit
    ) {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setDescription(description)
            .setConfirmationRequired(false)
            .setNegativeButtonText("Cancel")
            .build()

        val executor = ContextCompat.getMainExecutor(appContext)
        (context as? FragmentActivity)?.let {
            val biometricPrompt = BiometricPrompt(
                it,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        onBiometricSuccess()
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        onBiometricError.invoke(UNEXPECTED_ERROR_HAPPENED_CODE + errorCode)
                    }
                }
            )
            biometricPrompt.authenticate(promptInfo)
        }

    }
}