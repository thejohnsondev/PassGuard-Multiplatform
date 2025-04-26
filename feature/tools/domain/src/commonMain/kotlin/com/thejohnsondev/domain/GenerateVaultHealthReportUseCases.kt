package com.thejohnsondev.domain

import com.thejohnsondev.domain.vaulthealth.VaultHealthReport
import com.thejohnsondev.model.vault.PasswordDto

interface GenerateVaultHealthReportUseCases {
    operator fun invoke(
        passwords: List<PasswordDto>,
        passwordAgeThresholdDays: Int = 180,
    ): VaultHealthReport
}