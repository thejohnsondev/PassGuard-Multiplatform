package com.thejohnsondev.presentation.biometric

import androidx.compose.runtime.Composable
import com.thejohnsondev.ui.utils.ResString
import org.jetbrains.compose.resources.stringResource
import vaultmultiplatform.core.ui.generated.resources.biometric_prompt_description_android
import vaultmultiplatform.core.ui.generated.resources.biometric_prompt_title_android

actual class BiometricPromptDescriptionProvider {
    @Composable
    actual fun getTitle(): String = stringResource(ResString.biometric_prompt_title_android)

    @Composable
    actual fun getSubtitle(): String? = null

    @Composable
    actual fun getDescription(): String = stringResource(ResString.biometric_prompt_description_android)
}