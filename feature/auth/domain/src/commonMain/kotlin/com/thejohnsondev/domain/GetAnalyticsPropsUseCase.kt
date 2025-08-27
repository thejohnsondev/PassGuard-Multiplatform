package com.thejohnsondev.domain

import com.thejohnsondev.domain.model.AnalyticsProps
import com.thejohnsondev.domain.repo.AuthRepository
import com.thejohnsondev.model.vault.VaultType

class GetAnalyticsPropsUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): AnalyticsProps {
        val installID = authRepository.getInstallId()
        val isVaultInitialized = authRepository.isVaultInitialized()
        val userEmail = authRepository.getUserEmail()
        val darkThemeConfig = authRepository.getDarkThemeConfig()
        val vaultType = authRepository.isVaultLocal()?.let { isVaultLocal ->
            if (isVaultLocal) VaultType.LOCAL else VaultType.CLOUD
        }
        return AnalyticsProps(
            installID = installID,
            isVaultInitialized = isVaultInitialized,
            userEmail = userEmail,
            vaultType = vaultType,
            darkThemeConfig = darkThemeConfig
        )
    }
}