package com.thejohnsondev.vault.auth

import androidx.compose.ui.test.junit4.ComposeTestRule
import com.thejohnsondev.vault.utils.Robot
import java.util.UUID

class SignUpRobot(composeTestRule: ComposeTestRule): Robot(composeTestRule) {
    fun assertSignUpScreen() {
        waitForContent("PassGuard")
        assertText("PassGuard")
        assertText("Sign up", index = 0)
        assertText("Already have an account?")
    }

    fun enterCorrectCredentials() {
        enterText("Email", "${UUID.randomUUID()}@test.com")
        enterText("Password", "Pass123$")
    }

    fun enterExistingAccountCredentials() {
        enterText("Email", "test@test.com")
        enterText("Password", "WrongPass123$")
    }

    fun assertSignUpError() {
        waitForContent("Email already exists")
        assertText("Email already exists")
    }

    fun clickAcceptTermsAndConditions() {
        clickButton("Accept Privacy Policy")
    }

    fun clickSignUpButton() {
        clickButton("Sign up", index = 1)
    }
}