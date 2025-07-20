package com.thejohnsondev.domain

import com.thejohnsondev.data.ExportImportRepository
import com.thejohnsondev.platform.filemanager.ImportResult

class SelectCSVUseCase(
    private val exportImportRepository: ExportImportRepository,
) {
    operator fun invoke(
        onCompletion: (ImportResult) -> Unit
    ) {
        exportImportRepository.importCSV(onCompletion)
    }
}
