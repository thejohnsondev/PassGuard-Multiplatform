package com.thejohnsondev.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.platform.SoftwareKeyboardController

expect object KeyboardManager {
    @Composable
    fun keyboardAsState(): State<Boolean>
    fun isKeyboardAvailable(): Boolean
    @Composable
    fun getKeyboardController(): SoftwareKeyboardController?
}