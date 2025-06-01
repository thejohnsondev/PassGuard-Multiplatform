package com.thejohnsondev.data

import com.thejohnsondev.platform.filemanager.ExportResult
import com.thejohnsondev.platform.filemanager.ImportResult

interface ExportImportRepository {
    fun exportPasswordsToCSV(content: String, onCompletion: (ExportResult) -> Unit)
    fun importCSV(onCompletion: (ImportResult) -> Unit)
}