package com.thejohnsondev.model.validation

import com.thejohnsondev.model.DisplayableMessageValue

sealed interface PasswordValidationState {
    data object PasswordCorrectState : PasswordValidationState
    data class PasswordIncorrectState(val reason: DisplayableMessageValue) : PasswordValidationState
}