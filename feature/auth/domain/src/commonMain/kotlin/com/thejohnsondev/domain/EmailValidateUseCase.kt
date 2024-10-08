package com.thejohnsondev.domain

import com.thejohnsondev.model.validation.EmailValidationState

class EmailValidateUseCase {

    operator fun invoke(email: String): EmailValidationState {
        return EmailValidationState.EmailCorrectState // TODO IMPLEMENT LATER
    }

}