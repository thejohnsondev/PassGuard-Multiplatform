package com.thejohnsondev.domain

import com.thejohnsondev.domain.utils.PasswordGenerator
import com.thejohnsondev.model.tools.PasswordGeneratedResult
import com.thejohnsondev.model.tools.PasswordGeneratorConfig

internal class GeneratePasswordUseCaseImpl(
    private val passwordGenerator: PasswordGenerator
): GeneratePasswordUseCase {
    override suspend fun invoke(config: PasswordGeneratorConfig): PasswordGeneratedResult {
        return passwordGenerator.generatePassword(
            type = config.type,
            length = config.length,
            includeLower = config.includeLower,
            includeUpper = config.includeUpper,
            includeDigits = config.includeDigits,
            includeSpecial = config.includeSpecial
        )
    }
}