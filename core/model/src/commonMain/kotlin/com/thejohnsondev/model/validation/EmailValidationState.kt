package com.thejohnsondev.model.validation

import com.thejohnsondev.model.DisplayableMessageValue

sealed interface EmailValidationState {
    data object EmailCorrectState : EmailValidationState
    data class EmailIncorrectState(val reason: DisplayableMessageValue) : EmailValidationState
}