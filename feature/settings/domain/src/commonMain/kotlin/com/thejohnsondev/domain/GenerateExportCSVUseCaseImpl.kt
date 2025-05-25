package com.thejohnsondev.domain

import com.thejohnsondev.domain.export.CSVExportUtils
import com.thejohnsondev.domain.export.CSVGenerationResult
import com.thejohnsondev.model.vault.PasswordDto

class GenerateExportCSVUseCaseImpl : GenerateExportCSVUseCase {
    override suspend fun invoke(decryptedPasswords: List<PasswordDto>): CSVGenerationResult {
        val csvGenerationResult = CSVExportUtils.generateCsvContentForPasswords(decryptedPasswords)
        return csvGenerationResult
    }
}