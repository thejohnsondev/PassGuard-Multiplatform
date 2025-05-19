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
        val csvGenerationResult = CSVExportUtils.generateCsvContentForPasswords(decryptedPasswords)
        // TODO add showing not exported passwords
        if (!csvGenerationResult.isSuccessful) {
            return ExportResult(
                success = false,
                message = "Export failed: ${decryptedPasswords.size} passwords not exported: ${csvGenerationResult.message}",
            )
        }
        return exportImportRepository.exportPasswordsToCSV(csvGenerationResult.csvContent)
    }
}