package com.thejohnsondev.vault.auth.welcome

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.thejohnsondev.vault.MainActivity

class WelcomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    private lateinit var robot: WelcomeScreenRobot

    @Before
    fun setup() {
        robot = WelcomeScreenRobot(composeTestRule)
    }

    @Test
    fun testGetStartedButtonNavigatesToLoginScreen() {
        robot.assertGetStartedButtonIsDisplayed()
        robot.clickGetStartedButton()
        robot.assertChooseVaultTypeIsDisplayed()
    }
}