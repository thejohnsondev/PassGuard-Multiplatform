package com.thejohnsondev.domain

import com.thejohnsondev.model.validation.IncorrectPasswordReason
import com.thejohnsondev.model.validation.PasswordValidationState
import kotlin.test.Test
import kotlin.test.assertEquals

class PasswordValidationUseCaseImplTest {

    private val passwordValidationUseCase = PasswordValidationUseCaseImpl()

    @Test
    fun testIsPasswordValid_correctPassword() {
        val password = "Valid123"
        val result = passwordValidationUseCase(password)
        assertEquals(PasswordValidationState.PasswordCorrectState, result)
    }

    @Test
    fun testIsPasswordValid_badLength() {
        val password = "Val1"
        val result = passwordValidationUseCase(password)
        assertEquals(
            PasswordValidationState.PasswordIncorrectState(IncorrectPasswordReason.BAD_LENGTH),
            result
        )
    }

    @Test
    fun testIsPasswordValid_noNumbers() {
        val password = "ValidPassword"
        val result = passwordValidationUseCase(password)
        assertEquals(
            PasswordValidationState.PasswordIncorrectState(IncorrectPasswordReason.NO_NUMBERS),
            result
        )
    }

    @Test
    fun testIsPasswordValid_noUpperCase() {
        val password = "valid123"
        val result = passwordValidationUseCase(password)
        assertEquals(
            PasswordValidationState.PasswordIncorrectState(IncorrectPasswordReason.NO_CAPITAL),
            result
        )
    }

    @Test
    fun testIsPasswordValid_noLowerCase() {
        val password = "VALID123"
        val result = passwordValidationUseCase(password)
        assertEquals(
            PasswordValidationState.PasswordIncorrectState(IncorrectPasswordReason.NO_SMALL),
            result
        )
    }

}