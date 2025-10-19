package com.thejohnsondev.vault.auth

import androidx.compose.ui.test.junit4.ComposeTestRule
import com.thejohnsondev.vault.utils.Robot

class OnboardingRobot(composeTestRule: ComposeTestRule): Robot(composeTestRule) {

    fun assertFirstOnboardingScreen() {
        waitForContent("Your Data is Always Safe")
        assertText("Your Data is Always Safe")
        assertButton("Next")
    }

    fun assertSecondOnboardingScreen() {
        waitForContent("Vault Stays on Your Device")
        assertText("Vault Stays on Your Device")
    }

    fun assertThirdOnboardingScreen() {
        waitForContent("Move Passwords Easily")
        assertText("Move Passwords Easily")
        assertButton("Create Vault")
    }

    fun clickNextButton() {
        waitForContent("Next")
        clickButton("Next")
    }

    fun clickPrivacyPolicyCheckbox() {
        waitForContent("Accept Privacy Policy")
        clickButton("Accept Privacy Policy")
    }

    fun clickCreateVaultButton() {
        waitForContent("Create Vault")
        clickButton("Create Vault")
    }
}