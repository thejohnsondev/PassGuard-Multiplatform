package com.thejohnsondev.platform.filemanager

import android.os.Environment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class AndroidPlatformFileManager: PlatformFileManager {

    override fun downloadCSVWithContent(content: String, fileName: String, onCompletion: (ExportResult) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = try {
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                if (!downloadsDir.exists()) {
                    downloadsDir.mkdirs()
                }
                val file = File(downloadsDir, fileName)
                file.writeText(content)
                ExportResult(ExportStatus.SUCCESS, "File saved to Downloads: ${file.absolutePath}")
            } catch (e: Exception) {
                e.printStackTrace()
                ExportResult(ExportStatus.FAILURE, "Export failed: ${e.message}")
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