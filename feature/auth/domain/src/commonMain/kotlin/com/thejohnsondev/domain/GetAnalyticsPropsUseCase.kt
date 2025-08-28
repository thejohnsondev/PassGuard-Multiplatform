package com.thejohnsondev.domain

import com.thejohnsondev.common.getPlatform
import com.thejohnsondev.common.utils.BuildKonfigProvider
import com.thejohnsondev.domain.model.AnalyticsProps
import com.thejohnsondev.domain.repo.AuthRepository
import com.thejohnsondev.model.vault.VaultType

class GetAnalyticsPropsUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): AnalyticsProps {
        val installID = authRepository.getInstallId()
        val isVaultInitialized = authRepository.isVaultInitialized()
        val darkThemeConfig = authRepository.getDarkThemeConfig()
        val vaultType = if (authRepository.isVaultLocal()) VaultType.LOCAL else VaultType.CLOUD
        val appVersion = BuildKonfigProvider.getAppVersion()
        val platform = getPlatform().name
        return AnalyticsProps(
            installID = installID,
            isVaultInitialized = isVaultInitialized,
            vaultType = vaultType,
            darkThemeConfig = darkThemeConfig,
            appVersion = appVersion,
            platform = platform
        )
    }
}