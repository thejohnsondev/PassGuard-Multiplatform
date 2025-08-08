package com.thejohnsondev.vault

import androidx.compose.ui.test.junit4.createAndroidComposeRule
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

    @Test
    fun testEmptyVaultScreen() {
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
        with(AddVaultItemRobot(composeTestRule)) {
            clickBackButton()
        }
        with(VaultRobot(composeTestRule)) {
            assertEmptyVaultScreen()
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
    fun testAddVaultItem() {
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
        with(AddVaultItemRobot(composeTestRule)) {
            assertEmptyScreen()
            createSimplePasswordItem(
                title = "Test Item",
                username = "testuser",
                password = "testpassword"
            )
        }
        with(VaultRobot(composeTestRule)) {
            assertVaultItem(
                title = "Test Item",
                username = "testuser"
            )
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

}