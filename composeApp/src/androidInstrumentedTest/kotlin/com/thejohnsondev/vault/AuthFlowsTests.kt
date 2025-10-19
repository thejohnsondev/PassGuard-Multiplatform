package com.thejohnsondev.vault

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.thejohnsondev.vault.auth.LoginRobot
import com.thejohnsondev.vault.auth.OnboardingRobot
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

    private val welcomeRobot = WelcomeScreenRobot(composeTestRule)
    private val selectVaultTypeRobot = SelectVaultTypeRobot(composeTestRule)
    private val onboardingRobot = OnboardingRobot(composeTestRule)
    private val loginRobot = LoginRobot(composeTestRule)
    private val signUpRobot = SignUpRobot(composeTestRule)
    private val vaultRobot = VaultRobot(composeTestRule)
    private val navigationRobot = NavigationRobot(composeTestRule)
    private val settingsRobot = SettingsRobot(composeTestRule)

    private fun navigateToCloudVaultOptions() {
        welcomeRobot.apply {
            assertWelcomeScreenContent()
            clickGetStartedButton()
        }
        selectVaultTypeRobot.assertSelectVaultScreen()
    }

    private fun navigateToLoginScreen() {
        navigateToCloudVaultOptions()
        selectVaultTypeRobot.clickCloudVaultOption()
        selectVaultTypeRobot.clickLogInButton()
        loginRobot.assertLoginScreen()
    }

    private fun navigateToSignUpScreen() {
        navigateToCloudVaultOptions()
        selectVaultTypeRobot.clickCloudVaultOption()
        selectVaultTypeRobot.clickSignUpButton()
        signUpRobot.assertSignUpScreen()
    }

    private fun logoutFromAccount() {
        navigationRobot.goToSettings()
        settingsRobot.apply {
            clickManageAccount()
            clickLogout()
            clickConfirmLogout()
        }
    }

    @Test
    fun testLocalVaultCreationAndDeletion() {
        welcomeRobot.assertWelcomeScreenContent()

        welcomeRobot.clickGetStartedButton()
        onboardingRobot.apply {
            assertFirstOnboardingScreen()
            clickNextButton()
            assertSecondOnboardingScreen()
            clickNextButton()
            assertThirdOnboardingScreen()
            clickPrivacyPolicyCheckbox()
            clickCreateVaultButton()
        }
        vaultRobot.assertEmptyVaultScreen()

        navigationRobot.goToSettings()
        settingsRobot.apply {
            clickManageVault()
            clickDeleteVault()
            clickConfirmDeleteVault()
        }
        welcomeRobot.assertWelcomeScreenContent()
    }

    //    @Test // TODO uncomment when cloud vault is integrated
    fun testLoginWithWrongCredentials() {
        navigateToLoginScreen()

        loginRobot.apply {
            enterWrongCredentials()
            clickLoginButton()
        }
        loginRobot.assertLoginError()
    }

    //    @Test // TODO uncomment when cloud vault is integrated
    fun testSuccessfulLoginAndLogout() {
        navigateToLoginScreen()

        loginRobot.apply {
            enterCorrectCredentials()
            clickLoginButton()
        }
        vaultRobot.assertEmptyVaultScreen()

        logoutFromAccount()
        welcomeRobot.assertWelcomeScreenContent()
    }

    //    @Test // TODO uncomment when cloud vault is integrated
    fun testSignUpWithExistingAccountShowsError() {
        navigateToSignUpScreen()

        signUpRobot.apply {
            enterExistingAccountCredentials()
            clickAcceptTermsAndConditions()
            clickSignUpButton()
        }
        signUpRobot.assertSignUpError()
    }

    //    @Test // TODO uncomment when cloud vault is integrated
    fun testSuccessfulSignUpAndLogout() {
        navigateToSignUpScreen()

        signUpRobot.apply {
            enterCorrectCredentials()
            clickAcceptTermsAndConditions()
            clickSignUpButton()
        }
        vaultRobot.assertEmptyVaultScreen()

        logoutFromAccount()
        welcomeRobot.assertWelcomeScreenContent()
    }
}