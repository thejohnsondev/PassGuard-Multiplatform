package com.thejohnsondev.domain

import com.thejohnsondev.domain.export.CSVGenerationResult
import com.thejohnsondev.model.vault.PasswordDto

interface GenerateExportCSVUseCase {
    suspend operator fun invoke(decryptedPasswords: List<PasswordDto>): CSVGenerationResult
}