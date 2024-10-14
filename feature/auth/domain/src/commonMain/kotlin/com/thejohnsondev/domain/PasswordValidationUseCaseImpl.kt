package com.thejohnsondev.domain

import com.thejohnsondev.common.utils.isPasswordValid
import com.thejohnsondev.model.validation.PasswordValidationState

class PasswordValidationUseCaseImpl : PasswordValidationUseCase {

    override operator fun invoke(password: String): PasswordValidationState {
        return password.isPasswordValid()
    }

}