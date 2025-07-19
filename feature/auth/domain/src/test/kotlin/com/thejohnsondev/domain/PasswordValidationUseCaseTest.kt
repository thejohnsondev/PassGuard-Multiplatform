package com.thejohnsondev.domain

import com.thejohnsondev.model.DisplayableMessageValue
import com.thejohnsondev.model.validation.PasswordValidationState
import kotlin.test.Test
import kotlin.test.assertEquals

class PasswordValidationUseCaseTest {

    private val passwordValidationUseCase = PasswordValidationUseCase()

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
            PasswordValidationState.PasswordIncorrectState(DisplayableMessageValue.BadLength),
            result
        )
    }

    @Test
    fun testIsPasswordValid_noNumbers() {
        val password = "ValidPassword"
        val result = passwordValidationUseCase(password)
        assertEquals(
            PasswordValidationState.PasswordIncorrectState(DisplayableMessageValue.NoNumbers),
            result
        )
    }

    @Test
    fun testIsPasswordValid_noUpperCase() {
        val password = "valid123"
        val result = passwordValidationUseCase(password)
        assertEquals(
            PasswordValidationState.PasswordIncorrectState(DisplayableMessageValue.NoCapital),
            result
        )
    }

    @Test
    fun testIsPasswordValid_noLowerCase() {
        val password = "VALID123"
        val result = passwordValidationUseCase(password)
        assertEquals(
            PasswordValidationState.PasswordIncorrectState(DisplayableMessageValue.NoSmall),
            result
        )
    }

}