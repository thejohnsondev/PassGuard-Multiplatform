package com.thejohnsondev.model.validation

sealed interface EmailValidationState {
    data object EmailCorrectState : EmailValidationState
    data class EmailIncorrectState(val reason: EmailIncorrectReason) : EmailValidationState
}

enum class EmailIncorrectReason {
    INCORRECT
}
