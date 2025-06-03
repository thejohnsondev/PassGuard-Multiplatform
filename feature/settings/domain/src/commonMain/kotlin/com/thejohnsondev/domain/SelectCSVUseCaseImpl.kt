package com.thejohnsondev.domain

import com.thejohnsondev.data.ExportImportRepository
import com.thejohnsondev.platform.filemanager.ImportResult

class SelectCSVUseCaseImpl(
    private val exportImportRepository: ExportImportRepository,
): SelectCSVUseCase {
    override suspend fun invoke(
        onCompletion: (ImportResult) -> Unit
    ) {
        exportImportRepository.importCSV(onCompletion)
    }
}
