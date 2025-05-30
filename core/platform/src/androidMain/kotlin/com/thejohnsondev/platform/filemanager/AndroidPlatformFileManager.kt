package com.thejohnsondev.platform.filemanager

import android.content.Context
import android.os.Environment
import java.io.File

class AndroidPlatformFileManager(
    private val context: Context
): PlatformFileManager {
    override fun downloadCSVWithContent(content: String, fileName: String): ExportResult {
        return try {
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDir, fileName)
            file.writeText(content)
            ExportResult(true, "File saved to Downloads: ${file.absolutePath}")
        } catch (e: Exception) {
            ExportResult(false, "Export failed: ${e.message}")
        }
    }

    override fun selectFile(): String? {
        TODO("Not yet implemented")
    }
}