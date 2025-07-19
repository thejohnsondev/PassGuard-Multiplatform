package com.thejohnsondev.ui.utils

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController

actual object KeyboardManager {
    @Composable
    actual fun keyboardAsState(): State<Boolean> {
        val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
        return rememberUpdatedState(isImeVisible)
    }

    @Composable
    actual fun getKeyboardController(): SoftwareKeyboardController? {
        return LocalSoftwareKeyboardController.current
    }
}