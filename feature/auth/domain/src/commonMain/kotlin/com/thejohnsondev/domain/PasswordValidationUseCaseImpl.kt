package com.thejohnsondev.domain

import com.thejohnsondev.common.PASS_MIN_SIZE
import com.thejohnsondev.model.validation.IncorrectPasswordReason
import com.thejohnsondev.model.validation.PasswordValidationState

class PasswordValidationUseCaseImpl : PasswordValidationUseCase {

    override operator fun invoke(password: String): PasswordValidationState {
        val length = password.length
        if (length < PASS_MIN_SIZE) return PasswordValidationState.PasswordIncorrectState(
            IncorrectPasswordReason.BAD_LENGTH
        )

        val containsNumbers = password.any { it.isDigit() }
        if (!containsNumbers) return PasswordValidationState.PasswordIncorrectState(
            IncorrectPasswordReason.NO_NUMBERS
        )

        val containsUpperCase = password.any { it.isUpperCase() }
        if (!containsUpperCase) return PasswordValidationState.PasswordIncorrectState(
            IncorrectPasswordReason.NO_CAPITAL
        )

        val containsLowerCase = password.any { it.isLowerCase() }
        if (!containsLowerCase) return PasswordValidationState.PasswordIncorrectState(
            IncorrectPasswordReason.NO_SMALL
        )

        return PasswordValidationState.PasswordCorrectState
    }

}