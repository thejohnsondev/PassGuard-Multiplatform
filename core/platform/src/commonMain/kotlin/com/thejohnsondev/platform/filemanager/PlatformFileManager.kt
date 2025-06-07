package com.thejohnsondev.platform.filemanager

interface PlatformFileManager {
    fun downloadCSVWithContent(content: String, fileName: String, onCompletion: (ExportResult) -> Unit)
    fun importCSV(onCompletion: (ImportResult) -> Unit)
}