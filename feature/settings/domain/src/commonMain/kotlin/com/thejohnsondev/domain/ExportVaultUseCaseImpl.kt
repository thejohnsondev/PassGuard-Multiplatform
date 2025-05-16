package com.thejohnsondev.domain

import com.thejohnsondev.data.ExportImportRepository
import com.thejohnsondev.domain.export.CSVExportUtils
import com.thejohnsondev.model.vault.PasswordDto
import com.thejohnsondev.platform.filemanager.ExportResult

class ExportVaultUseCaseImpl(
    private val exportImportRepository: ExportImportRepository,
) : ExportVaultUseCase {
    override suspend fun exportVault(
        decryptedPasswords: List<PasswordDto>,
    ): ExportResult {
        val csvContent = CSVExportUtils.generateCsvContentForPasswords(decryptedPasswords)
        return exportImportRepository.exportPasswordsToCSV(csvContent)
    }
}