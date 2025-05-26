package com.thejohnsondev.platform.filemanager

interface PlatformFileManager {
    fun downloadCSVWithContent(content: String, fileName: String): ExportResult
}