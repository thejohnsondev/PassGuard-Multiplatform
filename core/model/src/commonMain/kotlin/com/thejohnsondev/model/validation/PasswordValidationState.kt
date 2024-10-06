package com.thejohnsondev.model.validation

sealed interface PasswordValidationState {
    data object PasswordCorrectState : PasswordValidationState
    class PasswordIncorrectState(val reason: IncorrectPasswordReason) : PasswordValidationState
}

enum class IncorrectPasswordReason {
    BAD_LENGTH,
    NO_NUMBERS,
    NO_CAPITAL,
    NO_SMALL
}
