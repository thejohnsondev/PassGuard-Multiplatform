package com.thejohnsondev.vault

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.thejohnsondev.vault.auth.login.LoginRobot
import com.thejohnsondev.vault.auth.selectvaulttype.SelectVaultTypeRobot
import com.thejohnsondev.vault.auth.welcome.WelcomeScreenRobot
import com.thejohnsondev.vault.navigation.NavigationRobot
import com.thejohnsondev.vault.settings.SettingsRobot
import com.thejohnsondev.vault.vault.VaultRobot
import org.junit.Rule
import org.junit.Test
import org.thejohnsondev.vault.MainActivity

class AuthFlowsTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testLocalVaultCreation() {
        with(WelcomeScreenRobot(composeTestRule)) {
            assertWelcomeScreenContent()
            clickGetStartedButton()
        }
        with(SelectVaultTypeRobot(composeTestRule)) {
            assertSelectVaultScreen()
            clickCloudVaultOption()
            assertCloudVaultOption()
            clickLocalVaultOption()
            assertLocalVaultOption()
            clickCreateLocalVaultButton()
        }
        with(VaultRobot(composeTestRule)) {
            assertVaultScreen()
        }
        with(NavigationRobot(composeTestRule)) {
            goToSettings()
        }
        with(SettingsRobot(composeTestRule)) {
            clickManageVault()
            clickDeleteVault()
            clickConfirmDeleteVault()
        }
    }

    @Test
    fun testLoginWithWrongCredentials() {
        with(WelcomeScreenRobot(composeTestRule)) {
            clickGetStartedButton()
        }
        with(SelectVaultTypeRobot(composeTestRule)) {
            clickCloudVaultOption()
            clickLogInButton()
        }
        with(LoginRobot(composeTestRule)) {
            assertLoginScreen()
            enterWrongCredentials()
            clickLoginButton()
            assertLoginError()
        }
    }

    @Test
    fun testLoginWithCorrectCredentials() {
        with(WelcomeScreenRobot(composeTestRule)) {
            clickGetStartedButton()
        }
        with(SelectVaultTypeRobot(composeTestRule)) {
            clickCloudVaultOption()
            clickLogInButton()
        }
        with(LoginRobot(composeTestRule)) {
            assertLoginScreen()
            enterCorrectCredentials()
            clickLoginButton()
        }
        with(VaultRobot(composeTestRule)) {
            assertVaultScreen()
        }
        with(NavigationRobot(composeTestRule)) {
            goToSettings()
        }
        with(SettingsRobot(composeTestRule)) {
            clickManageAccount()
            clickLogout()
            clickConfirmLogout()
        }
    }

}