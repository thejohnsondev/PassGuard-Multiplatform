package com.thejohnsondev.vault.auth.welcome

import androidx.compose.ui.test.junit4.ComposeTestRule
import com.thejohnsondev.vault.utils.Robot

class WelcomeScreenRobot(composeTestRule: ComposeTestRule) : Robot(composeTestRule) {

    fun clickGetStartedButton() {
        waitForContent("Get Started")
        clickButton("Get Started")
    }

    fun assertGetStartedButtonIsDisplayed() {
        waitForContent("Get Started")
        assertButton("Get Started")
    }

    fun assertChooseVaultTypeIsDisplayed() {
        waitForText("Choose your vault type")
        assertText("Choose your vault type")
    }

}