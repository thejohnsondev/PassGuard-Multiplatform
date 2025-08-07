package com.thejohnsondev.vault

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.thejohnsondev.vault.auth.SelectVaultTypeRobot
import com.thejohnsondev.vault.auth.WelcomeScreenRobot
import com.thejohnsondev.vault.vault.VaultRobot
import org.junit.Rule
import org.junit.Test
import org.thejohnsondev.vault.MainActivity

class VaultFlowsTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testVaultScreen() {
        with(WelcomeScreenRobot(composeTestRule)) {
            clickGetStartedButton()
        }
        with(SelectVaultTypeRobot(composeTestRule)) {
            clickLocalVaultOption()
            clickCreateLocalVaultButton()
        }
        with(VaultRobot(composeTestRule)) {
            assertEmptyVaultScreen()
            clickAddButton()
        }
    }

}