package com.thejohnsondev.domain

import com.thejohnsondev.domain.repo.PasswordGenerationRepository
import com.thejohnsondev.model.tools.PasswordStrength

class EvaluatePasswordStrengthUseCase(
    private val passwordGenerationRepository: PasswordGenerationRepository
) {
    operator fun invoke(password: String): PasswordStrength {
        return passwordGenerationRepository.evaluateStrength(password)
    }
}