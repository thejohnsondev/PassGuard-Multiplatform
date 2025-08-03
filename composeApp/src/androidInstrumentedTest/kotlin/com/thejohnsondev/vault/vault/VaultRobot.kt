package com.thejohnsondev.vault.vault

import androidx.compose.ui.test.junit4.ComposeTestRule
import com.thejohnsondev.vault.utils.Robot

class VaultRobot(composeTestRule: ComposeTestRule): Robot(composeTestRule) {

    fun assertVaultScreen() {
        waitForText("Vault")
        assertText("Vault", 0)
        assertText("Vault", 1)
    }

}