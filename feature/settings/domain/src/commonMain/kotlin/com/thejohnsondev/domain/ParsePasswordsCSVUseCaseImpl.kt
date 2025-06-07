package com.thejohnsondev.domain

import com.thejohnsondev.domain.export.CSVImportExportUtils
import com.thejohnsondev.domain.export.CsvParsingResult

class ParsePasswordsCSVUseCaseImpl: ParsePasswordsCSVUseCase {
    override suspend fun invoke(csvContent: String): CsvParsingResult {
        return CSVImportExportUtils.parseCsvContentToPasswords(csvContent)
    }
}