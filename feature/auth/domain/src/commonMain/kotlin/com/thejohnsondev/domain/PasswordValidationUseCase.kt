package com.thejohnsondev.domain

import com.thejohnsondev.model.validation.PasswordValidationState

interface PasswordValidationUseCase {
    operator fun invoke(password: String): PasswordValidationState
}