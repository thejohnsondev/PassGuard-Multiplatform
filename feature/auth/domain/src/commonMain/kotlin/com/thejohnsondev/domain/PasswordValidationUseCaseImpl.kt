package com.thejohnsondev.domain

import com.thejohnsondev.common.PASS_MIN_SIZE
import com.thejohnsondev.model.DisplayableMessageValue
import com.thejohnsondev.model.validation.PasswordValidationState

class PasswordValidationUseCaseImpl : PasswordValidationUseCase {

    override operator fun invoke(password: String): PasswordValidationState {
        val length = password.length
        if (length < PASS_MIN_SIZE) return PasswordValidationState.PasswordIncorrectState(
            DisplayableMessageValue.BadLength
        )

        val containsNumbers = password.any { it.isDigit() }
        if (!containsNumbers) return PasswordValidationState.PasswordIncorrectState(
            DisplayableMessageValue.NoNumbers
        )

        val containsUpperCase = password.any { it.isUpperCase() }
        if (!containsUpperCase) return PasswordValidationState.PasswordIncorrectState(
            DisplayableMessageValue.NoCapital
        )

        val containsLowerCase = password.any { it.isLowerCase() }
        if (!containsLowerCase) return PasswordValidationState.PasswordIncorrectState(
            DisplayableMessageValue.NoSmall
        )

        return PasswordValidationState.PasswordCorrectState
    }

}