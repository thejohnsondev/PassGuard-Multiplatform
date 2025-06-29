package com.thejohnsondev.presentation.biometric

import androidx.compose.runtime.Composable

expect class BiometricPromptDescriptionProvider() {
    @Composable
    fun getTitle(): String
    @Composable
    fun getSubtitle(): String?
    @Composable
    fun getDescription(): String
}