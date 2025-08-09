package com.thejohnsondev.vault.vault

import androidx.compose.ui.test.junit4.ComposeTestRule
import com.thejohnsondev.vault.utils.Robot

class AddVaultItemRobot(composeTestRule: ComposeTestRule): Robot(composeTestRule) {

    fun assertEmptyScreen() {
        waitForContent("Save")
        assertButton("Save")
        assertButton("Add field")
    }

    fun clickBackButton() {
        clickBack()
    }

    fun createSimplePasswordItem(
        title: String,
        username: String,
        password: String
    ) {
        waitForContent("Title")
        enterText("Title", title)
        enterText("Username", username)
        enterText("Password", password)
        clickButton("Save")
    }

}