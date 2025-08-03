package com.thejohnsondev.domain

import com.thejohnsondev.domain.passwordgenerator.PasswordGenerator
import com.thejohnsondev.model.tools.PasswordGeneratedResult
import com.thejohnsondev.model.tools.PasswordGeneratorConfig

class GeneratePasswordUseCase(
    private val passwordGenerator: PasswordGenerator
) {
    operator fun invoke(config: PasswordGeneratorConfig): PasswordGeneratedResult {
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