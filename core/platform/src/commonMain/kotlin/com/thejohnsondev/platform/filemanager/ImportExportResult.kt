package com.thejohnsondev.platform.filemanager

enum class FileActionStatus {
    SUCCESS,
    FAILURE,
    CANCELED
}

data class ExportResult(
    val status: FileActionStatus,
    val message: String
)

data class ImportResult(
    val status: FileActionStatus,
    val message: String,
    val fileContent: String? = null
)