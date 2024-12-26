package com.thejohnsondev.model.settings

data class PrivacySettings(
    val isUnlockWithBiometricEnabled: Boolean = false,
    val isBlockScreenshotsEnabled: Boolean = false,
)
