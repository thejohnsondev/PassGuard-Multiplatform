package com.thejohnsondev.common.utils

import com.thejohnsondev.model.validation.EmailIncorrectReason
import com.thejohnsondev.model.validation.EmailValidationState
import com.thejohnsondev.model.validation.IncorrectPasswordReason
import com.thejohnsondev.model.validation.PasswordValidationState
import kotlin.test.Test
import kotlin.test.assertEquals


class DataUtilsKtTest {

    @Test
    fun testIsPasswordValid_correctPassword() {
        val password = "Valid123"
        val result = password.isPasswordValid()
        assertEquals(PasswordValidationState.PasswordCorrectState, result)
    }

    @Test
    fun testIsPasswordValid_badLength() {
        val password = "Val1"
        val result = password.isPasswordValid()
        assertEquals(
            PasswordValidationState.PasswordIncorrectState(IncorrectPasswordReason.BAD_LENGTH),
            result
        )
    }

    @Test
    fun testIsPasswordValid_noNumbers() {
        val password = "ValidPassword"
        val result = password.isPasswordValid()
        assertEquals(
            PasswordValidationState.PasswordIncorrectState(IncorrectPasswordReason.NO_NUMBERS),
            result
        )
    }

    @Test
    fun testIsPasswordValid_noUpperCase() {
        val password = "valid123"
        val result = password.isPasswordValid()
        assertEquals(
            PasswordValidationState.PasswordIncorrectState(IncorrectPasswordReason.NO_CAPITAL),
            result
        )
    }

    @Test
    fun testIsPasswordValid_noLowerCase() {
        val password = "VALID123"
        val result = password.isPasswordValid()
        assertEquals(
            PasswordValidationState.PasswordIncorrectState(IncorrectPasswordReason.NO_SMALL),
            result
        )
    }

    @Test
    fun testIsEmailValid_correctEmail() {
        val email = "test@example.com"
        val result = email.isEmailValid()
        assertEquals(EmailValidationState.EmailCorrectState, result)
    }

    @Test
    fun testIsEmailValid_incorrectEmail() {
        val email = "invalid-email"
        val result = email.isEmailValid()
        assertEquals(
            EmailValidationState.EmailIncorrectState(EmailIncorrectReason.INCORRECT),
            result
        )
    }

    @Test
    fun testIsEmailValid_emptyEmail() {
        val email = ""
        val result = email.isEmailValid()
        assertEquals(
            EmailValidationState.EmailIncorrectState(EmailIncorrectReason.INCORRECT),
            result
        )
    }

    @Test
    fun testHidden_withNonEmptyString() {
        val input = "HelloWorld"
        val expected = "**********"
        val result = input.hidden()
        assertEquals(expected, result)
    }

    @Test
    fun testHidden_withEmptyString() {
        val input = ""
        val expected = ""
        val result = input.hidden()
        assertEquals(expected, result)
    }

    @Test
    fun testHidden_withSpaces() {
        val input = "Hello World"
        val expected = "***********"
        val result = input.hidden()
        assertEquals(expected, result)
    }

    @Test
    fun testHidden_withSpecialCharacters() {
        val input = "Hello@World!"
        val expected = "************"
        val result = input.hidden()
        assertEquals(expected, result)
    }

    @Test
    fun testGetPrettyErrorMessage_withError() {
        val error = "Some error occurred"
        val result = getPrettyErrorMessage(error)
        assertEquals("Some error occurred", result)
    }

    @Test
    fun testGetPrettyErrorMessage_withNullError() {
        val error: String? = null
        val result = getPrettyErrorMessage(error)
        assertEquals("Unknown error", result)
    }


}