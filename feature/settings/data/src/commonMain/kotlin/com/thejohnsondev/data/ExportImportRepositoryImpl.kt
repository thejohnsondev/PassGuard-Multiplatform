package com.thejohnsondev.data

import com.thejohnsondev.common.utils.EXPORT_FILE_TIME_FORMAT
import com.thejohnsondev.common.utils.getCurrentTimeStamp
import com.thejohnsondev.common.utils.parseTime
import com.thejohnsondev.platform.filemanager.ExportResult
import com.thejohnsondev.platform.filemanager.PlatformFileManager

const val EXPORT_FILE_NAME = "passguard_passwords"
const val EXPORT_FILE_EXTENSION = ".csv"

private fun generateExportFileName(): String {
    return "$EXPORT_FILE_NAME${getCurrentTimeStamp().parseTime(EXPORT_FILE_TIME_FORMAT)}$EXPORT_FILE_EXTENSION"
}

class ExportImportRepositoryImpl(
    private val platformFileManager: PlatformFileManager
) : ExportImportRepository {
    override fun exportPasswordsToCSV(content: String, onCompletion: (ExportResult) -> Unit) {
        return platformFileManager.downloadCSVWithContent(content, generateExportFileName(), onCompletion)
    }
}