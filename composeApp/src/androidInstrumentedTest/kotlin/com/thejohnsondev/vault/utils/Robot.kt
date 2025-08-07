package com.thejohnsondev.vault.utils

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import com.thejohnsondev.common.CONTENT_DESCRIPTION_VERTICAL_SCROLL

abstract class Robot(val composeRule: ComposeTestRule) {

    fun clickButton(
        description: String
    ) = composeRule
        .onNode(hasContentDescription(description).or(hasText(description)))
        .performClick()

    fun clickButton(
        description: String,
        index: Int
    ) = composeRule
        .onAllNodes(hasContentDescription(description).or(hasText(description)))[index]
        .performClick()

    fun clickBack() = clickButton("Back Button")

    fun assertButton(
        description: String
    ) = composeRule
        .onNode(hasContentDescription(description).and(hasClickAction()))
        .assertIsDisplayed()

    fun assertButton(
        description: String,
        index: Int
    ) = composeRule
        .onAllNodes(hasContentDescription(description).and(hasClickAction()))[index]
        .assertIsDisplayed()

    fun assertImage(
        description: String
    ) = composeRule
        .onNode(hasContentDescription(description))
        .assertIsDisplayed()

    fun assertText(
        text: String
    ) = composeRule
        .onNode(hasText(text))
        .assertIsDisplayed()

    fun assertText(
        text: String,
        index: Int
    ) = composeRule
        .onAllNodes(hasText(text))[index]
        .assertIsDisplayed()

    fun assertDoesNotExist(
        description: String
    ) = composeRule
        .onNode(hasContentDescription(description).or(hasText(description)))
        .assertDoesNotExist()

    @OptIn(ExperimentalTestApi::class)
    fun waitForContent(
        contentDescription: String,
        timeout: Long = 5000L
    ) = composeRule
        .waitUntilAtLeastOneExists(
            hasContentDescription(contentDescription).or(hasText(contentDescription)),
            timeout
        )

    fun enterText(
        fieldContent: String,
        text: String
    ) = composeRule
        .onNode(hasText(fieldContent).or(hasText(fieldContent)))
        .performTextInput(text)

    fun scrollToBottom() = composeRule
        .onNode(hasContentDescription(CONTENT_DESCRIPTION_VERTICAL_SCROLL), useUnmergedTree = true)
        .performTouchInput {
            swipeUp(
                startY = center.y,
                endY = top
            )
        }

}