package com.thejohnsondev.domain

import com.thejohnsondev.domain.utils.PasswordGenerator
import com.thejohnsondev.model.tools.PasswordStrength

internal class EvaluatePasswordStrengthUseCaseImpl(
    private val passwordGenerator: PasswordGenerator
): EvaluatePasswordStrengthUseCase {
    override fun invoke(password: String): PasswordStrength {
        return passwordGenerator.evaluateStrength(password)
    }
}