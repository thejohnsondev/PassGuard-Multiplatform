package com.thejohnsondev.platform.filemanager

interface FileManager {
    fun downloadCSVWithContent(content: String, fileName: String): ExportResult
}