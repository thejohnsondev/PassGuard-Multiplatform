package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.PasswordDto
import com.thejohnsondev.platform.filemanager.ExportResult

interface ExportVaultUseCase {
    suspend fun exportVault(decryptedPasswords: List<PasswordDto>,): ExportResult
}