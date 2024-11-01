package com.thejohnsondev.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState

@Composable
actual fun keyboardAsState(): State<Boolean> = rememberUpdatedState(false)

actual fun isKeyboardAvailable(): Boolean = false