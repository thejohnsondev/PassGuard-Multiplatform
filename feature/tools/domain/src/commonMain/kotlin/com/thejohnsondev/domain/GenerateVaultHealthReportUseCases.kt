package com.thejohnsondev.domain

import com.thejohnsondev.domain.vaulthealth.VaultHealthReport
import com.thejohnsondev.domain.vaulthealth.VaultHealthUtils
import com.thejohnsondev.model.vault.PasswordDto

class GenerateVaultHealthReportUseCases(
    private val vaultHealthUtils: VaultHealthUtils
) {
    operator fun invoke(
        passwords: List<PasswordDto>,
        passwordAgeThresholdDays: Int,
    ): VaultHealthReport {
        return vaultHealthUtils.generateReport(
            passwords = passwords,
            passwordAgeThresholdDays = passwordAgeThresholdDays
        )
    }
}