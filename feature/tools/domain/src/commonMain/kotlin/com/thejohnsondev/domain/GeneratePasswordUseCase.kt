package com.thejohnsondev.domain

import com.thejohnsondev.domain.repo.PasswordGenerationRepository
import com.thejohnsondev.model.tools.PasswordGeneratedResult
import com.thejohnsondev.model.tools.PasswordGeneratorConfig

class GeneratePasswordUseCase(
    private val passwordGenerationRepository: PasswordGenerationRepository
) {
    operator fun invoke(config: PasswordGeneratorConfig): PasswordGeneratedResult {
        return passwordGenerationRepository.generatePassword(
            type = config.type,
            length = config.length,
            includeLower = config.includeLower,
            includeUpper = config.includeUpper,
            includeDigits = config.includeDigits,
            includeSpecial = config.includeSpecial
        )
    }
}