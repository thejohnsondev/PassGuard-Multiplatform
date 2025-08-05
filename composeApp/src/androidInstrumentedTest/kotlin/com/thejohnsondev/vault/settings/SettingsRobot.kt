package com.thejohnsondev.vault.settings

import androidx.compose.ui.test.junit4.ComposeTestRule
import com.thejohnsondev.vault.utils.Robot

class SettingsRobot(composeTestRule: ComposeTestRule): Robot(composeTestRule) {

    fun assertSettingsScreen() {
        // TODO implement
    }

    fun clickManageVault() {
        waitForContent("Manage vault")
        clickButton("Manage vault")
    }

    fun clickDeleteVault() {
        waitForContent("Delete Vault")
        scrollToBottom()
        clickButton("Delete Vault")
    }

    fun clickConfirmDeleteVault() {
        waitForContent("Confirm")
        clickButton("Confirm")
    }

    fun clickManageAccount() {
        waitForContent("Manage account")
        clickButton("Manage account")
    }

    fun clickLogout() {
        waitForContent("Logout")
        scrollToBottom()
        clickButton("Logout")
    }

    fun clickConfirmLogout() {
        waitForContent("Confirm")
        clickButton("Confirm")
    }
}