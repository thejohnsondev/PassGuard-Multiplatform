package com.thejohnsondev.domain

import com.thejohnsondev.common.utils.isEmailValid
import com.thejohnsondev.model.validation.EmailValidationState

class EmailValidateUseCaseImpl : EmailValidateUseCase {

    override operator fun invoke(email: String): EmailValidationState {
        return email.isEmailValid()
    }

}