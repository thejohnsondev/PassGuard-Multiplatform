package com.thejohnsondev.vault.utils

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.performClick

abstract class Robot(val composeRule: ComposeTestRule) {

    fun clickButton(
        description: String
    ) = composeRule
        .onNode(hasContentDescription(description))
        .performClick()

    fun goBack() = clickButton("Back Button")

    fun assertButton(
        description: String
    ) = composeRule
        .onNode(hasContentDescription(description).and(hasClickAction()))
        .assertIsDisplayed()

    fun assertImage(
        description: String
    ) = composeRule
        .onNode(hasContentDescription(description))
        .assertIsDisplayed()

    fun assertText(
        text: String,
        ignoreCase: Boolean = false,
        substring: Boolean = false
    ) = composeRule
        .onNode(hasText(text, ignoreCase = ignoreCase, substring = substring))
        .assertIsDisplayed()

    fun assertDoesNotExist(
        description: String,
        ignoreCase: Boolean = false,
        substring: Boolean = false
    ) = composeRule
        .onNode(hasContentDescription(description, ignoreCase = ignoreCase, substring = substring))
        .assertDoesNotExist()

    @OptIn(ExperimentalTestApi::class)
    fun waitForContent(
        contentDescription: String,
        timeout: Long = 5000L
    ) = composeRule
        .waitUntilAtLeastOneExists(
            hasContentDescription(contentDescription),
            timeout
        )

    @OptIn(ExperimentalTestApi::class)
    fun waitForText(
        text: String,
        timeout: Long = 5000L
    ) = composeRule
        .waitUntilAtLeastOneExists(
            hasText(text),
            timeout
        )
}