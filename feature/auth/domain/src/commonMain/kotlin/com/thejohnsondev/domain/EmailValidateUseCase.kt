package com.thejohnsondev.domain

import com.thejohnsondev.model.validation.EmailValidationState

interface EmailValidateUseCase {
    operator fun invoke(email: String): EmailValidationState
}