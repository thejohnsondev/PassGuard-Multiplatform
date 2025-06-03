package com.thejohnsondev.domain

import com.thejohnsondev.domain.export.CsvParsingResult

interface ParsePasswordsCSVUseCase {
    suspend operator fun invoke(
        csvContent: String
    ): CsvParsingResult
}