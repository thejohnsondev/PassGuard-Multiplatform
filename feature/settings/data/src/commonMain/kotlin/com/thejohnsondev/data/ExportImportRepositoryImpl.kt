package com.thejohnsondev.data

import com.thejohnsondev.platform.filemanager.ExportResult
import com.thejohnsondev.platform.filemanager.FileManager

class ExportImportRepositoryImpl(
    private val fileManager: FileManager
): ExportImportRepository {
    override fun exportPasswordsToCSV(content: String): ExportResult {
        return fileManager.downloadCSVWithContent(content)
    }
}