package com.thejohnsondev.vault.auth.selectvaulttype

import androidx.compose.ui.test.junit4.ComposeTestRule
import com.thejohnsondev.vault.utils.Robot

class SelectVaultTypeRobot(composeTestRule: ComposeTestRule): Robot(composeTestRule) {

    fun assertOptionsVisible() {
        waitForText("Local Vault")
        assertText("Local Vault")
        waitForText("Cloud Vault")
        assertText("Cloud Vault")
    }

    fun clickCloudVaultOption() {
        waitForText("Cloud Vault")
        clickButton("Cloud Vault")
        waitForContent("Log in")
        assertButton("Log in")
        waitForContent("Sign up")
        assertButton("Sign up")
        assertDoesNotExist("Create a local Vault")
    }

    fun clickLocalVaultOption() {
        waitForText("Local Vault")
        clickButton("Local Vault")
        waitForContent("Create a local Vault")
        assertButton("Create a local Vault")
        assertDoesNotExist("Log in")
        assertDoesNotExist("Sign up")
    }
}