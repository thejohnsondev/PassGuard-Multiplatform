package com.thejohnsondev.platform.filemanager

interface PlatformFileManager {
    fun downloadCSVWithContent(content: String, fileName: String, onCompletion: (ExportResult) -> Unit)
    fun selectFile(): String? // TODO use platform specific file picker
}