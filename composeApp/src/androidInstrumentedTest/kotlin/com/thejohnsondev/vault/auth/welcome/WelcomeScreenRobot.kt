package com.thejohnsondev.vault.auth.welcome

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.performClick

class WelcomeScreenRobot(private val composeTestRule: ComposeTestRule) {

    private val getStartedButtonMatcher =
        hasContentDescription("Get Started")
    private val chooseVaultTypeTextMatcher =
        hasText("Choose your vault type")

    @OptIn(ExperimentalTestApi::class)
    fun clickGetStartedButton() {
        composeTestRule.waitUntilAtLeastOneExists(
            getStartedButtonMatcher,
            timeoutMillis = 5000
        )
        composeTestRule
            .onNode(getStartedButtonMatcher)
            .performClick()
    }

    @OptIn(ExperimentalTestApi::class)
    fun assertGetStartedButtonIsDisplayed() {
        composeTestRule.waitUntilAtLeastOneExists(
            getStartedButtonMatcher,
            timeoutMillis = 5000
        )
        composeTestRule
            .onNode(getStartedButtonMatcher)
            .assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    fun assertChooseVaultTypeIsDisplayed() {
        composeTestRule.waitUntilAtLeastOneExists(
            chooseVaultTypeTextMatcher,
            timeoutMillis = 5000
        )
        composeTestRule
            .onNode(chooseVaultTypeTextMatcher)
            .assertIsDisplayed()
    }
}