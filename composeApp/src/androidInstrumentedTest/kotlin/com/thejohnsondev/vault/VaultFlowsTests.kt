package com.thejohnsondev.vault

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.thejohnsondev.vault.auth.OnboardingRobot
import com.thejohnsondev.vault.auth.SelectVaultTypeRobot
import com.thejohnsondev.vault.auth.WelcomeScreenRobot
import com.thejohnsondev.vault.navigation.NavigationRobot
import com.thejohnsondev.vault.settings.SettingsRobot
import com.thejohnsondev.vault.vault.AddVaultItemRobot
import com.thejohnsondev.vault.vault.VaultRobot
import org.junit.Rule
import org.junit.Test
import org.thejohnsondev.vault.MainActivity

class VaultFlowsTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val welcomeRobot = WelcomeScreenRobot(composeTestRule)
    private val onboardingRobot = OnboardingRobot(composeTestRule)
    private val vaultRobot = VaultRobot(composeTestRule)
    private val addVaultItemRobot = AddVaultItemRobot(composeTestRule)
    private val navigationRobot = NavigationRobot(composeTestRule)
    private val settingsRobot = SettingsRobot(composeTestRule)

    private val item1title = "Test Item 1"
    private val item1username = "testuser1"
    private val item1password = "testpassword1"

    private val item2title = "Test Item 2"
    private val item2username = "testuser2"
    private val item2password = "testpassword2"

    @Test
    fun testFullLocalVaultFlow() {
        createLocalVault()
        testEmptyVaultAndAddingFirstItem()
        testFirstItemLifecycle()
        addSecondItemAndTestInteractions()
        deleteVault()
    }

    private fun createLocalVault() {
        welcomeRobot.apply {
            assertWelcomeScreenContent()
            clickGetStartedButton()
        }
        onboardingRobot.apply {
            assertFirstOnboardingScreen()
            clickNextButton()
            clickNextButton()
            clickPrivacyPolicyCheckbox()
            clickCreateVaultButton()
        }
    }

    private fun testEmptyVaultAndAddingFirstItem() {
        vaultRobot.apply {
            assertEmptyVaultScreen()
            clickAddButton()
        }
        addVaultItemRobot.clickBackButton()
        vaultRobot.assertEmptyVaultScreen()

        vaultRobot.clickAddButton()
        addVaultItemRobot.apply {
            assertEmptyScreen()
            createSimplePasswordItem(
                title = item1title,
                username = item1username,
                password = item1password
            )
        }
    }

    private fun testFirstItemLifecycle() {
        vaultRobot.apply {
            assertItemClosed(title = item1title, username = item1username)
            assertItemUpdated(title = item1title, username = item1username)
            clickItem(title = item1title)
            assertItemOpened(title = item1title, username = item1username)
            clickEyeButton()
            assertPasswordVisible(password = item1password)
            clickItem(title = item1title)
            assertItemClosed(title = item1title, username = item1username)
        }
    }

    private fun addSecondItemAndTestInteractions() {
        vaultRobot.clickAddButton()
        addVaultItemRobot.createSimplePasswordItem(
            title = item2title,
            username = item2username,
            password = item2password
        )
        vaultRobot.apply {
            assertItemClosed(title = item2title, username = item2username)
            assertItemUpdated(title = item2title, username = item2username)

            clickItem(title = item2title)
            assertItemOpened(title = item2title, username = item2username)
            assertItemClosed(title = item1title, username = item1username)

            clickItem(title = item1title)
            assertItemOpened(title = item1title, username = item1username)
            assertItemClosed(title = item2title, username = item2username)

            deleteItem()
            assertItemDeleted(item1title)
        }
    }

    private fun deleteVault() {
        navigationRobot.goToSettings()
        settingsRobot.apply {
            clickManageVault()
            clickDeleteVault()
            clickConfirmDeleteVault()
        }
    }
}