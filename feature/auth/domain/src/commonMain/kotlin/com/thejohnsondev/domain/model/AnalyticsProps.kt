package com.thejohnsondev.domain.model

import com.thejohnsondev.model.settings.DarkThemeConfig
import com.thejohnsondev.model.vault.VaultType

data class AnalyticsProps(
    val installID: String?,
    val isVaultInitialized: Boolean,
    val vaultType: VaultType?,
    val darkThemeConfig: DarkThemeConfig
)