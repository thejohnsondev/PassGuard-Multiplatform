package com.thejohnsondev.vault.vault

import androidx.compose.ui.test.junit4.ComposeTestRule
import com.thejohnsondev.vault.utils.Robot

class VaultRobot(composeTestRule: ComposeTestRule): Robot(composeTestRule) {

    fun assertEmptyVaultScreen() {
        waitForContent("Vault")
        assertText("Vault", 0)
        assertText("Vault", 1)
        assertText("Your vault is empty")
        assertButton("Add")
    }

    fun clickAddButton() {
        waitForContent("Add")
        clickButton("Add")
    }

    fun assertVaultItem(
        title: String,
        username: String,
    ) {
        assertText(title)
        assertText(username)
    }

}