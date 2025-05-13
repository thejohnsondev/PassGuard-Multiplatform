package com.thejohnsondev.platform.filemanager

const val EXPORT_FILE_NAME = "passguard_passwords.csv"

interface FileManager {
    fun downloadCSVWithContent(content: String): ExportResult
}