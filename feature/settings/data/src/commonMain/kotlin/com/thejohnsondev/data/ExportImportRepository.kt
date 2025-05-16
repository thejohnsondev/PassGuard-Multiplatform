package com.thejohnsondev.data

import com.thejohnsondev.platform.filemanager.ExportResult

interface ExportImportRepository {
    fun exportPasswordsToCSV(
        content: String
    ): ExportResult
}