package com.thejohnsondev.model.validation

sealed interface EmailValidationState {
    data object EmailCorrectState : EmailValidationState
    class EmailIncorrectState(val reason: EmailIncorrectReason) : EmailValidationState
}

enum class EmailIncorrectReason {
    INCORRECT
}
