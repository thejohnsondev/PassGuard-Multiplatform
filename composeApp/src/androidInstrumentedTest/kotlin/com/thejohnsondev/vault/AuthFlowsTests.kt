package com.thejohnsondev.vault

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.thejohnsondev.vault.auth.LoginRobot
import com.thejohnsondev.vault.auth.SelectVaultTypeRobot
import com.thejohnsondev.vault.auth.SignUpRobot
import com.thejohnsondev.vault.auth.WelcomeScreenRobot
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

    @Test
    fun testSignUpWithExistingAccount() {
        with(WelcomeScreenRobot(composeTestRule)) {
            clickGetStartedButton()
        }
        with(SelectVaultTypeRobot(composeTestRule)) {
            clickCloudVaultOption()
            clickSignUpButton()
        }
        with(SignUpRobot(composeTestRule)) {
            assertSignUpScreen()
            enterExistingAccountCredentials()
            clickAcceptTermsAndConditions()
            clickSignUpButton()
            assertSignUpError()
        }
    }

    @Test
    fun testSignUpWithNewAccount() {
        with(WelcomeScreenRobot(composeTestRule)) {
            clickGetStartedButton()
        }
        with(SelectVaultTypeRobot(composeTestRule)) {
            clickCloudVaultOption()
            clickSignUpButton()
        }
        with(SignUpRobot(composeTestRule)) {
            assertSignUpScreen()
            enterCorrectCredentials()
            clickAcceptTermsAndConditions()
            clickSignUpButton()
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