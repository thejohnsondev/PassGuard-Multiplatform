package com.thejohnsondev.platform.filemanager

import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AndroidPlatformFileManager(
    private val context: Context
) : PlatformFileManager {

    override fun downloadCSVWithContent(
        content: String,
        fileName: String,
        onCompletion: (ExportResult) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = try {
                val resolver = context.contentResolver
                val mimeType = "text/csv"

                val contentValues = ContentValues().apply {
                    put(MediaStore.Downloads.DISPLAY_NAME, fileName)
                    put(MediaStore.Downloads.MIME_TYPE, mimeType)
                    put(MediaStore.Downloads.IS_PENDING, 1)
                }

                val collection =
                    MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                val itemUri = resolver.insert(collection, contentValues)

                if (itemUri == null) throw Exception("Failed to create file entry")

                resolver.openOutputStream(itemUri)?.use { output ->
                    output.write(content.toByteArray())
                }

                contentValues.clear()
                contentValues.put(MediaStore.Downloads.IS_PENDING, 0)
                resolver.update(itemUri, contentValues, null, null)

                ExportResult(FileActionStatus.SUCCESS, "File saved to Downloads as $fileName")
            } catch (e: Exception) {
                e.printStackTrace()
                ExportResult(FileActionStatus.FAILURE, "Export failed: ${e.message}")
            }
            withContext(Dispatchers.Main) {
                onCompletion(result)
            }
        }
    }

    override fun importCSV(onCompletion: (ImportResult) -> Unit) {
        AndroidActivityProvider.launchFilePicker(onCompletion)
    }

}