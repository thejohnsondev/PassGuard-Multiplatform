package com.thejohnsondev.platform.filemanager

import java.io.File

class DesktopPlatformFileManager: PlatformFileManager {
    override fun downloadCSVWithContent(content: String, fileName: String): ExportResult {
        return try {
            val downloadsDir = File(System.getProperty("user.home"), "Downloads")
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