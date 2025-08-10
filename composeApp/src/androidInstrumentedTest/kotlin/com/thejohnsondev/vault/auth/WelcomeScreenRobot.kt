package com.thejohnsondev.vault.auth

import androidx.compose.ui.test.junit4.ComposeTestRule
import com.thejohnsondev.vault.utils.Robot

class WelcomeScreenRobot(composeTestRule: ComposeTestRule) : Robot(composeTestRule) {

    fun assertWelcomeScreenContent() {
        waitForContent("Get Started")
        assertButton("Get Started")
        assertText("PassGuard")
    }

    fun clickGetStartedButton() {
        waitForContent("Get Started")
        clickButton("Get Started")
    }

}