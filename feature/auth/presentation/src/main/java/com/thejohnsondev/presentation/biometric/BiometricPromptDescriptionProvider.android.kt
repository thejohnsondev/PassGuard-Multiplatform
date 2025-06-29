package com.thejohnsondev.presentation.biometric

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import com.thejohnsondev.ui.utils.ResString
import vaultmultiplatform.core.ui.generated.resources.biometric_description_android
import vaultmultiplatform.core.ui.generated.resources.biometric_title_android

actual class BiometricPromptDescriptionProvider {
    @Composable
    actual fun getTitle(): String = stringResource(ResString.biometric_title_android)
    @Composable
    actual fun getSubtitle(): String? = null
    @Composable
    actual fun getDescription(): String = stringResource(ResString.biometric_description_android)
}