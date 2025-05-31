package com.thejohnsondev.platform.filemanager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class DesktopPlatformFileManager : PlatformFileManager {

    override fun downloadCSVWithContent(
        content: String,
        fileName: String,
        onCompletion: (ExportResult) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = try {
                val downloadsDir = File(System.getProperty("user.home"), "Downloads")
                if (!downloadsDir.exists()) {
                    downloadsDir.mkdirs()
                }
                val file = File(downloadsDir, fileName)
                file.writeText(content)
                ExportResult(true, "File saved to Downloads: ${file.absolutePath}")
            } catch (e: Exception) {
                ExportResult(false, "Export failed: ${e.message}")
            }
            withContext(Dispatchers.Main) {
                onCompletion(result)
            }
        }
    }

    override fun selectFile(): String? {
        TODO("Not yet implemented")
    }
}