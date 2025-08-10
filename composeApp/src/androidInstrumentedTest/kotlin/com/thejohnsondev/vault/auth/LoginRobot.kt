package com.thejohnsondev.vault.auth

import androidx.compose.ui.test.junit4.ComposeTestRule
import com.thejohnsondev.vault.utils.Robot

class LoginRobot(composeTestRule: ComposeTestRule): Robot(composeTestRule) {

    fun assertLoginScreen() {
        waitForContent("PassGuard")
        assertText("PassGuard")
        assertText("Log in", index = 0)
        assertText("Don't have an account?")
    }

    fun enterCorrectCredentials() {
        enterText("Email", "test@test.com")
        enterText("Password", "Pass123$")
    }

    fun enterWrongCredentials() {
        enterText("Email", "wrong@test.com")
        enterText("Password", "WrongPass123$")
    }

    fun assertLoginError() {
        waitForContent("Invalid credentials")
        assertText("Invalid credentials")
    }

    fun clickLoginButton() {
        clickButton("Log in", index = 1)
    }

}