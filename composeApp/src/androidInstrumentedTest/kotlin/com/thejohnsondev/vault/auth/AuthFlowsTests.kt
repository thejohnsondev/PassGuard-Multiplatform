package com.thejohnsondev.vault.auth

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.thejohnsondev.vault.auth.selectvaulttype.SelectVaultTypeRobot
import com.thejohnsondev.vault.auth.welcome.WelcomeScreenRobot
import org.junit.Rule
import org.junit.Test
import org.thejohnsondev.vault.MainActivity

class AuthFlowsTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testLocalVaultCreation() {
        with(WelcomeScreenRobot(composeTestRule)) {
            assertGetStartedButtonIsDisplayed()
            clickGetStartedButton()
            assertChooseVaultTypeIsDisplayed()
        }
        with(SelectVaultTypeRobot(composeTestRule)) {
            assertOptionsVisible()
            clickCloudVaultOption()
            clickLocalVaultOption()
        }
    }
}