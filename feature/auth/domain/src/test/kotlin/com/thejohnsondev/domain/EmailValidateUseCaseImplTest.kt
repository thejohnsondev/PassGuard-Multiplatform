package com.thejohnsondev.domain

import com.thejohnsondev.model.validation.EmailIncorrectReason
import com.thejohnsondev.model.validation.EmailValidationState
import kotlin.test.Test
import kotlin.test.assertEquals

class EmailValidateUseCaseImplTest {

    private val useCase = EmailValidateUseCaseImpl()

    @Test
    fun testIsEmailValid_correctEmail() {
        val email = "test@example.com"
        val result = useCase(email)
        assertEquals(EmailValidationState.EmailCorrectState, result)
    }

    @Test
    fun testIsEmailValid_incorrectEmail() {
        val email = "invalid-email"
        val result = useCase(email)
        assertEquals(
            EmailValidationState.EmailIncorrectState(EmailIncorrectReason.INCORRECT),
            result
        )
    }

    @Test
    fun testIsEmailValid_emptyEmail() {
        val email = ""
        val result = useCase(email)
        assertEquals(
            EmailValidationState.EmailIncorrectState(EmailIncorrectReason.INCORRECT),
            result
        )
    }

}