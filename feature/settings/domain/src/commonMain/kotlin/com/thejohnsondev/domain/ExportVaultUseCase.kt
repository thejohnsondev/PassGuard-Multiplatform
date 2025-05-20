package com.thejohnsondev.domain

import com.thejohnsondev.platform.filemanager.ExportResult

interface ExportVaultUseCase {
    suspend fun exportVault(csvContent: String): ExportResult
}