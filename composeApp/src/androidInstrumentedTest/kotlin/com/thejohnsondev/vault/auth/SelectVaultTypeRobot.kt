package com.thejohnsondev.vault.auth

import androidx.compose.ui.test.junit4.ComposeTestRule
import com.thejohnsondev.vault.utils.Robot

class SelectVaultTypeRobot(composeTestRule: ComposeTestRule): Robot(composeTestRule) {

    fun assertSelectVaultScreen() {
        waitForContent("Choose your vault type")
        assertText("Choose your vault type")
        waitForContent("Local Vault")
        assertText("Local Vault")
        waitForContent("Cloud Vault")
        assertText("Cloud Vault")
    }

    fun clickCloudVaultOption() {
        waitForContent("Cloud Vault")
        clickButton("Cloud Vault")
    }

    fun assertCloudVaultOption() {
        waitForContent("Log in")
        assertButton("Log in")
        waitForContent("Sign up")
        assertButton("Sign up")
        assertDoesNotExist("Create a local Vault")
    }

    fun clickLocalVaultOption() {
        waitForContent("Local Vault")
        clickButton("Local Vault")
    }

    fun assertLocalVaultOption() {
        waitForContent("Create a local Vault")
        assertButton("Create a local Vault")
        assertDoesNotExist("Log in")
        assertDoesNotExist("Sign up")
    }

    fun clickCreateLocalVaultButton() {
        waitForContent("Create a local Vault")
        clickButton("Create a local Vault")
    }

    fun clickLogInButton() {
        waitForContent("Log in")
        clickButton("Log in")
    }

    fun clickSignUpButton() {
        waitForContent("Sign up")
        clickButton("Sign up")
    }
}