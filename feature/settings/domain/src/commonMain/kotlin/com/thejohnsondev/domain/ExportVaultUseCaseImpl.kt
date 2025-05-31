package com.thejohnsondev.domain

import com.thejohnsondev.data.ExportImportRepository
import com.thejohnsondev.domain.export.CSVExportUtils
import com.thejohnsondev.model.vault.PasswordDto
import com.thejohnsondev.platform.filemanager.ExportResult

class ExportVaultUseCaseImpl(
    private val exportImportRepository: ExportImportRepository,
) : ExportVaultUseCase {
    override suspend fun exportVault(
        csvContent: String,
        onCompletion: (ExportResult) -> Unit
    ) {
        try {
            exportImportRepository.exportPasswordsToCSV(csvContent, onCompletion)
        } catch (e: Exception) {
            ExportResult(
                success = false,
                message = e.message ?: "Unknown error",
            )
        }
    }
}