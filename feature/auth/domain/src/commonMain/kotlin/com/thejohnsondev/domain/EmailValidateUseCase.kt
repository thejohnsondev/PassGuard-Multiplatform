package com.thejohnsondev.domain

import com.thejohnsondev.model.DisplayableMessageValue
import com.thejohnsondev.model.validation.EmailValidationState

class EmailValidateUseCase {

    operator fun invoke(email: String): EmailValidationState {
        return if (email.isNotEmpty() && Regex(getEmailPattern()).matches(email)) {
            EmailValidationState.EmailCorrectState
        } else {
            EmailValidationState.EmailIncorrectState(DisplayableMessageValue.EmailInvalid)
        }
    }

    private fun getEmailPattern() =
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"

}