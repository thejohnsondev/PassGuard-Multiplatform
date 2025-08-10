package com.thejohnsondev.vault.navigation

import androidx.compose.ui.test.junit4.ComposeTestRule
import com.thejohnsondev.vault.utils.Robot

class NavigationRobot(composeTestRule: ComposeTestRule): Robot(composeTestRule) {

    fun goToVault() {
        waitForContent("Vault")
        clickButton("Vault", index = 1)
    }

    fun goToTools() {
        waitForContent("Tools")
        clickButton("Tools")
    }

    fun goToSettings() {
        waitForContent("Settings")
        clickButton("Settings")
    }

}