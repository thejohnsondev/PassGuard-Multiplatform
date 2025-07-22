package com.thejohnsondev.domain

import com.thejohnsondev.domain.passwordgenerator.PasswordGenerator
import com.thejohnsondev.model.tools.PasswordStrength

internal class EvaluatePasswordStrengthUseCase(
    private val passwordGenerator: PasswordGenerator
) {
    operator fun invoke(password: String): PasswordStrength {
        return passwordGenerator.evaluateStrength(password)
    }
}