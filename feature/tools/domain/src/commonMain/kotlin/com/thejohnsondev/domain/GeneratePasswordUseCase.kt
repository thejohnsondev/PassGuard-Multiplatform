package com.thejohnsondev.domain

import com.thejohnsondev.model.tools.PasswordGeneratedResult
import com.thejohnsondev.model.tools.PasswordGeneratorConfig

interface GeneratePasswordUseCase {
    suspend operator fun invoke(
        config: PasswordGeneratorConfig
    ): PasswordGeneratedResult
}