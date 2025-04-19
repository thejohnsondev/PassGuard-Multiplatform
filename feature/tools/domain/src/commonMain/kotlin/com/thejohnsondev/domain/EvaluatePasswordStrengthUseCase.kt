package com.thejohnsondev.domain

import com.thejohnsondev.model.tools.PasswordStrength

interface EvaluatePasswordStrengthUseCase {
    operator fun invoke(
        password: String
    ): PasswordStrength
}