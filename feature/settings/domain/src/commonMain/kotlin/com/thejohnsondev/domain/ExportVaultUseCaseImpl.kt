package com.thejohnsondev.domain

import com.thejohnsondev.data.ExportImportRepository
import com.thejohnsondev.platform.filemanager.ExportResult
import com.thejohnsondev.platform.filemanager.ExportStatus

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
                status = ExportStatus.FAILURE,
                message = e.message ?: "Unknown error",
            )
        }
    }
}