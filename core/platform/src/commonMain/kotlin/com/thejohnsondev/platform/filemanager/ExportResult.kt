package com.thejohnsondev.platform.filemanager

enum class ExportStatus {
    SUCCESS,
    FAILURE,
    CANCELED
}

data class ExportResult(
    val status: ExportStatus,
    val message: String
)