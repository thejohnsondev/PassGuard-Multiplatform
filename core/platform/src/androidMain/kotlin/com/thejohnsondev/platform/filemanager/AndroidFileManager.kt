package com.thejohnsondev.platform.filemanager

import android.content.Context
import android.os.Environment
import java.io.File

class AndroidFileManager(
    private val context: Context
): FileManager {
    override fun downloadCSVWithContent(content: String): ExportResult {
        return try {
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDir, EXPORT_FILE_NAME)
            file.writeText(content)
            ExportResult(true, "File saved to Downloads: ${file.absolutePath}")
        } catch (e: Exception) {
            ExportResult(false, "Export failed: ${e.message}")
        }
    }
}