package com.thejohnsondev.vault.vault

import androidx.compose.ui.test.junit4.ComposeTestRule
import com.thejohnsondev.vault.utils.Robot

class VaultRobot(composeTestRule: ComposeTestRule): Robot(composeTestRule) {

    fun assertEmptyVaultScreen() {
        waitForContent("Vault")
        assertText("Vault", 0)
        assertText("Vault", 1)
        waitForContent("Your vault is empty")
        assertText("Your vault is empty")
        assertButton("Add")
    }

    fun asserNotEmptyVaultScreen() {
        waitForContent("Vault")
        assertTextNotDisplayed("Your vault is empty")
    }

    fun clickAddButton() {
        waitForContent("Add")
        clickButton("Add")
    }

    fun assertItemClosed(
        title: String,
        username: String,
    ) {
        assertText(title, 0)
        assertText(username, 0)
    }

    fun assertItemUpdated(
        title: String,
        username: String,
    ) {
        waitForContent(title, 0)
        assertText(title, 0)
        assertText(username, 0)
    }

    fun clickItem(
        title: String,
    ) {
        waitUntilNotDisplayed(
            "Updated Item with border"
        )
        clickButton(title, 0)
    }

    fun clickEyeButton() {
        clickButton("Eye Icon", 0)
    }

    fun assertPasswordVisible(
        password: String,
    ) {
        assertText(password)
    }

    fun assertItemOpened(
        title: String,
        username: String,
    ) {
        waitForContent("More info")
        assertText("More info")
        assertText(title, 0)
        assertText(username, 0)
    }

    fun deleteItem() {
        waitForContent("Delete Icon")
        clickButton("Delete Icon")
        waitForContent("Delete")
        clickButton("Delete")
    }

    fun assertItemDeleted(
        title: String,
    ) {
        assertDoesNotExist(title)
    }

}