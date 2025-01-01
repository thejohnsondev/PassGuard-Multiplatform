package com.thejohnsondev.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController

actual object KeyboardManager {
    @Composable
    actual fun keyboardAsState(): State<Boolean> = rememberUpdatedState(false)

    actual fun isKeyboardAvailable(): Boolean = false

    @Composable
    actual fun getKeyboardController(): SoftwareKeyboardController? {
        return LocalSoftwareKeyboardController.current
    }
}